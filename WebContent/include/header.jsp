<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<head>
		<title>Sample Site</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300, 400,700|Inconsolata:400,700" rel="stylesheet">
		
		<link rel="stylesheet" href="/blogNew/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="/blogNew/bootstrap/css/animate.css">
		<link rel="stylesheet" href="/blogNew/bootstrap/css/owl.carousel.min.css">
		
		<link rel="stylesheet" href="/blogNew/bootstrap/fonts/ionicons/css/ionicons.min.css">
		<link rel="stylesheet" href="/blogNew/bootstrap/fonts/fontawesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="/blogNew/bootstrap/fonts/flaticon/font/flaticon.css">
		
		<link rel="stylesheet" href="/blogNew/bootstrap/css/style.css">
		
		<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="wrap">
			<header role="banner">
				<div class="top-bar">
					<div class="container">
						<div class="row">
							<div class="col-12 social text-right">
								<c:choose>
									<c:when test="${empty sessionScope.user}">
										<a href="/blogNew/user/join.jsp">Join</a>
										<a href="/blogNew/user/login.jsp">Login</a>
									</c:when>
									<c:otherwise>
										<a href="/blogNew/user?cmd=info&username=${sessionScope.user.username}" style="opacity:1; text-decoration:none; cursor:pointer;"><img src="${sessionScope.user.profile}" style="width:30px; border-radius:50%; display:inline-block"/></a>
										<a href="#" style="opacity:1; text-decoration:none; cursor:default;">Hello, ${sessionScope.user.username}</a>
										<a href="/blogNew/user?cmd=info&username=${sessionScope.user.username}">MyInfo</a>
										<a href="/blogNew/user?cmd=logout">Logout</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
				
				<div class="container logo-wrap">
					<div class="row pt-5">
						<div class="col-12 text-center">
							<a class="absolute-toggle d-block d-md-none" data-toggle="collapse" href="#navbarMenu" role="button" aria-expanded="false" aria-controls="navbarMenu">
								<span class="burger-lines"></span>
							</a>
							<h1 class="site-logo">
								<a href="/blogNew/index.jsp">Sample Site</a>
							</h1>
						</div>
					</div>
				</div>
				
				<nav class="navbar navbar-expand-md  navbar-light bg-light">
					<div class="container">
						<div class="collapse navbar-collapse" id="navbarMenu">
							<ul class="navbar-nav mx-auto">
								<li class="nav-item">
									<a class="nav-link active" href="/blogNew/index.jsp">Home</a>
								</li>
								<li class="nav-item">
									&nbsp; &nbsp; &nbsp;
								</li>
								<li class="nav-item">
									<a class="nav-link" href="/blogNew/guide.jsp">Guide</a>
								</li>
								<li class="nav-item">
									&nbsp; &nbsp; &nbsp;
								</li>
								<li class="nav-item dropdown">
									<a class="nav-link dropdown-toggle" href="#" id="dropdown05" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Categories</a>
									<div id="dropdown-menu" class="dropdown-menu" aria-labelledby="dropdown05">
									</div>
								</li>
								<li class="nav-item">
									&nbsp; &nbsp; &nbsp;
								</li>
								<li class="nav-item">
									<a class="nav-link" href="/blogNew/board/write.jsp">Posting</a>
								</li>
							</ul>
							<script>
								$(document).ready(function(){
									$.ajax({
										url: "/blogNew/category?cmd=list",
										type: "post", 
										dataType: "json",
										async: false,
										success: function(data){
											if(data.id === 0){
												var aTag = "<a class='dropdown-item' href='#'>카테고리를 불러오지 못했습니다.<br/>페이지를 새로고침 해주세요.</a>";
												$("#dropdown-menu").append(aTag);
											} else {
												$.each(data, function(index, item){
													var aTag = "<a class='dropdown-item' href='/blogNew/board?cmd=list&categoryId=" + item.id + "&page=1'>" + item.name + "</a>";
													$("#dropdown-menu").append(aTag);
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
					</div>
				</nav>
			
			</header>
