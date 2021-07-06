package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.PasswordChangeFormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    private Logger logger = LoggerFactory.getLogger(DefaultController.class);
    private final WebSecurityConfig webSecurityConfig;
    private final MemberService memberservice;

    public DefaultController(MemberService memberservice, WebSecurityConfig webSecurityConfig) {
        this.memberservice = memberservice;
        this.webSecurityConfig = webSecurityConfig;
    }

    @RequestMapping("/login")
    public String login(Model model) {
        logger.debug("/login: REQUEST");
        return "login";
    }

    @RequestMapping("/logout-page")
    public String logout(Model model) {
        logger.debug("/logout-page: REQUEST");
        return "logout";
    }

    @RequestMapping("/change-password")
    public String change_password(Model model) {
        logger.debug("/change-password: REQUEST");
        model.addAttribute("passwordChangeFormData", new PasswordChangeFormData());

        return "change_password";
    }

    @PostMapping("/change-password")
    public String setPassword(@Validated PasswordChangeFormData passwordChangeFormData, BindingResult bindingResult, Model model) {
        logger.debug("/change-password: POST");

        if (bindingResult.hasErrors()) {
            return "change_password";
        }

        if (!passwordChangeFormData.getPassword().equals(passwordChangeFormData.getReppassword())) {
            passwordChangeFormData.setMessage("Die Passwörter stimmen nicht überein.");
            model.addAttribute("passwordChangeFormData", passwordChangeFormData);

            return "change_password";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member tmpMember = null;

        if (principal instanceof UserDetails) {
            tmpMember = memberservice.getByUserName(((UserDetails) principal).getUsername());
        }

        String encodedPassword = webSecurityConfig.passwordEncoder().encode(passwordChangeFormData.getPassword());

        tmpMember.setPassword(encodedPassword);
        tmpMember.setPw_changed(System.currentTimeMillis());

        memberservice.update(tmpMember);
        model.addAttribute("message", "Dein Passwort wurde erfolgreich zurückgesetzt " + tmpMember.getUsername() + "!");

        return "changeconfirmed";
    }
}