package com.example.joyh.arduinoAssistant.domain.model.impl;

import java.io.Serializable;

/**
 * Created by joyn on 2018/8/21 0021.
 * 板子对象
 * 开发板对象，保存了开发板的各种信息以及各类资源的保存路径
 */

public class BoardBeanModel implements Serializable {
   
    //板子的名字
    private String boardName;
    //板子的简介
    private String intro;
    //板子图片网络路径
    private String picURL;
    //板子图片本地路径
    private String picPath;
    //引脚图路径
    private String pinFigurePath;
    //原理图路径
    private String schematicPath;
    //pcb路径
    private String PCBPath;
    //对象所拥有的资源总数
    private int resourceNum;
    /**
     * 获取资源数目
     * @return 资源数目
     */
    public int getResourceNum() {
        resourceNum=0;
        if(intro!=null){
            resourceNum++;
        }
        if(picPath!=null){
            resourceNum++;
        }
        if(pinFigurePath!=null){
            resourceNum++;
        }
        if(schematicPath!=null){
            resourceNum++;
        }
        if(PCBPath!=null){
            resourceNum++;
        }
        return resourceNum;
    }



    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }



    public String getPinFigurePath() {
        return pinFigurePath;
    }

    public void setPinFigurePath(String pinFigurePath) {
        this.pinFigurePath = pinFigurePath;
    }

    public String getSchematicPath() {
        return schematicPath;
    }

    public void setSchematicPath(String schematicPath) {
        this.schematicPath = schematicPath;
    }

    public String getPCBPath() {
        return PCBPath;
    }

    public void setPCBPath(String PCBPath) {
        this.PCBPath = PCBPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardBeanModel that = (BoardBeanModel) o;

        return boardName != null ? boardName.equals(that.boardName) : that.boardName == null;
    }

    @Override
    public String toString() {
        return "BoardBeanModel{" +
                "boardName='" + boardName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return boardName != null ? boardName.hashCode() : 0;
    }
}
