package akademia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    @GetMapping("/secured")
    public String securedPage() {
        return "secured";
    }
}
