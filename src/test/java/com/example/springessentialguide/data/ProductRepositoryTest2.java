package com.example.springessentialguide.data;

import com.example.springessentialguide.data.entity.Product;
import com.example.springessentialguide.data.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProductRepositoryTest2 {
    @Autowired
    ProductRepository productRepository;

    @Test
    void basicCRUDTest() {
        /** create */
        Product givenProduct = Product.builder()
                .name("노트")
                .price(1000)
                .stock(500)
                .build();

        //when
        Product savedProduct = productRepository.save(givenProduct);

        // then
        assertEquals(savedProduct.getNumber(), givenProduct.getNumber());
        assertEquals(savedProduct.getName(), givenProduct.getName());
        assertEquals(savedProduct.getPrice(), givenProduct.getPrice());
        assertEquals(savedProduct.getStock(), givenProduct.getStock());


        /** update */
        Product selectedProduct = productRepository.findById(savedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        Product foundProduct = productRepository.findById(selectedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        foundProduct.setName("장난감");
        Product updateProduct = productRepository.save(foundProduct);

        // then
        assertEquals(updateProduct.getName(), "장난감");

        /** delete */
        // when
        productRepository.delete(updateProduct);

        // then
        assertFalse(productRepository.findById(selectedProduct.getNumber()).isPresent());
    }


}
