
<%@ page import="org.example.servletex.service.BoardService" %>
<%@ page import="org.example.servletex.vo.BoardVO" %>
<%@ page import="org.example.servletex.mybatis.MybatisSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.example.servletex.vo.MemberVO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.example.servletex.service.CommentService" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.servletex.vo.CommentVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>게시글 상세보기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e3f2fd; /* Light blue background */
            color: #1e88e5; /* Blue text */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            /*text-align: center;*/
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 600px;
        }
        h2 {
            color: #1e88e5;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .post-content {
            font-size: 18px;
            margin-bottom: 20px;
            text-align: left;
            color: #555;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            background-color: #1e88e5;
            color: white;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #1565c0;
        }
        .footer {
            margin-top: 20px;
            font-size: 12px;
            color: #888;
        }
        .like-btn {
            background-color: transparent;
            cursor: pointer;
        }
        .like-btn:hover {
            background-color: transparent;
        }

        #commentList {
            margin-top: 20px;
            width: 100%;
            max-width: 700px;
        }

        /* 댓글 항목 스타일 */
        .comment-item {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            position: relative;
            transition: background-color 0.3s ease;
        }

        .comment-item:hover {
            background-color: #f1f1f1;
        }

        /* 작성자 이름 */
        .comment-writer {
            font-weight: bold;
            color: black;
            font-size: 16px;
            text-align: left; /* 왼쪽 정렬 */
        }

        /* 댓글 내용 */
        .comment-content {
            font-size: 14px;
            color: #333;
            margin: 10px 0;
            text-align: left; /* 왼쪽 정렬 */
        }

        /* 날짜 */
        .comment-date {
            font-size: 12px;
            color: #777;
            text-align: right; /* 오른쪽 정렬 */
        }

        /* 삭제 버튼 스타일 */
        .delete-btn {
            background-color: #ff4757;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 12px;
            transition: background-color 0.3s ease;
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .delete-btn:hover {
            background-color: #e84118;
        }

        .delete-btn:focus {
            outline: none;
        }

        /* 댓글 입력 컨테이너 */
        .comment-input-container {
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 20px;
        }

        /* 댓글 구분선 */
        .comment-divider {
            border: 0;
            border-top: 2px solid black; /* 초록색 구분선 */
            width: 100%;
            margin-bottom: 20px;
        }
    </style>
    <%
//        String no = request.getParameter("no");
//        SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();
//        BoardService boardService = new BoardService(factory);
//        board = boardService.detail(Integer.parseInt(no));

        BoardVO board = (BoardVO) request.getAttribute("board");


        HttpSession sessionObj = request.getSession(false);
        String loggedInUser = null;
        if (sessionObj != null) {
            MemberVO vo = (MemberVO) sessionObj.getAttribute("member");
            if (vo != null) {
                loggedInUser = vo.getName();
            }
        }


        boolean isWriter = (loggedInUser != null && loggedInUser.equals(board.getWriter()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(board.getWritedate());

    %>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>
    <script>
        let liked = false;  // 초기 좋아요 상태는 false로 설정


        // 페이지 로드시 좋아요 상태와 개수를 불러옵니다.
        $(document).ready(function() {
            loadLikeStatus(<%= board.getNo() %>);
        });

        // 좋아요 상태 불러오기
        function loadLikeStatus(boardno) {
            $.ajax({
                type: "GET",
                url: "toggleLike",
                data: { boardno: boardno },
                dataType: "json",
                success: function(response) {
                    if (response.error) {
                        alert(response.error);
                        return;
                    }

                    // 서버에서 받은 좋아요 상태와 개수
                    liked = response.liked;
                    const likeButton = document.getElementById('likeButton');
                    const likeCount = response.likeCount;

                    // 좋아요 버튼 상태 업데이트
                    if (liked) {
                        likeButton.innerHTML = "❤️";
                        likeButton.classList.add('liked');
                    } else {
                        likeButton.innerHTML = "🤍";
                        likeButton.classList.remove('liked');
                    }
                },
                error: function() {
                    alert("좋아요 상태 불러오기 중 오류가 발생했습니다.");
                }
            });
        }


        function likePost(boardno) {
            $.ajax({
                type: "POST",
                url: "toggleLike",
                data: { boardno: boardno},
                dataType: "json",  // 응답을 JSON으로 받음
                success: function(response) {
                    console.log(response);
                    liked = response.liked;
                    const likeButton = document.getElementById('likeButton');
                    if (liked) {
                        liked = true;
                        likeButton.innerHTML = "❤️"
                        likeButton.classList.add('liked');
                    } else {
                        liked = false;
                        likeButton.innerHTML = "🤍"
                        likeButton.classList.remove('liked');
                    }
                },
                error: function(xhr, status, error) {
                    console.log(xhr)
                    console.log(status)
                    console.log(error)
                    alert("오류가 발생했습니다.");

                }
            });
        }


        // 댓글 작성 함수
        function submitComment(postId, writer) {
            var commentText = document.getElementById('commentText').value
            console.log("postId: " + postId + ", writer: " + writer + ", commentText: " + commentText);


            if (!commentText) {
                alert("댓글을 입력해주세요.");
                return;
            }

            $.ajax({
                type: "POST",
                url: "commentServlet",
                data: { postId: postId, commentText: commentText, writer: writer },
                dataType: "json",
                success: function(response) {
                    console.log(response)
                    // 댓글 목록을 업데이트
                    loadComments(postId)
                    // 입력란 비우기
                    //document.getElementById('commentText').value = "";
                },
                error: function(xhr, status, error) {
                    console.log(xhr)
                    console.log(status)
                    console.log(error)
                    alert("댓글 작성 중 오류가 발생했습니다.");
                }
            });
        }

        // 댓글 삭제 함수
        function deleteComment(commentId) {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            $.ajax({
                type: "POST",
                url: "commentDeleteServlet",
                data: { commentId: commentId },
                dataType: "json",
                success: function(response) {
                    if (response.success) {
                        alert("댓글이 삭제되었습니다.");
                        loadComments(<%=board.getNo()%>);
                    } else {
                        alert("삭제 실패: " + response.message);
                    }
                },
                error: function() {
                    alert("댓글 삭제 중 오류가 발생했습니다.");
                }
            });
        }

        // 댓글 목록 업데이트 함수
        function updateCommentList(comments, loggedInUser) {
            var commentList = document.getElementById('comments');
            commentList.innerHTML = ""; // 기존 댓글 삭제

            console.log("업데이트된 댓글 목록: ", comments);  // 디버깅용 로그 추가

            comments.forEach(function(comment) {
                console.log(comment.writer)
                console.log(comment.content)

                console.log(comment.writer ,comment.content, comment.writedate);

                let li = $("<li class='comment-item'></li>"); // 댓글 항목 (기본적인 스타일 적용)
                let writer = $("<span class='comment-writer'></span>").text(comment.writer); // 작성자
                let content = $("<p class='comment-content'></p>").text(comment.content); // 댓글 내용
                let writedate = $("<span class='comment-date'></span>").text(comment.writedate); // 작성 날짜
                let delBtn = $("<button class='delete-btn'></button>").text('삭제').attr('onclick', 'deleteComment(' + comment.commentno + ')'); // 삭제 버튼

                li.append(writer);
                li.append(content);
                li.append(writedate);
                li.append(delBtn);

                // 댓글 리스트에 추가
                $('#comments').append(li);

        })}


        // 댓글 목록 불러오기
        function loadComments(postId) {
            $.ajax({
                type: "GET",
                url: "commentListServlet",
                data: { boardno: postId },
                dataType: "json",
                success: function(response) {
                    updateCommentList(response, "<%= loggedInUser %>");
                    console.log(response)
                },
                error: function() {
                    alert("댓글을 불러오는 중 오류가 발생했습니다.");
                }
            });
        }

        // 페이지 로드 시 댓글 불러오기
        $(document).ready(function() {
            loadComments(<%=board.getNo()%>);
        });

    </script>
</head>
<body>
<div class="container">

    <h2><%=board.getTitle()%></h2>
    <div class="post-content">
        <p><%=board.getText()%></p>
        <p><small>작성자: <%=board.getWriter()%> | 날짜: <%=formattedDate%></small></p>
    </div>

    <a href="boardSearch" class="btn">목록으로</a>
    <% if (isWriter) { %>
    <a href="boardUpdate.jsp?no=<%=board.getNo()%>" class="btn">수정</a>
    <a href="boardDeleteServlet?no=<%=board.getNo()%>" class="btn">삭제</a>
    <% } else { %>
    <p class="footer">작성자만 수정 및 삭제가 가능합니다.</p>
    <% } %>
    <button id="likeButton" class="btn like-btn" onclick="likePost(<%=board.getNo()%>)">

    </button>


    <!-- 댓글 입력 폼 -->
    <hr class="comment-divider">
    <h3>댓글</h3>
    <div class="comment-input-container">
    <textarea id="commentText" name="comment" placeholder="댓글을 입력하세요" rows="3" cols="60"></textarea>
    <button class="btn" onclick="submitComment(<%=board.getNo()%>, '<%=loggedInUser%>')">댓글 작성</button>
    </div>
    <!-- 댓글 목록 표시 -->
    <div id="commentList">
        <!-- 댓글 목록 -->
        <ul id="comments">

        </ul>
    </div>
</div>
</body>
</html>
