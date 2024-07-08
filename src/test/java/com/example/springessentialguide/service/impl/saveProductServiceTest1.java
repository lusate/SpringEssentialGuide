package com.example.springessentialguide.service.impl;

import com.example.springessentialguide.data.dto.ProductDto;
import com.example.springessentialguide.data.dto.ProductResponseDto;
import com.example.springessentialguide.data.entity.Product;
import com.example.springessentialguide.data.repository.ProductRepository;
import com.example.springessentialguide.data.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class saveProductServiceTest1 {
    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUpTest() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void saveProductTest() {
        // any는 Mock 객체의 동작을 정의하거나 검증하는 단계에서 조건으로 특정 매개변수의 전달을 설정하지 않고
        // 메서드의 실행만을 확인하거나 좀 더 큰 범위의 클래스 객체를 매개변수로 전달받는 등의 상황에 사용.
        Mockito.when(productRepository.save(any(Product.class)))
                .then(returnsFirstArg());


        ProductResponseDto productResponseDto = productService.saveProduct(
                new ProductDto("펜", 1000, 1234)
        );

        Assertions.assertEquals(productResponseDto.getName(), "펜");
        Assertions.assertEquals(productResponseDto.getPrice(), 1000);
        Assertions.assertEquals(productResponseDto.getStock(), 1234);

        verify(productRepository).save(any());
    }
}
