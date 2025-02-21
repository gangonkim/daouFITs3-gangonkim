package org.example.servletex.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.vo.MemberVO;

import java.util.HashMap;
import java.util.Map;

//connection 대신 SQLSessionFactory를 DAO에 전달
public class MemberService {

    private SqlSessionFactory sqlSessionFactory;

    public MemberService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    public MemberVO login(String id, String password) {
        MemberVO vo = new MemberVO();

        SqlSession session = sqlSessionFactory.openSession();
        // Create a map to store both the keyword and priceLimit
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);

        try {
            // Pass the map as the parameter
            vo = session.selectOne("example.MyBook.selectByIdPw", params);
            System.out.println("Keyword: " + id);
            System.out.println("Price Limit: " + password);
            System.out.println("SQL Query executed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return vo;
    }


}
