package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Register Controller
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Controller
public class RegisterController {
    @Autowired
    MemberService memberservice;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());

        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(RegisterMember registerMember, Model model) {
        System.out.println("postRequestRegistMembers: registerMember");
        System.out.println(registerMember);

        //Hier ergänzen

        return "registerconfirmed";
    }
}