package com.example.springessentialguide.data.repository;

import com.example.springessentialguide.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUid(String uid); // email
}
