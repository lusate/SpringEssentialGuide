package com.example.springessentialguide.securityDto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    private String username;
    private String password;
}
