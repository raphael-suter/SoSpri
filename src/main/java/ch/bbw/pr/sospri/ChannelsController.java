package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

/**
 * Channels Controller
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Controller
public class ChannelsController {
    @Autowired
    MessageService messageservice;

    @Autowired
    MemberService memberservice;

    @GetMapping("/get-channel")
    public String getRequestChannel(Model model) {
        System.out.println("getRequestChannel");
        model.addAttribute("messages", messageservice.getAll());

        Message message = new Message();
        model.addAttribute("message", message);

        return "channel";
    }

    @PostMapping("/add-message")
    public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult) {
        System.out.println("postRequestChannel(): message: " + message.toString());

        if (bindingResult.hasErrors()) {
            System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
            model.addAttribute("messages", messageservice.getAll());
            return "channel";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member tmpMember = null;

        if (principal instanceof UserDetails) {
            tmpMember = memberservice.getByUserName(((UserDetails) principal).getUsername());
        }

        message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
        message.setOrigin(new Date());
        System.out.println("message: " + message);
        messageservice.add(message);

        return "redirect:/get-channel";
    }
}
