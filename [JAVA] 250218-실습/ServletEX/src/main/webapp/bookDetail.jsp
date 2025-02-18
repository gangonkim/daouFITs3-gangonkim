<%@ page import="org.example.servletex.vo.BookVO" %>
<%@ page import="org.example.servletex.service.BookService" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서 상세 정보</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .container { width: 50%; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }
        .btn { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer; }
        .btn:hover { background-color: #45a049; }
    </style>
</head>
<body>
<h1>도서 상세 정보</h1>

<%
    // Get the ISBN from the request parameter
    String isbn = request.getParameter("isbn");
    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
    BookService bookService = new BookService(factory);


    if (isbn != null && !isbn.isEmpty()) {
        // Call the service to get the book details by ISBN
        BookVO book = bookService.selectByIsbn(isbn);

        if (book != null) {
%>
<table border="1">
    <tr>
        <th>도서명</th>
        <td><%= book.getBtitle() %></td>
    </tr>
    <tr>
        <th>저자</th>
        <td><%= book.getBauthor() %></td>
    </tr>
    <tr>
        <th>가격</th>
        <td><%= book.getBprice() %>원</td>
    </tr>
    <tr>
        <th>ISBN</th>
        <td><%= book.getBisbn() %></td>
    </tr>
</table>
<%
} else {
%>
<p>해당 도서를 찾을 수 없습니다.</p>
<%
    }
} else {
%>
<p>ISBN이 제공되지 않았습니다.</p>
<%
    }
%>

<a href="bookResult.jsp">검색 결과로 돌아가기</a>
</body>
</html>

