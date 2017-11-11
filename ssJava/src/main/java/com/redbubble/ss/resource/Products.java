package com.redbubble.ss.resource;

import java.util.List;

public class Products {
    private final List<Product> products;
    private final int total;

    public Products(List<Product> products, int total) {
        this.products = products;
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Products products1 = (Products) o;

        if (total != products1.total) return false;
        return products != null ? products.equals(products1.products) : products1.products == null;
    }

    @Override
    public int hashCode() {
        int result = products != null ? products.hashCode() : 0;
        result = 31 * result + total;
        return result;
    }
}
