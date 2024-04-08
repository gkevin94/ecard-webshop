package com.ecard.ecardwebshop.category;

public class Category {
    private long id;
    private String name;
    private long ordinal;

    public Category(long id, String name, long ordinal) {
        this.id = id;
        this.name = name;
        this.ordinal = ordinal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(long ordinal) {
        this.ordinal = ordinal;
    }
}
