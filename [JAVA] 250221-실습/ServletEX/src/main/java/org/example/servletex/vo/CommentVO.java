package org.example.servletex.vo;

import java.sql.Timestamp;

public class CommentVO {

    private String commentno;
    private String writer;
    private Timestamp writedate;
    private String content;
    private String boardno;

    public CommentVO() {

    }

    public CommentVO(String commentno, String writer, Timestamp writedate, String content, String boardno) {
        this.commentno = commentno;
        this.writer = writer;
        this.writedate = writedate;
        this.content = content;
        this.boardno = boardno;
    }

    public String getCommentno() {
        return commentno;
    }
    public void setCommentno(String commentno) {
        this.commentno = commentno;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getWritedate() {
        return writedate;
    }
    public void setWritedate(Timestamp writedate) {
        this.writedate = writedate;
    }

    public String getBoardno() {
        return boardno;
    }
    public void setBoardno(String boardno) {
        this.boardno = boardno;
    }


}
