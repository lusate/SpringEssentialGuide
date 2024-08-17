package com.example.springessentialguide.data.repository;

import com.example.springessentialguide.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByUsername(String username);

    Member findByUsername(String username);
}
