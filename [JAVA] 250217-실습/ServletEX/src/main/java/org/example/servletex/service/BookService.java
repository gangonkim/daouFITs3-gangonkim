package org.example.servletex.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.vo.BookVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//connection 대신 SQLSessionFactory를 DAO에 전달
public class BookService {

    private SqlSessionFactory sqlSessionFactory;

    public BookService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    public List<BookVO> selectByKeyword(String keyword, int priceLimit) {
        List<BookVO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        // Create a map to store both the keyword and priceLimit
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("price", priceLimit);

        try {
            // Pass the map as the parameter
            list = session.selectList("example.MyBook.selectByKeyword", params);
            System.out.println("Keyword: " + keyword);
            System.out.println("Price Limit: " + priceLimit);
            System.out.println("SQL Query executed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    // Method to get book details by ISBN
    public BookVO selectByIsbn(String isbn) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.selectOne("example.MyBook.selectByIsbn", isbn);
        } finally {
            session.close();
        }
    }

    //insert
    public int insertBook(BookVO book) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.insert("example.MyBook.insertBook", book);
            System.out.println("책 등록 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }


    //update
    public int updateBook(BookVO book) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.update("example.MyBook.updateBook", book);
            System.out.println("책 정보 업데이트 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }


    //delete
    public int deleteBook(String bisbn) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession(true)) { // Auto-commit 설정
            result = session.delete("example.MyBook.deleteBook", bisbn);
            System.out.println("책 삭제 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 성공하면 1, 실패하면 0
    }


}
