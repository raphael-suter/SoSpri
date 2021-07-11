package ch.bbw.pr.sospri.controller;

import ch.bbw.pr.sospri.ReCaptchaValidationService;
import ch.bbw.pr.sospri.WebSecurityConfig;
import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberFormData;
import ch.bbw.pr.sospri.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Register Controller
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Controller
public class RegisterController {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final WebSecurityConfig webSecurityConfig;
    private final MemberService memberservice;

    @Autowired
    private ReCaptchaValidationService validator;

    public RegisterController(MemberService memberservice, WebSecurityConfig webSecurityConfig) {
        this.memberservice = memberservice;
        this.webSecurityConfig = webSecurityConfig;
    }

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        logger.debug("/get-register: GET");
        model.addAttribute("memberFormData", new MemberFormData());

        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(@Validated MemberFormData memberFormData, BindingResult bindingResult, @RequestParam(name = "g-recaptcha-response")
            String captcha, Model model) {
        logger.debug("/get-register: POST");

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!validator.validateCaptcha(captcha)) {
            memberFormData.setMessage("Captcha wurde nicht verifiziert!");
            model.addAttribute("memberFormData", memberFormData);

            return "register";
        }

        String username = memberFormData.getPrename().toLowerCase()
                + "."
                + memberFormData.getLastname().toLowerCase();

        String output = username;

        for (int count = 2; true; count++) {
            try {
                memberservice.getByUserName(output);
            } catch (UsernameNotFoundException usernameNotFoundException) {
                break;
            }

            output = username + count;
            logger.info("Benutzername musste um Zahl ergänzt werden: " + output);
        }

        if (!memberFormData.getPassword().equals(memberFormData.getConfirmation())) {
            memberFormData.setMessage("Die Passwörter stimmen nicht überein.");
            model.addAttribute("memberFormData", memberFormData);

            return "register";
        }

        String encodedPassword = webSecurityConfig.passwordEncoder().encode(memberFormData.getPassword());
        memberservice.add(new Member(memberFormData.getPrename(), memberFormData.getLastname(), encodedPassword, output, "member", System.currentTimeMillis()));
        model.addAttribute("message", "Willkommen bei SoSpri " + output + "!");

        return "registerconfirmed";
    }
}