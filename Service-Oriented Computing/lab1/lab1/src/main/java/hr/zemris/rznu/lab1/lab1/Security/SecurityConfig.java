package hr.zemris.rznu.lab1.lab1.Security;

import hr.zemris.rznu.lab1.lab1.Util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryConfiguration = auth.inMemoryAuthentication();
        inMemoryConfiguration.withUser("admin").password(bCryptPasswordEncoder().encode("1234")).roles("ADMIN", "USER")
                .and().withUser("user").password(bCryptPasswordEncoder().encode("1234")).roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//swagger
                .antMatchers(Constants.Paths.basePath, Constants.Paths.index,Constants.Paths.resources, Constants.Paths.webjars, Constants.Paths.js, Constants.Paths.css, Constants.Swagger.swaggerUI, Constants.Swagger.swaggerResources, Constants.Swagger.swaggerDocs, Constants.Swagger.swaggerConfig, Constants.Swagger.swaggerSecurity)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        http.csrf().disable();
    }
}
