<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
	<%@ include file="/include/header.jsp"%>
		<section class="site-section pt-5 pb-5">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="owl-carousel owl-theme home-slider">
							<div>
								<a href="/blogNew/guide.jsp" class="a-block d-flex align-items-center" style="background-image: url('/blogNew/bootstrap/images/img_5.jpg');">
									<div class="text">
										<h3>Sample Site에 오신 것을 환영합니다.</h3>
										<p>
											Guide으로 이동하시면 사용 전 필요한 내용을 확인하실 수 있습니다.<br/>
											필요한 것들이 설정되지 않으면 사이트가 깨져보일 수 있습니다.<br/>
										</p>
									</div>
								</a>
							</div>
		
							<div>
								<a href="/blogNew/join.jsp" class="a-block d-flex align-items-center" style="background-image: url('/blogNew/bootstrap/images/img_7.jpg');">
									<div class="text">
										<h3>Sample Site에 오신 것을 환영합니다.</h3>
										<p>
											회원가입 후 홈페이지의 각종 기능을 사용하실 수 있습니다.<br/><br/>
										</p>
									</div>
								</a>
							</div>
		
							<div>
								<a href="#" class="a-block d-flex align-items-center" style="background-image: url('/blogNew/bootstrap/images/img_10.jpg');">
									<div class="text">
										<h3>Sample Site에 오신 것을 환영합니다.</h3>
										<p>
											홈페이지를 자유롭게 이용해보세요!<br/><br/>
										</p>
									</div>
								</a>
							</div>
		
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- END section -->
		
		<section class="site-section py-sm">
			<div class="container">
				<div class="row blog-entries">
					<%@ include file="/include/sidebar.jsp"%>
					
					<div class="col-md-12 col-lg-8 main-content">
						<div class="row">
							<div class="col-md-6">
								<h2 class="mb-4">Latest Posts</h2>
							</div>
						</div>
						<div id="latestPosts" class="row"></div>
						<script>
							$(document).ready(function(){
								$.ajax({
									url: "/blogNew/board?cmd=latest",
									type: "post", 
									dataType: "json",
									async: false,
									success: function(data){
										if(data.id === 0){
											var divTag = `<div class="col-md-12">`;
											divTag += `<a href="#" class="blog-entry">`;
											divTag += `<div class="blog-content-body">`;
											divTag += `<h2>게시글이 존재하지 않습니다.</h2>`;
											divTag += `</div>`;
											divTag += `</a>`;
											divTag += `</div>`;
											
											$("#latestPosts").append(divTag);
										} else {
											$.each(data, function(index, item){
												var divTag = `<div class="col-md-6">`;
												divTag += `<a href="/blogNew/board?cmd=detail&id=` + item.id + `" class="blog-entry">`;
												divTag += `<img src="` + item.previewImg + `">`;
												divTag += `<div class="blog-content-body">`;
												divTag += `<div class="post-meta">`;
												divTag += `<span class="author mr-2">`;
												divTag += `<img src="` + item.profile + `"> ` + item.username + `</span>`;
												divTag += `&bullet; <span class="mr-2">` + item.insDt + ` </span>`;
												divTag += `&bullet; <span class="ml-2"><span class="fa fa-comments"></span> ` + item.readCount + `</span>`;
												divTag += `</div>`;
												divTag += `<h2 style="display: -webkit-box; -webkit-box-orient: vertical; text-align: left; overflow: hidden; text-overflow: ellipsis; white-space: normal; height: 1.2em; -webkit-line-clamp: 1; word-break:break-all">` + item.title + `</h2>`;
												divTag += `</div>`;
												divTag += `</a>`;
												divTag += `</div>`;
												
												$("#latestPosts").append(divTag);
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
					</div>
					<!-- END main-content -->
				</div>
			</div>
		</section>
	<%@ include file="/include/footer.jsp"%>
</html>