package net.javaguides.studentmanagement.service.impl;

import net.javaguides.studentmanagement.model.Member;
import net.javaguides.studentmanagement.repository.MemberRepository;
import net.javaguides.studentmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> getAllMembers() { return memberRepository.findAll(); }

    @Override
    public Member saveMember(Member member) { return memberRepository.save(member); }

    @Override
    public Member getMemberById(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    @Override
    public void deleteMemberById(long id) { memberRepository.deleteById(id); }
}
