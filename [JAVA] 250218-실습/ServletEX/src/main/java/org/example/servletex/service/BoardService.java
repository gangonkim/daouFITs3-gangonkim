package org.example.servletex.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.vo.BoardVO;
import org.example.servletex.vo.BookVO;
import org.example.servletex.vo.MemberVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //delete
    public int delete(int no) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.delete("example.MyBook.delete", no);
            System.out.println("게시글 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }





}
