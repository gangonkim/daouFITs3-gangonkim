package org.example.servletex.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.vo.BoardLikeVO;
import org.example.servletex.vo.BoardVO;

import java.util.List;

//connection 대신 SQLSessionFactory를 DAO에 전달
public class BoardLikeService {

    private SqlSessionFactory sqlSessionFactory;

    public BoardLikeService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    // 좋아요 추가
    public boolean addLike(String memberId, int boardNo) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit
            BoardLikeVO like = new BoardLikeVO(memberId, boardNo);
            int result = session.insert("example.MyBook.addLike", like);
            return result > 0;
        }
    }

    // 좋아요 취소
    public boolean removeLike(String memberId, int boardNo) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            BoardLikeVO like = new BoardLikeVO(memberId, boardNo);
            int result = session.delete("example.MyBook.removeLike", like);
            return result > 0;
        }
    }

    // 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
    public boolean isLiked(String memberId, int boardNo) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Integer count = session.selectOne("example.MyBook.isLiked", new BoardLikeVO(memberId, boardNo));
            return count != null && count > 0;
        }
    }

    // 게시글의 좋아요 개수 조회
    public int getLikeCount(int boardNo) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("example.MyBook.getLikeCount", boardNo);
        }
    }



}
