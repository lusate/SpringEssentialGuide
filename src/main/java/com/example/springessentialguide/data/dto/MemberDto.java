package com.example.springessentialguide.data.dto;

import com.example.springessentialguide.data.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String address;
    private String phone;
    private String profileImg;


    static public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .address(member.getAddress())
                .phone(member.getPhone())
                .profileImg(member.getProfileImg()).build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .address(address)
                .phone(phone)
                .profileImg(profileImg).build();
    }
}