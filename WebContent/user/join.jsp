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
									<h1>Join Us</h1>
								</div>
							</div>
							<form onsubmit="return joinValidate();" action="/blogNew/user?cmd=join" method="post" id="joinForm">
								<div class="row">
									<div class="col-md-12 form-group">
										<label for="username">아이디 *</label>
										&nbsp; &nbsp;<button type="button" onClick="duplicateCheck();" id="btnDuplicate" class="btn btn-black text-center" style="line-height:0.5">중복확인</button>
										&nbsp; &nbsp;<i id="duplicateResult"></i>
										<input type="text" id="username" name="username" class="form-control" required="required" />
									</div>
									<div class="col-md-12 form-group">
										<label for="password">비밀번호 *</label>
										<input type="password" id="password" name="password" class="form-control" required="required" />
									</div>
									<div class="col-md-12 form-group">
										<label for="passwordChk">비밀번호 확인 *</label>
										&nbsp; &nbsp;<i id="passwordMatchResult"></i>
										<input type="password" id="passwordChk" name="passwordChk" class="form-control" required="required" />
									</div>
									<div class="col-md-12 form-group">
										<label for="email">이메일 *</label>
										<input type="email" id="email" name="email" class="form-control" required="required" />
									</div>
									<div class="col-md-12 form-group">
										<label for="address">주소 &nbsp;</label>
										&nbsp; &nbsp; &nbsp;<button type="button" onClick="searchAddr();" id="btnSearchAddr" class="btn btn-black text-center" style="line-height:0.5">주소찾기</button>
										<input type="text" id="address" name="address" class="form-control" required="required" readonly="readonly" style="background: white" />
									</div>
								</div>
								<br/>
								<div class="row">
									<div class="col-md-12 form-group text-right">
										<input type="submit" value="회원가입" class="btn btn-primary" />
									</div>
								</div>
								<input type="hidden" id="usernameChk" name="usernameChk" value="false" />
							</form>
						</div>
						<div class="col-md-2" style="display:inline-block"></div>
					</div>
				</div>
			</div>
		</section>
		<script>
			// 아이디 중복 확인
			function duplicateCheck() {
				var formData = $("#joinForm").serialize();
				
				$.ajax({
					url: "/blogNew/user?cmd=duplicate",
		            type: "post",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType: "text",
					data: formData,
		            success:function(data){
		            	if(data === "ok"){
		            		$("#usernameChk").val("true");
		            		$("#duplicateResult").text("사용 가능한 아이디입니다.");
		            		$("#duplicateResult").css("color", "green");
		            	} else {
		            		$("#usernameChk").val("false");
		            		$("#duplicateResult").text("사용할 수 없는 아이디입니다.");
		            		$("#duplicateResult").css("color", "red");
		            	}
		            },
		            error:function(error){
		            	console.log(error);
		            }
				});
			}
			
			// 비밀번호 일치 확인
			function passwordCheck(){
				var password = $("#password").val();
				var passwordChk = $("#passwordChk").val();
				
				if(password === passwordChk){
					return true;
				} else {
			         $("#passwordMatchResult").text("비밀번호가 일치하지 않습니다.");
			         $("#passwordMatchResult").css("color", "red");
					return false;
				}
			}
			
			// 유효성 검사
			function joinValidate(){
				if($("#usernameChk").val() == "false"){
					alert("아이디 중복여부를 확인해주세요.");
					return false;
				} else if(passwordCheck() == false){
					alert("비밀번호를 확인해주세요.");
					return false;
				} else {
					return true;
				}
			}
			
			// 주소 검색
			function searchAddr(){
				// 주소검색을 수행할 팝업 페이지를 호출
				// 호출된 페이지(jusoPopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출
				var pop = window.open("/blogNew/api/juso/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			}
			
			function jusoCallBack(roadFullAddr){
				// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록
				var fullAddr = document.querySelector('#address');
				fullAddr.value = roadFullAddr;
			}
		</script>
	<%@ include file="/include/footer.jsp"%>
</html>