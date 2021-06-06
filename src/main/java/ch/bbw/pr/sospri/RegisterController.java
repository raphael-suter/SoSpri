package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberFormData;
import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;

/**
 * Register Controller
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Controller
public class RegisterController {
    final MemberService memberservice;

    public RegisterController(MemberService memberservice) {
        this.memberservice = memberservice;
    }

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("memberFormData", new MemberFormData());

        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(@Validated MemberFormData memberFormData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        String username = memberFormData.getPrename().toLowerCase()
                + "."
                + memberFormData.getLastname().toLowerCase();

        if (memberservice.getByUserName(username) != null) {
            memberFormData.setMessage("Dieser Benutzername (" + username + ") existiert bereits.");
            model.addAttribute("memberFormData", memberFormData);

            return "register";
        }

        if (!memberFormData.getPassword().equals(memberFormData.getConfirmation())) {
            memberFormData.setMessage("Die Passwörter stimmen nicht überein.");
            model.addAttribute("memberFormData", memberFormData);

            return "register";
        }

        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(memberFormData.getPassword());

        memberservice.add(new Member(memberFormData.getPrename(), memberFormData.getLastname(), encodedPassword, username, "member"));
        model.addAttribute("message", "Willkommen bei SoSpri " + username + "!");

        return "registerconfirmed";
    }
}