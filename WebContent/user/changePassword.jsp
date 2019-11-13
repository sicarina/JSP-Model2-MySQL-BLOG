<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
	<%@ include file="/include/header.jsp"%>
	<c:if test="${empty sessionScope.user}">
		<script>
			alert("로그인 후 사용가능한 메뉴입니다.");
			location.href = "/blogNew/user/login.jsp";
		</script>
	</c:if>
		<section class="site-section">
			<div class="container">
				<div class="row blog-entries">
					<div class="col-md-12 col-lg-12 main-content">
						<div class="col-md-2" style="display:inline-block"></div>
						<div class="col-md-8" style="display:inline-block">
							<div class="row mb-4">
								<div class="col-md-12">
									<h1>Change Password</h1>
								</div>
							</div>
							<form onsubmit="return passwordCheck();" action="/blogNew/user?cmd=password" method="post" id="passwordForm">
								<div class="row">
									<div class="col-md-12 form-group">
										<label for="password">현재 비밀번호</label>
										<input type="password" id="password" name="password" class="form-control" required="required" />
									</div>
									<div class="col-md-12 form-group">
										<label for="newPassword">새 비밀번호</label>
										<input type="password" id="newPassword" name="newPassword" class="form-control" required="required" />
									</div>
									<div class="col-md-12 form-group">
										<label for="newPasswordChk">새 비밀번호 확인</label>
										&nbsp; &nbsp;<i id="passwordMatchResult"></i>
										<input type="password" id="newPasswordChk" name="newPasswordChk" class="form-control" required="required" />
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-12 form-group text-right">
										<input type="submit" value="비밀번호 변경" class="btn btn-primary" />
									</div>
								</div>
								<input type="hidden" name="username" value="${sessionScope.user.username}" />
							</form>
						</div>
						<div class="col-md-2" style="display:inline-block"></div>
					</div>
				</div>
			</div>
		</section>
		<script>
			// 비밀번호 일치 확인
			function passwordCheck(){
				var newPassword = $("#newPassword").val();
				var newPasswordChk = $("#newPasswordChk").val();
				
				if(newPassword === newPasswordChk){
					return true;
				} else {
			         $("#passwordMatchResult").text("비밀번호가 일치하지 않습니다.");
			         $("#passwordMatchResult").css("color", "red");
					return false;
				}
			}
		</script>
	<%@ include file="/include/footer.jsp"%>
</html>