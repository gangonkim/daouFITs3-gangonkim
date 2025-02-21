package org.example.servletex.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.vo.CommentVO;

import java.util.List;

//connection 대신 SQLSessionFactory를 DAO에 전달
public class CommentService {
    private SqlSessionFactory sqlSessionFactory;

    public CommentService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    // 댓글 추가
    public void addComment(CommentVO comment) {

        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.insert("example.MyBook.insertComment", comment);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            System.out.println("");
        }
    }

    // 특정 게시글의 댓글 목록 조회
    public List<CommentVO> selectCommentsByPostId(int boardno) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.selectList("example.MyBook.selectCommentsByPostId", boardno);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("댓글 목록 조회 중 오류 발생");
            return null;
        } finally {
            session.close();
        }
    }

    //deleteComment
    public int deleteComment(int no) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.delete("example.MyBook.deleteComment", no);
            System.out.println("댓글 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }
}

