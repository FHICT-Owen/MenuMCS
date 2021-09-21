package com.digitalmenu.menuservice.category;
public class Category {
    private long Id;
    private String Name;
    public Category() {
    }
    public Category(long id, String name) {
        Id = id;
        Name = name;
    }
    public long getId() {
        return Id;
    }
    public void setId(long id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
}
