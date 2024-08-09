package com.example.springessentialguide.jwt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 클라이언트에 토큰을 보내기 위해 JwtToken DTO를 생성.
 */

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {
    @NotNull
    private String grantType; // JWT에 대한 인증 타입.

    @NotNull
    private String accessToken; // 인증된 사용자가 특정 리소스에 접근할 때 사용되는 토큰.

    @NotNull
    private String refreshToken; // Access Token의 갱신을 위해 사용되는 토큰.
}
