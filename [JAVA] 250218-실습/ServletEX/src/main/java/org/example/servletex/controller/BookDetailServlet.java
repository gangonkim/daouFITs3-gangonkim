package org.example.servletex.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.servletex.mybatis.MybatisSessionFactory;
import org.example.servletex.service.BookService;
import org.example.servletex.vo.BookVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/bookDetail")
public class BookDetailServlet extends HttpServlet {

    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private static final long serialVersionUID = 1L;
    BookService bookService = new BookService(factory);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String isbn = request.getParameter("isbn");
        if (isbn != null) {
            BookVO book = bookService.selectByIsbn(isbn);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/bookDetail.jsp").forward(request, response);
        } else {
            response.sendRedirect("bookResult.jsp");
        }
    }
}

