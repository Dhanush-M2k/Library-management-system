package net.javaguides.studentmanagement.service;

import net.javaguides.studentmanagement.model.Member;
import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    Member saveMember(Member member);
    Member getMemberById(long id);
    void deleteMemberById(long id);
}
