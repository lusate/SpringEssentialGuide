package com.example.springessentialguide.data.dto;

import java.util.Map;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> attributes;

    public NaverResponse(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response"); // response 라는 키에 대해서 값을 넣어줌
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString(); // id라는 키에 대한 값
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }
}
