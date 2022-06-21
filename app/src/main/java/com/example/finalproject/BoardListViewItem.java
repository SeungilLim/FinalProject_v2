package com.example.finalproject;


import android.graphics.Bitmap;

public class BoardListViewItem {

    private String title;
    private int boardId;
    private int price;
    private String image;
    private String date;

    public BoardListViewItem(String title, int boardId, int price, String image, String date) {
        this.title = title;
        this.boardId = boardId;
        this.price = price;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
