package example.dao;

import example.vo.BookVO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

//connection 대신 SQLSessionFactory를 DAO에 전달
public class BookDAO {

    private SqlSessionFactory sqlSessionFactory;

    public BookDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    //select
    public List<BookVO> selectByKeyword(String keyword) {
        List<BookVO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        //이 SQL세션에 트랜잭션이 걸림
        try {
            list = session.selectList("example.MyBook.selectByKeyword", keyword);
            System.out.println("sql수행");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
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
