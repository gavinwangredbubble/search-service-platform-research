package com.redbubble.ss.repository;

public interface ProductRepository {
    ProductsData search(String query, int page, int pageSize);
}


