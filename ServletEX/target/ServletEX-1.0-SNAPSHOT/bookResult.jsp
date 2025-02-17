<%@ page import="org.example.servletex.vo.BookVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>도서 검색 결과</title>
</head>
<body>
<h1>도서 검색 결과</h1>
<table border="1">
  <tr>
    <th>도서명</th>
    <th>가격</th>
  </tr>

  <%
    // Get the books from request
    List<BookVO> books = (List<BookVO>) request.getAttribute("books");

    // Loop through the list and display each book's details
    for (BookVO book : books) {
  %>
  <tr>
    <td>
      <!-- Create a link to the book details page -->
      <a href="bookDetail.jsp?isbn=<%= book.getBisbn() %>">
        <%= book.getBtitle() %>
      </a>
    </td>
    <td><%= book.getBprice() %>원</td> <!-- Display book price -->
  </tr>
  <%
    }
  %>
</table>
<a href="index.jsp">검색으로 돌아가기</a>
</body>
</html>

