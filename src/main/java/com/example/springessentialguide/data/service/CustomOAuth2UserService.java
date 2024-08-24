package com.example.springessentialguide.data.service;

import com.example.springessentialguide.data.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2Uer가 뭐야? : {}", oAuth2User);

        System.out.println(oAuth2User);

        // 사용자가 인증을 시도한 OAuth2 클라이언트의 고유 식별자(등록 ID)
        // 일반적으로 Google, Naver, Facebook 등과 같은 특정 OAuth2 프로바이더의 이름으로 설정
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // 해당 DTO의 데이터를 받음.
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듦.
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        log.info("username: {}", username);

        UserDto userDto = new UserDto();
        userDto.setUsername(username); // 우리 서버에서 만들어 줄 username값
        userDto.setName(oAuth2Response.getName()); //  사용자의 id를 담을 name값
        userDto.setRole("ROLE_USER");

        // 우리 서버에서 특정할 수 있는 username이 있고 이것을 DTO에 담아서 넘겨주면 로그인이 완성됩니다.
        return new CustomOAuth2User(userDto); // 이것도 DTO
    }
}
