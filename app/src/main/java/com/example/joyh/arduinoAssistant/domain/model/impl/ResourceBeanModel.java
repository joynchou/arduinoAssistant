package com.example.joyh.arduinoAssistant.domain.model.impl;

import java.io.Serializable;

/**
 * Created by joyn on 2018/9/17 0017.
 * 资源对象
 */

public class ResourceBeanModel implements Serializable {

    private String resourceName;
    private int resourceType;
    //是否可分享
    private boolean sharable;
    //资源的保存路径
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isSharable() {
        return sharable;
    }

    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }

    @Override
    public String toString() {
        return "ResourceBeanModel{" +
                "resourceName='" + resourceName + '\'' +
                ", resourceType=" + resourceType +
                ", sharable=" + sharable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceBeanModel that = (ResourceBeanModel) o;

        if (resourceType != that.resourceType) return false;
        return resourceName != null ? resourceName.equals(that.resourceName) : that.resourceName == null;
    }

    @Override
    public int hashCode() {
        int result = resourceName != null ? resourceName.hashCode() : 0;
        result = 31 * result + resourceType;
        return result;
    }
}
