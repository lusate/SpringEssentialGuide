package com.example.springessentialguide.service.impl;

import com.example.springessentialguide.data.dto.ProductDto;
import com.example.springessentialguide.data.dto.ProductResponseDto;
import com.example.springessentialguide.data.entity.Product;
import com.example.springessentialguide.data.repository.ProductRepository;
import com.example.springessentialguide.data.service.ProductService;
import com.example.springessentialguide.data.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


/**
 * 스프링에서 객체를 주입받기 위해 @Extends를 사용해 Jnit5의 테스트에서 스프링 테스트 컨텍스트를 사용하도록 설정.
 * @Autowired 로 주입받는 ProductService를 주입받기 위해 직접 클래스를 @Import 어노테이션을 통해 사용.
 */

@ExtendWith(SpringExtension.class)
@Import(ProductServiceImpl.class)
public class saveProductServiceTest2 {
    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @BeforeEach
    public void setUpTest() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void saveProductTest() {
        // any는 Mock 객체의 동작을 정의하거나 검증하는단계에서 조건으로 특정 매개변수의 전달을 설정하지 않고
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
