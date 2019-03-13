package com.dhht.annotation;

/**
 * @author HanPei
 * @date 2019/3/12  下午2:49
 */
public class User {
    String name;
    int id;

    @Deprecated
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
