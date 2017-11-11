package com.redbubble.ss.resource;

final public class Product {
    private final long id;
    private final String title;
    private final String iaCode;

    public Product(long id, String title, String iaCode) {
        this.id = id;
        this.title = title;
        this.iaCode = iaCode;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIaCode() {
        return iaCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (title != null ? !title.equals(product.title) : product.title != null) return false;
        return iaCode != null ? iaCode.equals(product.iaCode) : product.iaCode == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (iaCode != null ? iaCode.hashCode() : 0);
        return result;
    }
}
