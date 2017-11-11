package com.redbubble.ss.repository;


import java.util.List;

final public class ProductsData {
    public final List<ProductData> productDataList;
    public final int total;

    public ProductsData(List<ProductData> productDataList, int total) {
        this.productDataList = productDataList;
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsData that = (ProductsData) o;

        if (total != that.total) return false;
        return productDataList != null ? productDataList.equals(that.productDataList) : that.productDataList == null;
    }

    @Override
    public int hashCode() {
        int result = productDataList != null ? productDataList.hashCode() : 0;
        result = 31 * result + total;
        return result;
    }
}
