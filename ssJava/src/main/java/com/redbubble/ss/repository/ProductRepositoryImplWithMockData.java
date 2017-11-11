package com.redbubble.ss.repository;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ProductRepositoryImplWithMockData implements ProductRepository {

    @Override
    public ProductsData search(String query, int page, int pageSize) {
        return new ProductsData(
                Arrays.asList(
                        new ProductData(new ProductId(21392258L), "Cat Sticker", "u-sticker"),
                        new ProductData(new ProductId(15563459L), "Cat Eye Galaxy Sticker", "u-sticker")
                ),
                2
        );
    }
}
