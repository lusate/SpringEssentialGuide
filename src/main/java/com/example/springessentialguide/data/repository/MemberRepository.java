package com.example.springessentialguide.data.repository;

import com.example.springessentialguide.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getByUid(String uid); // email

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
