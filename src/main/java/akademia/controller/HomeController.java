package akademia.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String securedAdminPage() {
        return "secured";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String securedUserPage() {
        return "user_secured";
    }
}
