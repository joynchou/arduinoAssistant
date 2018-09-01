package com.example.joyh.arduinoAssistant.domain.model.impl;

import com.example.joyh.arduinoAssistant.domain.model.BoardBeanModel;

/**
 * Created by joyn on 2018/8/21 0021.
 */

public class BoardBeanModelImpl implements BoardBeanModel {
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

        BoardBeanModelImpl that = (BoardBeanModelImpl) o;

        if (boardId != that.boardId) return false;
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
        return "BoardBeanModelImpl{" +
                "boardId=" + boardId +
                ", boardName='" + boardName + '\'' +
                '}';
    }
}
