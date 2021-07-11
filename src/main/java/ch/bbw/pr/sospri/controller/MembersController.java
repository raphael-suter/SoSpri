package ch.bbw.pr.sospri.controller;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Member Controller
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
@Controller
public class MembersController {
    private Logger logger = LoggerFactory.getLogger(MembersController.class);

    @Autowired
    MemberService memberservice;

    @GetMapping("/get-members")
    public String getRequestMembers(Model model) {
        logger.debug("/get-members: GET");
        model.addAttribute("members", memberservice.getAll());

        return "members";
    }

    @GetMapping("/edit-member")
    public String editMember(@RequestParam(name = "id", required = true) long id, Model model) {
        logger.debug("/edit-member: GET");

        Member member = memberservice.getById(id);
        model.addAttribute("member", member);

        return "editmember";
    }

    @PostMapping("/edit-member")
    public String editMember(Member member, Model model) {
        logger.debug("/edit-member: POST");

        Member value = memberservice.getById(member.getId());
        memberservice.update(value, member.getAuthority());

        return "redirect:/get-members";
    }

    @GetMapping("/delete-member")
    public String deleteMember(@RequestParam(name = "id", required = true) long id, Model model) {
        logger.debug("/delete-member: GET");
        memberservice.deleteById(id);

        return "redirect:/get-members";
    }
}
