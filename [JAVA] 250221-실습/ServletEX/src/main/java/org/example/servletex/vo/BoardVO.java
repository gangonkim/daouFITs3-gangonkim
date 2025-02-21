package org.example.servletex.vo;

import oracle.sql.TIMESTAMP;

import java.sql.Timestamp;

public class BoardVO {
    private int no;
    private String title;
    private String text;
    private Timestamp writedate;
    private int readcnt;
    private String writer;
    private int likecnt;

    public BoardVO() {

    }

    public BoardVO(int no, String title, String text, Timestamp writeDate, int readcnt, String writer) {

        this.no = no;
        this.title = title;
        this.text = text;
        this.writedate = writeDate;
        this.readcnt = readcnt;
        this.writer = writer;
        this.likecnt = 0;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getWritedate() {
        return writedate;
    }

    public void setWrite_date(Timestamp writedate) {
        this.writedate = writedate;
    }

    public int getReadcnt() {
        return readcnt;
    }

    public void setReadcnt(int readcnt) {
        this.readcnt = readcnt;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getLikecnt() {
        return likecnt;
    }
    public void setLikecnt(int likecnt) {
        this.likecnt = likecnt;
    }
}
