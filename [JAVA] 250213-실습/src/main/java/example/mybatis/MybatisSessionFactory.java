package example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

// SqlSessionFactory이라는 객체를 통해 sqlSession을 뽑아낼 수 있음
//sqlSession을 통해 SQL문을 실행
public class MybatisSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try{
            String resource = "./SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            if(sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
