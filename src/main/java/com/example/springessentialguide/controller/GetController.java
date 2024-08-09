package com.example.springessentialguide.controller;

import com.example.springessentialguide.data.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        return "Hello World";
    }

    @GetMapping("value = /name")
    public String getValue() {
        return "Flature";
    }

    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable) {
        return variable;
    }

    // 명시적 매핑 -> "variable"가 URL과 이름이 같아야 함. 매개변수명은 달라도 됨.
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable") String variable) {
        return variable;
    }

    // ~~localhost:8080/api/v1/get-api/request?key1=value&key2=value2
    @GetMapping(value = "/request")
    public String getRequestParam(
            @RequestParam Map<String , String> param
    ) {
        StringBuilder sb = new StringBuilder();

        // entrySet은 Map 전체 출력
        // for (Map.Entry<String, String> entry : map.entrySet()) {}
        param.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + " \n");
        });

        return sb.toString();
    }

    // Dto 활용한 GET 메서드 구현
    @GetMapping("value = /request2")
    public String getRequestParam(MemberDto memberDto) {
        return memberDto.toString();
    }

    /**
     * Swagger를 활용한 Get 메서드
     * @ApiOperation : 대상 API의 설명을 작성하기 윟나 어노테이션입니다.
     * @ApiParam : 매개변쉥 대한 설명 및 설정을 위한 어노테이션입니다. 메서드의 매개변수뿐 아니라 DTO 객체를 매개변수로 사용할 경우
     * DTO 클래스 내의 매개변수에도 정의할 수 있습니다.
     */
    @ApiOperation(value = "GET 메서드 예제", notes = "@RequestParam을 활용한 GET Method")
    @GetMapping(value = "/requestSwagger")
    public String getRequestParamSwagger(
            @ApiParam(value = "이름, required = true") @RequestParam String name,
            @ApiParam(value = "이메일, required = true") @RequestParam String email,
            @ApiParam(value = "회사, required = true") @RequestParam String organization
    ) {
        return name + " " + email + " " + organization;
    }
}
