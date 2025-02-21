package org.example.servletex.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.vo.BoardVO;

import java.util.List;

//connection 대신 SQLSessionFactory를 DAO에 전달
public class BoardService {

    private SqlSessionFactory sqlSessionFactory;

    public BoardService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    //목록
    public List<BoardVO> list() {
        List<BoardVO> list = null;

        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("example.MyBook.list");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    // 제목 또는 내용으로 검색된 게시글 목록 조회
    public List<BoardVO> searchList(String searchQuery) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            if (searchQuery == null || searchQuery.isEmpty()) {
                System.out.println("검색어없음");
                return session.selectList("example.MyBook.list"); // 모든 게시글 조회
            } else {
                searchQuery = "%" + searchQuery + "%";  // 검색어 양쪽에 % 추가
                return session.selectList("example.MyBook.searchBoard", searchQuery); // 검색된 게시글 조회
            }
        } finally {
            session.close();
        }
    }

    // 상세 조회 (id로 검색)
    public BoardVO detail(int id) {
        BoardVO board = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            board = session.selectOne("example.MyBook.detail", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return board;
    }

    public void increaseReadCount(int no) {
        try (SqlSession session = sqlSessionFactory.openSession()) { // Auto-commit
            session.update("example.MyBook.increaseReadCount", no);
            session.commit();
            System.out.println("updateReadCount");
        }
    }


    public int insert(BoardVO board) {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            result = session.insert("example.MyBook.insert", board);
            session.commit();
            System.out.println("게시글 등록 완료");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    //update
    public int update(BoardVO board) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.update("example.MyBook.update", board);
            System.out.println("게시글 업데이트 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }

    //deleteComment
    public int delete(int no) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.delete("example.MyBook.deleteComment", no);
            System.out.println("게시글 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }

    public int toggleLike(int postId, boolean liked) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {

            int result = 0;
            // 좋아요가 이미 눌렸다면 취소하고, 아니면 추가
            if (liked) {
                result = session.update("example.MyBook.decrementLikes", postId);
            } else {
                result = session.update("example.MyBook.incrementLikes", postId);  // 좋아요 추가 (수 증가)
            }


            // 변경 사항을 커밋
            session.commit();

            // 최신 좋아요 수 반환
            return session.selectOne("example.MyBook.getLikes", postId);
        }
    }

    // 좋아요 증가
    public int incrementLikes(int postId) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.update("example.MyBook.incrementLikes", postId);
            System.out.println("좋아요 증가 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int decrementLikes(int postId) {
        int result = 0;
        try {
            SqlSession session = sqlSessionFactory.openSession(true);
            session = sqlSessionFactory.openSession(true);
            result = session.update("example.MyBook.decrementLikes", postId);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }





}
