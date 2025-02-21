package org.example.servletex.vo;

import java.sql.Timestamp;

public class BoardLikeVO {
    private String memberid;
    private int boardno;


    public BoardLikeVO() {

    }

    public BoardLikeVO(String memberid, int boardno) {
        this.memberid = memberid;
        this.boardno = boardno;
    }
    public String getMemberid() {
        return memberid;
    }
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }
    public int getBoardno() {
        return boardno;
    }
    public void setBoardno(int boardno) {
        this.boardno = boardno;
    }
}
