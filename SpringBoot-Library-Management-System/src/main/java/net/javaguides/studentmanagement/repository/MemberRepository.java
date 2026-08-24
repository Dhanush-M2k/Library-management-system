package net.javaguides.studentmanagement.repository;

import net.javaguides.studentmanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {}
