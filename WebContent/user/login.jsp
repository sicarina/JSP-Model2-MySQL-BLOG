<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
	<%@ include file="/include/header.jsp"%>
		<section class="site-section">
			<div class="container">
				<div class="row blog-entries">
					<div class="col-md-12 col-lg-12 main-content">
						<div class="col-md-2" style="display:inline-block"></div>
						<div class="col-md-8" style="display:inline-block">
							<div class="row mb-4">
								<div class="col-md-12">
									<h1>Login</h1>
								</div>
							</div>
							<form action="/blogNew/user?cmd=login" method="post" id="loginForm">
								<div class="row">
									<div class="col-md-12 form-group">
										<label for="username">아이디</label>
										<c:choose>
											<c:when test="${empty cookie.username.value}">
												<input type="text" id="username" name="username" class="form-control" required="required" />									
											</c:when>
											<c:otherwise>
												<input type="text" id="username" name="username" class="form-control" required="required" value="${cookie.username.value}"/>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-12 form-group">
										<label for="password">비밀번호</label>
										<input type="password" id="password" name="password" class="form-control" required="required" />
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-12 form-group text-right">
										<c:choose>
											<c:when test="${empty cookie.username.value}">
												<input type="checkbox" id="rememberMe" name="rememberMe">								
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="rememberMe" name="rememberMe" checked="checked">
											</c:otherwise>
										</c:choose>
										<label for="rememberMe">아이디 저장</label> &nbsp; &nbsp;
										<input type="submit" value="로그인" class="btn btn-primary" />
									</div>
								</div>
							</form>
						</div>
						<div class="col-md-2" style="display:inline-block"></div>
					</div>
				</div>
			</div>
		</section>
	<%@ include file="/include/footer.jsp"%>
</html>