package hr.zemris.rznu.lab1.lab1.Controllers;

import hr.zemris.rznu.lab1.lab1.Util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(Constants.Paths.basePath)
    public String home()
    {
        return "redirect:/swagger-ui.html";
    }
}
