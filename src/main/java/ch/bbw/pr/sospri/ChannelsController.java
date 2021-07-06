package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(ChannelsController.class);

    final
    MessageService messageservice;

    final
    MemberService memberservice;

    public ChannelsController(MessageService messageservice, MemberService memberservice) {
        this.messageservice = messageservice;
        this.memberservice = memberservice;
    }

    @GetMapping("/get-channel")
    public String getRequestChannel(Model model) {
        logger.debug("/get-channel: GET");
        model.addAttribute("messages", messageservice.getAll());

        Message message = new Message();
        model.addAttribute("message", message);

        return "channel";
    }

    @PostMapping("/add-message")
    public String postRequestChannel(Model model, @ModelAttribute @Valid Message message, BindingResult bindingResult) {
        logger.debug("/add-message: POST");

        if (bindingResult.hasErrors()) {
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
        messageservice.add(message);

        return "redirect:/get-channel";
    }
}
