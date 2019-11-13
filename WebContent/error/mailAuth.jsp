<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
	<%@ include file="/include/header.jsp"%>
		<br/><br/><br/>
		<div class="text text-center">
		<c:choose>
			<c:when test="${param.msg eq 'db'}">
				<h3>이메일 인증정보 저장에 실패하였습니다.</h3>
			</c:when>
			<c:when test="${param.msg eq 'code'}">
				<h3>인증정보가 일치하는 사용자가 없어 이메일 인증에 실패하였습니다.</h3>
			</c:when>
		</c:choose>
		<br/><br/>
		<p>상세한 내용은 관리자에게 문의주시기 바랍니다.</p>
		</div>
		<br/><br/><br/>
	<%@ include file="/include/footer.jsp"%>
</html>