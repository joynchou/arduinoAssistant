package com.example.joyh.arduinoAssistant.domain.model.impl;

/**
 * Created by joyn on 2018/8/21 0021.
 * 开发板对象，保存了开发板的各种信息以及各类资源的保存路径
 */

public class BoardBeanModel {
    //板子的唯一id
    private int boardId;
    //板子的名字
    private String boardName;
    //板子的简介
    private String intro;
    //板子图片路径
    private String picURL;
    private String picPath;
    //引脚图路径
    private String pinFigurePath;
    //原理图路径
    private String schematicPath;
    //pcb路径
    private String PCBPath;
    //对象所拥有的资源总数
    private int resourceNum;

    public int getResourceNum() {
        resourceNum=0;
        if(!intro.isEmpty()){
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

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
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
    public int hashCode() {
        int result = boardId;
        result = 31 * result + (boardName != null ? boardName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BoardBeanModel{" +
                "boardId=" + boardId +
                ", boardName='" + boardName + '\'' +
                '}';
    }
}
