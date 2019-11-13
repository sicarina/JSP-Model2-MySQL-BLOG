<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="/include/header.jsp"%>
	<section class="site-section py-lg">
		<div class="container">
			<div class="row blog-entries element-animate">
				<%@ include file="/include/sidebar.jsp"%>
				<div class="col-md-12 col-lg-8 main-content">
					<div class="row mb-5">
						<div class="col-md-12">
							<h1 class="mb-4">${board.title}</h1>
							
							<div class="post-meta">
								<span class="author mr-2">
									<img src="${board.profile}" class="mr-2"> ${board.username}
								</span>
								&bullet; 
								<span class="mr-2">${board.insDt} </span>
								&bullet; 
								<span class="ml-2">
									<span class="fa fa-comments"></span> ${board.readCount}
								</span>
								<c:if test="${board.insId eq sessionScope.user.id}">
									&nbsp; &nbsp; &nbsp; 
									<div class="text-right" style="display:inline-block">
										<a href="/blogNew/board?cmd=updateForm&id=${board.id}">
											<span style="font-size:1.5em; font-weight:bold;">UPDATE</span>
										</a>
										&nbsp; &nbsp;
										<a href="/blogNew/board?cmd=delete&id=${board.id}">
											<span style="font-size:1.5em; font-weight:bold;">DELETE</span>
										</a>
									</div>
								</c:if>
							</div>
							<hr/>
							<div class="post-content-body">
								${board.content}
							</div>
							<br/><br/>
							<hr/>
							<div class="pt-4">
								<ul id="commentList" class="comment-list">
									<c:forEach var="comment" items="${comments}">
										<li id="comment${comment.id}" class="comment" style="margin:5px;">
											<div class="vcard">
												<img src="${comment.profile}">
											</div>
											<div class="comment-body">
												<h3>${comment.username}</h3>
												<div class="meta">${comment.insDt}</div>
												<p>${comment.content}</p>
												<p>
													<button type="button" id="btnReply${comment.id}" onClick="replyView(${comment.id});" class="reply rounded" style="line-height:1em;">Reply</button>
													<c:if test="${not empty sessionScope.user.username}">
														<button type="button" id="btnWrite${comment.id}" onClick="makeReplyWriteForm(${comment.id});" class="reply rounded" style="line-height:1em;">Write</button>
													</c:if>
													<c:if test="${sessionScope.user.username eq comment.username}">
														<button type="button" onClick="commentView(${comment.id});" class="reply rounded" style="line-height:1em;">Update</button>
														<button type="button" onClick="commentDelete(${comment.id});" class="reply rounded" style="line-height:1em;">Delete</button>
													</c:if>
												</p>
											</div>
										</li>
									</c:forEach>
								</ul>
								<!-- END comment-list -->
			
								<div class="comment-form-wrap pt-5">
									<form class="p-3 bg-light" id="commentWriteForm">	
										<div class="form-group">
											<label for="content" style="font-weight:bold;">Leave a comment</label>
											<textarea name="content" id="content" cols="30" rows="3" class="form-control" required="required"></textarea>
										</div>
										<div class="form-group text-right">
											<input type="button" onClick="commentWrite();" value="댓글 달기" class="btn btn-primary"  style="line-height:0.5">
										</div>
										<input type="hidden" name="boardId" value="${board.id}"/>
										<input type="hidden" name="commentId" value="0"/>
										<input type="hidden" name="userId" value="${sessionScope.user.id}"/>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END main-content -->
			</div>
		</div>
	</section>
	<script>
		// 댓글 쓰기
		function commentWrite(){
			if("${empty sessionScope.user}" == "true"){
				alert("로그인 후 사용가능한 기능입니다.");
			} else {
				var commentWriteForm = $("#commentWriteForm").serialize();
				
				$.ajax({
					url: "/blogNew/comment?cmd=write",
					type: "post", 
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					data: commentWriteForm,
					dataType: "json",
					success: function(data){
						var commentWriteForm = makeCommentWriteForm(data.id, data.username, data.insDt, data.content, data.profile);
						$("#commentList").append(commentWriteForm);
						$("#content").val("");
					},
					error: function(request, status, error){
						console.log("code:" + request.status);
						console.log("message:" + request.responseText);
						console.log("error:" + error);
					}
				});
			}
		}
		
		// 댓글 작성 후 작성한 댓글 화면에 붙일 수 있도록 폼 작성
		function makeCommentWriteForm(id, username, insDt, content, profile){
			var commentWriteForm = `<li id="comment` + id + `" class="comment" style="margin:5px;">`;
			commentWriteForm += `<div class="vcard">`;
			commentWriteForm += `<img src="` + profile + `">`;
			commentWriteForm += `</div>`;
			commentWriteForm += `<div class="comment-body">`;
			commentWriteForm += `<h3>` + username + `</h3>`;
			commentWriteForm += `<div class="meta">` + insDt + `</div>`;
			commentWriteForm += `<p>` + content + `</p>`;
			commentWriteForm += `<p><button type="button" id="btnReply` + id + `" onClick="replyView(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Reply</button>`;
			commentWriteForm += `<button type="button" id="btnWrite` + id + `" onClick="makeReplyWriteForm(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Write</button>`;
			commentWriteForm += `<button type="button" onClick="commentView(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Update</button>`;
			commentWriteForm += `<button type="button" onClick="commentDelete(` + id + `);" class="reply rounded" style="line-height:1em;">Delete</button></p>`;
			commentWriteForm += `</div>`;
			commentWriteForm += `</li>`;
			
			return commentWriteForm;
		}
		
		// 댓글 삭제
		function commentDelete(id){
			if(confirm("댓글을 삭제하시겠습니까?")){
				$.ajax({
					url: "/blogNew/comment?cmd=delete",
		            type: "post",
					contentType: "text/plain; charset=utf-8",
					data: id+"",
		            success:function(data){
		            	if(data === "ok"){
		            		$("#comment"+id).remove();
		            	}
		            },
		            error:function(error){
		            	console.log("code:" + request.status);
						console.log("message:" + request.responseText);
						console.log("error:" + error);
		            }
				});
			}
		}
		
		// 댓글 수정하기 위해 댓글 폼을 불러와서 내용을 뿌려줌
		function commentView(id){
			$.ajax({
				url: "/blogNew/comment?cmd=view",
				type: "post", 
				contentType: "text/plain; charset=utf-8",
				data: id+"",
				dataType: "json",
				success: function(data){
					var commentViewForm = makeCommentViewForm(data.id, data.content, "${sessionScope.user.id}");
					$("#comment"+data.id).empty();
					$("#comment"+data.id).append(commentViewForm);
				},
				error: function(request, status, error){
					console.log("code:" + request.status);
					console.log("message:" + request.responseText);
					console.log("error:" + error);
				}
			});
		}
		
		// 댓글을 수정할 수 있도록 기존내용 보여주는 폼 작성
		function makeCommentViewForm(id, content, insId){
			var commentViewForm = `<div class="comment-form-wrap">`;
			commentViewForm += `<form class="p-3 bg-light" id="commentUpdateForm` + id + `">`;
			commentViewForm += `<div class="form-group">`;
			commentViewForm += `<textarea name="content" id="content" cols="30" rows="3" class="form-control" required="required">` + content + `</textarea>`;
			commentViewForm += `</div>`;
			commentViewForm += `<div class="form-group text-right">`;
			commentViewForm += `<input type="button" onClick="commentUpdate(` + id + `);" value="댓글 수정" class="btn btn-primary"  style="line-height:0.5">`;
			commentViewForm += `</div>`;
			commentViewForm += `<input type="hidden" name="id" value="` + id + `"/>`;
			commentViewForm += `<input type="hidden" name="userId" value="` + insId + `"/>`;
			commentViewForm += `</form>`;
			commentViewForm += `</div>`;
			
			return commentViewForm;
		}
		
		// 댓글 수정
		function commentUpdate(id){
			var commentWriteForm = $("#commentUpdateForm"+id).serialize();
			
			$.ajax({
				url: "/blogNew/comment?cmd=update",
				type: "post", 
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				data: commentWriteForm,
				dataType: "json",
				success: function(data){
					var commentUpdateForm = makeCommentUpdateForm(data.id, data.username, data.insDt, data.content, data.profile);
					$("#comment"+data.id).empty();
					$("#comment"+data.id).append(commentUpdateForm);
				},
				error: function(request, status, error){
					console.log("code:" + request.status);
					console.log("message:" + request.responseText);
					console.log("error:" + error);
				}
			});
		}
		
		// 댓글 수정한 내용을 반영해주는 폼 작성
		function makeCommentUpdateForm(id, username, insDt, content, profile){
			var commentUpdateForm = `<div class="vcard">`;
			commentUpdateForm += `<img src="` + profile + `">`;
			commentUpdateForm += `</div>`;
			commentUpdateForm += `<div class="comment-body">`;
			commentUpdateForm += `<h3>` + username + `</h3>`;
			commentUpdateForm += `<div class="meta">` + insDt + `</div>`;
			commentUpdateForm += `<p>` + content + `</p>`;
			commentUpdateForm += `<p><button type="button" id="btnReply` + id + `" onClick="replyView(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Reply</button>`;
			commentUpdateForm += `<button type="button" id="btnWrite` + id + `" onClick="makeReplyWriteForm(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Write</button>`;
			commentUpdateForm += `<button type="button" onClick="commentView(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Update</button>`;
			commentUpdateForm += `<button type="button" onClick="commentDelete(` + id + `);" class="reply rounded" style="line-height:1em;">Delete</button></p>`;
			commentUpdateForm += `</div>`;
			
			return commentUpdateForm;
		}
		
		// 답글 쓰기 폼 작성
		function makeReplyWriteForm(commentId){
			var replyWriteFormDiv = document.getElementById("replyWriteFormDiv"+commentId);
			
			if(replyWriteFormDiv != null){
				$("#replyWriteFormDiv"+commentId).remove();
				$("#btnWrite"+commentId).text("Write");
			} else {
				if($("#btnReply"+commentId).text() === "Reply"){
					replyView(commentId);
				}

				var replyWriteForm = `<div id="replyWriteFormDiv` + commentId + `" class="comment-form-wrap">`;
				replyWriteForm += `<form class="p-3 bg-light" id="replyWriteForm` + commentId + `">`;
				replyWriteForm += `<div class="form-group">`;
				replyWriteForm += `<label for="content" style="font-weight:bold;">Leave a reply</label>`;
				replyWriteForm += `<textarea name="content" id="content" cols="30" rows="3" class="form-control" required="required"></textarea>`;
				replyWriteForm += `</div>`;
				replyWriteForm += `<div class="form-group text-right">`;
				replyWriteForm += `<input type="button" onClick="replyWrite(` + commentId + `);" value="답글 쓰기" class="btn btn-primary"  style="line-height:0.5">`;
				replyWriteForm += `</div>`;
				replyWriteForm += `<input type="hidden" name="boardId" value="${param.id}"/>`;
				replyWriteForm += `<input type="hidden" name="commentId" value="` + commentId + `"/>`;
				replyWriteForm += `<input type="hidden" name="userId" value="${sessionScope.user.id}"/>`;
				replyWriteForm += `</form>`;
				replyWriteForm += `</div>`;
				
				$("#comment"+commentId).append(replyWriteForm);
				$("#btnWrite"+commentId).text("Write Close");
			}
		}
		
		// 답글 쓰기
		function replyWrite(commentId){
			var replyWriteForm = $("#replyWriteForm"+commentId).serialize();
			
			$.ajax({
				url: "/blogNew/comment?cmd=write",
				type: "post", 
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				data: replyWriteForm,
				dataType: "json",
				success: function(data){
					var replyResult = makeReplyResult(data.id, data.username, data.insDt, data.content, data.profile);
					$("#comment"+commentId).append(replyResult);
					
					$("#replyWriteFormDiv"+commentId).remove();
					$("#btnWrite"+commentId).text("Write");

					$("#btnReply"+commentId).text("Reply Close");
				},
				error: function(request, status, error){
					console.log("code:" + request.status);
					console.log("message:" + request.responseText);
					console.log("error:" + error);
				}
			});
		}
		
		// 답글 작성 후 작성한 답글 화면에 붙일 수 있도록 폼 작성 : 답글 가져올때도 사용
		function makeReplyResult(id, username, insDt, content, profile){
			var replyResult = `<ul id="comment` + id + `" class="children">`;
			replyResult += `<li class="comment" style="margin:5px;">`;
			replyResult += `<div class="vcard">`;
			replyResult += `<img src="` + profile + `">`;
			replyResult += `</div>`;
			replyResult += `<div class="comment-body">`;
			replyResult += `<h3>` + username + `</h3>`;
			replyResult += `<div class="meta">` + insDt + `</div>`;
			replyResult += `<p>` + content + `</p>`;
			replyResult += `<p><button type="button" id="btnReply` + id + `" onClick="replyView(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Reply</button>`;
			
			if("${empty sessionScope.user.username}" === "false"){
				replyResult += `<button type="button" id="btnWrite` + id + `" onClick="makeReplyWriteForm(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Write</button>`;				
			}
			
			if("${sessionScope.user.username}" === username){
				replyResult += `<button type="button" onClick="commentView(` + id + `);" class="reply rounded" style="line-height:1em; margin-right:9px;">Update</button>`;
				replyResult += `<button type="button" onClick="commentDelete(` + id + `);" class="reply rounded" style="line-height:1em;">Delete</button></p>`;
			}
			replyResult += `</div>`;
			replyResult += `</li>`;
			replyResult += `</ul>`;
			
			return replyResult;
		}
		
		// 답글 보기
		function replyView(commentId){
			if($("#btnReply"+commentId).text() === "Reply"){
				$.ajax({
					url: "/blogNew/comment?cmd=viewAll",
					type: "post", 
					contentType: "text/plain; charset=utf-8",
					data: commentId+"",
					dataType: "json",
					success: function(data){
						$.each(data, function(index, item){
							if(item.id != 0 && typeof(item.id) != "undefined"){
								var replyResult = makeReplyResult(item.id, item.username, item.insDt, item.content, item.profile);
								$("#comment"+commentId).append(replyResult);
								$("#btnReply"+commentId).text("Reply Close");
							}
						});
					},
					error: function(request, status, error){
						console.log("code:" + request.status);
						console.log("message:" + request.responseText);
						console.log("error:" + error);
					}
				});
			} else {
				$("#comment"+commentId).children("ul").remove();
				$("#btnReply"+commentId).text("Reply");
			}
		}
	</script>
	<%@ include file="/include/footer.jsp"%>
</html>