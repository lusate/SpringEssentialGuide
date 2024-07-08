package com.example.springessentialguide.data;

import com.example.springessentialguide.data.entity.Product;
import com.example.springessentialguide.data.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @DataJpaTest 어노테이션 : JPA와 관련된 설정만 로드해서 테스트를 진행.
 * 테스트 코드가 종료되면 자동으로 DB의 롤백이 진행된다.
 * 기본값으로 임베디드 DB를 사용. 다른 DB를 사용하려면 별도의 설정을 거쳐 사용 가능.
 */

/**
 * @AutoConfigureTestDatabase(replace = Replace.NONE)
 * 기본값은 Replace.ANY이고 임베디드 메모리 데이터베이스를 사용한다.
 * NONE이면 애플리케이션에서 실제로 사용하는 DB로 테스트가 가능.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveTest() {
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(1000);

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertEquals(product.getName(), savedProduct.getName());

        assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    void selectTest() {
        // given
        Product product = new Product();

    }
}
