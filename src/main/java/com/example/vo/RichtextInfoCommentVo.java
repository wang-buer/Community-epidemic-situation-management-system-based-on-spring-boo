package com.example.vo;

import com.example.entity.RichtextInfoComment;
import java.util.List;

public class RichtextInfoCommentVo extends RichtextInfoComment {

    private String foreignName;

    private List<RichtextInfoCommentVo> children;

    public List<RichtextInfoCommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<RichtextInfoCommentVo> children) {
        this.children = children;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }
}