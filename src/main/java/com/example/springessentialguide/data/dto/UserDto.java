package com.example.springessentialguide.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String role;
    private String name; // 사용자의 id를 담을 name값
    private String username; // 우리 서버에서 만들어 줄 username값
}
