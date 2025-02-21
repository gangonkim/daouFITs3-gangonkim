function likePost(postId) {
    $.ajax({
        type: "POST",
        url: "likePostServlet",
        data: { no: postId },
        success: function(response) {
            // 좋아요 수 업데이트
            const likeButton = document.getElementById('likeButton');
            likeButton.innerHTML = "좋아요 " + response;
        },
        error: function() {
            alert("오류가 발생했습니다.");
        }
    });
}