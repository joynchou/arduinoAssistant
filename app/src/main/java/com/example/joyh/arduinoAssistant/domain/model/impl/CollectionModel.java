package com.example.joyh.arduinoAssistant.domain.model.impl;

import java.io.Serializable;

/**
 * Created by joyn on 2018/9/5 0005.
 * 收藏项目的bean对象，保存了可收藏项目信息
 */

public class CollectionModel implements Serializable {
    /**
     * 收藏对象的名字
     */
    private String name;
    /**
     * 收藏对象的类型，将是一个预定类型
     */
    private int type;
    /**
     * 是否已经被收藏，
     */
    private boolean state;

    public CollectionModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public CollectionModel(String name, int type, boolean state) {
        this.name = name;
        this.type = type;
        this.state = state;
    }


    @Override
    public String toString() {
        return "CollectionModel{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionModel that = (CollectionModel) o;

        if (type != that.type) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + type;
        return result;
    }
}
