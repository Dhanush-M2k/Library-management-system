package net.javaguides.studentmanagement.controller;

import net.javaguides.studentmanagement.model.Member;
import net.javaguides.studentmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) { this.memberService = memberService; }

    @GetMapping
    public String viewMemberList(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members";
    }

    @GetMapping("/new")
    public String showNewMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "new_member";
    }

    @PostMapping
    public String saveMember(@ModelAttribute("member") Member member) {
        memberService.saveMember(member);
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("member", memberService.getMemberById(id));
        return "update_member";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable long id, @ModelAttribute("member") Member member) {
        member.setId(id);
        memberService.saveMember(member);
        return "redirect:/members";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            memberService.deleteMemberById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Member deleted successfully.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "This member can't be deleted because they have existing book orders linked to them. Remove those orders first.");
        }
        return "redirect:/members";
    }
}
