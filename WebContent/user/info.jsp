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
									<h1>My Info</h1>
								</div>
							</div>
							<form onsubmit="return checkEmail();" action="/blogNew/user?cmd=update" method="post" id="updateForm">
								<div class="row">
									<div class="col-md-12 form-group">
										<label for="username">아이디</label>
										<input type="text" id="username" name="username" class="form-control" required="required" readonly="readonly" style="background: white" value="${user.username}" />
									</div>
									<div class="col-md-12 form-group">
										<label for="email">이메일</label>
										&nbsp; &nbsp;<i id="emailCheckResult" style="color:green">
										<c:choose>
											<c:when test="${user.emailChk eq true}">
												인증됨
											</c:when>
											<c:otherwise>
												인증되지 않음 
												&nbsp;<button type="button" onClick="sendMail();" id="btnSendMail" class="btn btn-black text-center" style="line-height:0.5">인증메일발송</button>
												&nbsp;<span id="sendResult" style="color:red"></span>
											</c:otherwise>
										</c:choose>
										</i>
										<input type="email" id="email" name="email" class="form-control" required="required" value="${user.email}" />
									</div>
									<div class="col-md-12 form-group">
										<label for="address">주소</label>
										&nbsp; &nbsp; &nbsp;<button type="button" onClick="searchAddr();" id="btnSearchAddr" class="btn btn-black text-center" style="line-height:0.5">주소찾기</button>
										<input type="text" id="address" name="address" class="form-control" required="required" readonly="readonly" style="background: white" value="${user.address}" />
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-12 form-group text-right">
										<input type="button" onClick="changePassword();" value="비밀번호 변경" class="btn btn-primary" />
										<input type="submit" value="정보수정" class="btn btn-primary" />
									</div>
								</div>
								<input type="hidden" name="id" value="${user.id}" />
								<input type="hidden" id="emailChk" name="emailChk"/>
							</form>
							<br/><br/>
							<form onsubmit="return uploadImg();" action="/blogNew/user?cmd=profile" method="post" id="profileForm" enctype="multipart/form-data">
								<div class="row">
									<div class="col-md-12 form-group">
										프로필 사진
									</div>
									<div class="col-md-12 form-group">
										<img id="profileImg" src="${user.profile}" style="width:30%"/>
										<input type="file" id="profile" name="profile" style="vertical-align:bottom;"/>
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-12 form-group text-right">
										<input type="button" onClick="deleteImg(${user.id});" value="사진 삭제" class="btn btn-primary" />
										<input type="submit" value="사진 수정" class="btn btn-primary" />
									</div>
								</div>
								<input type="hidden" name="id" value="${user.id}" />
								<input type="hidden" id="imgChk" name="imgChk"/>
							</form>
						</div>
						<div class="col-md-2" style="display:inline-block"></div>
					</div>
				</div>
			</div>
		</section>
		<script>
			// 인증 메일 발송
			function sendMail(){
				$("#sendResult").text("발송중...");
				var formData = $("#updateForm").serialize();
				
				$.ajax({
					url: "/blogNew/user?cmd=send",
			           type: "post",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType: "text",
					data: formData,
					success:function(data){
						if(data === "success"){
							$("#emailCheckResult").text("인증메일 발송성공");
						} else {
							$("#sendResult").text("발송실패 : 다시 시도해 주세요.");
						}
					},
					error:function(error){
						$("#sendResult").text("발송실패 : 다시 시도해 주세요.");
						console.log(error);
					}
				});
			}
		
			// 이메일 변경 여부 체크
			function checkEmail(){
				if("${user.email}" === $("#email").val()){
					if("${user.emailChk}" == "true"){
						$("#emailChk").val("true");
					} else {
						$("#emailChk").val("false");
					}
					return true;
				} else {
					if("${user.emailChk}" == "true"){
						var userChoice = confirm("이메일을 변경하시면 기존 인증 내역이 사라집니다.\n계속하시겠습니까?");
						
						if(userChoice){
							$("#emailChk").val("false");
							return true;
						} else {
							return false;
						}
					} else {
						$("#emailChk").val("false");
						return true;
					}
				}
			}
			
			// 비밀번호 변경페이지 이동
			function changePassword(){
				var userChoice = confirm("비밀번호를 변경하시겠습니까?");
				
				if(userChoice){
					location.href = "/blogNew/user/changePassword.jsp"
				}
			}
			
			// 주소 검색
			function searchAddr(){
				// 주소검색을 수행할 팝업 페이지를 호출
				// 호출된 페이지(jusoPopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출
				var pop = window.open("/blogNew/api/juso/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			}
			
			function jusoCallBack(roadFullAddr){
				// 팝업페이지에서 주소입력한 정보를 받아서 현 페이지에 정보를 등록
				var fullAddr = document.querySelector("#address");
				fullAddr.value = roadFullAddr;
			}
			
			// 프로필 사진 미리보기
			$("#profile").on("change", handleImgFile);
			function handleImgFile(e){
				var f = e.target.files[0];
				
				if(!f.type.match("image.*")){
					alert("이미지만 등록할 수 있습니다.");
					return;
				}
				
				var reader = new FileReader();
				
				reader.onload = function(e){
					$("#profileImg").attr("src", e.target.result);
					$("#imgChk").val("true");
				}
				
				reader.readAsDataURL(f);
			}
			
			// 프로필 사진 변경여부 체크
			function uploadImg(){
				if($("#imgChk").val() == "true"){
					return true;
				} else {
					alert("변경된 파일이 없습니다.");
					return false;
				}
			}
			
			// 프로필 사진 삭제
			function deleteImg(id){
				$.ajax({
					url: "/blogNew/user?cmd=profileDelete",
					type: "post",
					contentType: "multipart/form-data; charset=utf-8",
					dataType: "text",
					data: id+"",
					success:function(data){
						alert("프로필 사진이 변경되었습니다.");
						location.href = "/blogNew/index.jsp"
					},
					error:function(error){
						console.log(error);
						alert("프로필 사진 수정에 실패하였습니다.\n다시 시도해 주세요.");
					}
				});
			}
		</script>
	<%@ include file="/include/footer.jsp"%>
</html>