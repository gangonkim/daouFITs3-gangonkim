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
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/search")
public class BookSearchServlet extends HttpServlet {

    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    private static final long serialVersionUID = 1L;
    BookService bookService = new BookService(factory);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // Retrieve form data
        String keyword = request.getParameter("keyword");
        String priceLimitStr = request.getParameter("priceLimit");
        int priceLimit = Integer.parseInt(priceLimitStr);

        // Get books matching the keyword
        List<BookVO> books = bookService.selectByKeyword(keyword, priceLimit);
        System.out.println(keyword);
        System.out.println(priceLimitStr);
        System.out.println(books.get(0).getBtitle());

        // Filter books based on the price limit
        List<BookVO> filteredBooks = new ArrayList<>();
        for (BookVO book : books) {
                filteredBooks.add(book);
        }

        // Set the filtered list in the request scope
        request.setAttribute("books", filteredBooks);

        // Forward the request to a JSP page to display the results
        request.getRequestDispatcher("/bookResult.jsp").forward(request, response);
    }
}


