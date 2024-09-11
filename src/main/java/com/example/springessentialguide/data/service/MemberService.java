package com.example.springessentialguide.data.service;

<<<<<<< HEAD
import com.example.springessentialguide.data.dto.SignUpDto;
import com.example.springessentialguide.data.entity.Member;
import com.example.springessentialguide.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUpProcess(SignUpDto signUpDto) {
        String username = signUpDto.getUsername();
        String password = signUpDto.getPassword();

        Boolean isExist = memberRepository.existsByUsername(username);

        if(isExist) return;

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(bCryptPasswordEncoder.encode(password));
        member.setRole("ROLE_ADMIN");

        memberRepository.save(member);
    }
=======
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

>>>>>>> 938048e (merge basic JWT)
}
