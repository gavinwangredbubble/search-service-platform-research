package com.redbubble.ss.service;

import com.redbubble.ss.repository.ProductRepository;
import com.redbubble.ss.repository.ProductsData;
import com.redbubble.ss.resource.Product;
import com.redbubble.ss.resource.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public Products search(String query, int page, int pageSize) {
        ProductsData productsData = productRepository.search(query, page, pageSize);

        List<Product> products = productsData.productDataList
                .stream()
                .map(productData -> new Product(productData.getId(), productData.title, productData.iaCode))
                .collect(Collectors.toList());

        return new Products(products, products.size());
    }
}

