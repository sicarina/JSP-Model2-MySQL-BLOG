<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<%@ include file="/include/header.jsp"%>
	<c:if test="${empty sessionScope.user}">
		<script>
			alert("로그인 후 사용가능한 메뉴입니다.");
			location.href = "/blogNew/user/login.jsp";
		</script>
	</c:if>
	<section class="site-section py-lg">
		<div class="container">
			<div class="row blog-entries element-animate">
				
				<%@ include file="/include/sidebar.jsp"%>
				
				<div class="col-md-12 col-lg-8 main-content">
					<div class="row">
						<div class="col-md-6">
							<h2 class="mb-4">Write</h2>
						</div>
					</div>
					<div class="row mb-5">
						<div class="col-md-12">
							<form action="/blogNew/board?cmd=write" method="post" id="writeForm">
								<div class="row">
									<div class="col-md-12 form-group">
										<select id="category" name="category" class="form-control">
										</select>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 form-group">
										<input type="text" id="title" name="title" class="form-control" required="required" placeholder="제목을 입력하세요."/>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 form-group">
										<textarea name="content" id="summernote" class="form-control " cols="30" rows="8"></textarea>
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-12 form-group text-right">
										<input type="submit" value="저장" class="btn btn-primary" />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css" rel="stylesheet">
	<script src="/blogNew/api/summernote/summernote-bs4.js"></script>

	<script>
		$('#summernote').summernote({
			placeholder: '내용을 입력하세요.',
			tabsize: 2,
			height: 200
		});
		
		$('.dropdown-toggle').dropdown();
		
		$(document).ready(function(){
			$.ajax({
				url: "/blogNew/category?cmd=list",
				type: "post", 
				dataType: "json",
				success: function(data){
					if(data.id === 0){
						var optionTag = "<option value='0'>카테고리를 불러오지 못했습니다. 페이지를 새로고침 해주세요.</option>";
						$("#category").append(optionTag);
					} else {
						$.each(data, function(index, item){
							var optionTag = "<option value='" + item.id + "'>" + item.name + "</option>";
							$("#category").append(optionTag);
						});
					}
				},
				error: function(request, status, error){
					console.log("code:" + request.status);
					console.log("message:" + request.responseText);
					console.log("error:" + error);
				}
			});
		});
	</script>
	<%@ include file="/include/footer.jsp"%>
</html>