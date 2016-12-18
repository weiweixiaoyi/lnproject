package com.example.guo.lnproject.bean;

/**
 * Created by Administrator on 2016/3/9.
 */
public class RecommendDrinkEntity {
    private int title;
    private int content;
    private int image;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    private String step;
    public RecommendDrinkEntity(int title, int content) {
        this.title = title;
        this.content = content;
    }
    public RecommendDrinkEntity(int title, int content,int image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public RecommendDrinkEntity(){}

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
