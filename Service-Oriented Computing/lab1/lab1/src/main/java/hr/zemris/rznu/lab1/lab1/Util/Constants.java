package hr.zemris.rznu.lab1.lab1.Util;

public interface Constants {
    interface Paths{
        String basePath = "/";
        String resources = "/resources/**";
        String js = "/js/**";
        String css = "/css/**";
        String webjars = "/webjars/**";
        String index="/index";
    }

    interface Api{
        String api = "/api";
        String apiAll = "/api/**";

        interface User{
            String userRoot=api+"/user";
            String getAllUsers="";
            String userByIdOrEmail="/{var}";
            String saveUser="/";
            String getAllTweetsForUser="/{var}/tweets";
        }

        interface Tweet{
            String tweetRoot=api+"/tweet";
            String getAllTweets="";
            String tweetBy="/{id}";
            String saveTweet="/";
        }
    }

    interface Swagger
    {
        String swaggerUI = "/swagger-ui.html";
        String swaggerResources = "/swagger-resources/**";
        String swaggerDocs = "/v2/api-docs";
        String swaggerConfig="/configuration/ui";
        String swaggerSecurity="/configuration/security";
        String controllerReferences = "hr.zemris.rznu.lab1.lab1.Controllers";
    }

    interface Views{
        String index="index";
    }
}
