package com.redbubble.ss.repository;

public final class ProductData {
    private final ProductId id;
    public final String title;
    public final String iaCode;

    ProductData(ProductId id, String title, String iaCode) {
        this.id = id;
        this.title = title;
        this.iaCode = iaCode;
    }

    public long getId() {
        return this.id.rawId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductData that = (ProductData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return iaCode != null ? iaCode.equals(that.iaCode) : that.iaCode == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (iaCode != null ? iaCode.hashCode() : 0);
        return result;
    }
}

final class ProductId {
    final long rawId;

    ProductId(long id) {
        this.rawId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductId productId = (ProductId) o;

        return rawId == productId.rawId;
    }

    @Override
    public int hashCode() {
        return (int) (rawId ^ (rawId >>> 32));
    }
}
