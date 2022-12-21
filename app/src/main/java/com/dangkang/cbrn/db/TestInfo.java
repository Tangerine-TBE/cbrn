package com.dangkang.cbrn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
@Entity
public class TestInfo {
    @Id(autoincrement = true)
    private Long id;
    private String fileName;
    private String filePath;
    private long time;
    private boolean isSelected;
    @Generated(hash = 627050633)
    public TestInfo(Long id, String fileName, String filePath, long time, boolean isSelected) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.time = time;
        this.isSelected = isSelected;
    }
    @Generated(hash = 1499832600)
    public TestInfo() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean getIsSelected() {
        return this.isSelected;
    }
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
