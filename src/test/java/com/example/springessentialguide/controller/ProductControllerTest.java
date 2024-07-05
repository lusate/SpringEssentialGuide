package com.example.springessentialguide.controller;

import com.example.springessentialguide.data.dto.ProductDto;
import com.example.springessentialguide.data.dto.ProductResponseDto;
import com.example.springessentialguide.data.service.impl.ProductServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductServiceImpl productService;

    /**
     * Mokito 테스트 코드 방식
     */
    @Test
    @DisplayName("MockMvc를 통한 Product 데이터 가져오기 테스트")
    void getProductTest() throws Exception {
        // Mock 객체를 설정
        // productService의 getProduct 메서드가 호출될 때 어떤 동작을 할지 정의.
        given(productService.getProduct(123L))
                .willReturn(new ProductResponseDto(123L, "pen", 5000, 2000));
        // 어떤 결과를 리턴할 것인지 정의.

        /**
         * MockMvc를 사용한 요청 수행 및 검증
         */
        String productId = "123";
        // MockMvc 객체를 사용하여 HTTP GET 요청을 "/product?number=123" 경로로 보냄
        mockMvc.perform(
                        get("/product?number=" + productId))
                .andExpect(status().isOk()) // 응답 상태가 200인지 검토
                .andExpect(jsonPath("$.number").exists()) // JSON 응답에 'number' 필드가 존재하는지 확인.
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.stock").exists())
                .andDo(print()); // 요청과 응답 내용을 콘솔에 출력. -> 요청과 응답의 전체 내용을 확인.

        /**
         * 메서드 호출 검증
         * verify : Mockito의 'verify' 메서드를 사용하여 getProduct 메서드가 '123L' 인자로 호출되었는지 확인.
         */
        verify(productService).getProduct(123L);
    }


    /**
     * createProduct() 메서드에 대한 테스트
     */
    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws Exception {
        given(productService.saveProduct(new ProductDto("pen", 5000, 2000)))
                .willReturn(new ProductResponseDto(12315L, "pen", 5000, 2000));

        ProductDto productDto = ProductDto.builder()
                .name("pen")
                .price(5000)
                .stock(2000)
                .build();

        Gson gson = new Gson();
        String content = gson.toJson(productDto);

        mockMvc.perform(
                        post("/product/post").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.stock").exists())
                .andDo(print());


        // verity() 실행됐는지 검증하는 역할
        verify(productService).saveProduct(new ProductDto("pen", 5000, 2000));
    }
}