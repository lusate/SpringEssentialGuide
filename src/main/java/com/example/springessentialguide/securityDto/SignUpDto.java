package com.example.springessentialguide.securityDto;

import com.example.springessentialguide.data.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class SignUpDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String profileImg;
    private List<String> roles;

    public Member toEntity(String encodedPassword, List<String> roles) {
        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .address(address)
                .uid(email)
                .phone(phone)
                .profileImg(profileImg)
                .roles(roles)
                .build();
    }
}
