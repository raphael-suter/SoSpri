package ch.bbw.pr.sospri;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/logout-page")
    public String logout(Model model) {
        return "logout";
    }

    @RequestMapping("/change-password")
    public String change_password(Model model) {
        return "change_password";
    }
}