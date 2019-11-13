					<div class="col-md-12 col-lg-4 sidebar">
		
						<div class="sidebar-box">
							<h3 class="heading">Categories</h3>
							<ul class="categories" id="categories"></ul>
						</div>
						<script>
							$(document).ready(function(){
								$.ajax({
									url: "/blogNew/category?cmd=list",
									type: "post", 
									dataType: "json",
									async: false,
									success: function(data){
										if(data.id === 0){
											var liTag = "<li><a href='#'>카테고리를 불러오지 못했습니다.<br/>페이지를 새로고침 해주세요.</a></li>";
											$("#categories").append(liTag);
										} else {
											$.each(data, function(index, item){
												var liTag = "<li><a href='/blogNew/board?cmd=list&categoryId=" + item.id + "&page=1'>" + item.name + "</a></li>";
												$("#categories").append(liTag);
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
						<!-- END sidebar-box -->
		
						<div class="sidebar-box">
							<h3 class="heading">Popular Posts</h3>
							<div class="post-entry-sidebar">
								<ul id="popularPostList"></ul>
								<script>
									$(document).ready(function(){
										$.ajax({
											url: "/blogNew/board?cmd=popular",
											type: "post", 
											dataType: "json",
											async: false,
											success: function(data){
												if(data.id === 0){
													var liTag = `<li>`;
													liTag += `<a href="#">`;
													liTag += `<div class="text">`;
													liTag += `<h4>글이 존재하지 않습니다.</h4>`;
													liTag += `</div>`;
													liTag += `</a>`;
													liTag += `</li>`;
													
													$("#popularPostList").append(liTag);
												} else {
													$.each(data, function(index, item){
														var liTag = `<li>`;
														liTag += `<a href="/blogNew/board?cmd=detail&id=` + item.id + `">`;
														liTag += `<img src="` + item.previewImg + `" class="mr-4"/>`;
														liTag += `<div class="text">`;
														liTag += `<h4 style="display: -webkit-box; -webkit-box-orient: vertical; text-align: left; overflow: hidden; text-overflow: ellipsis; white-space: normal; line-height: 1.2; height: 1em; -webkit-line-clamp: 1; word-break:break-all">` + item.title + `</h4>`;
														liTag += `<div class="post-meta">`;
														liTag += `<div class="mr-2">` + item.insDt + `</div>`;
														liTag += `</div>`;
														liTag += `</div>`;
														liTag += `</a>`;
														liTag += `</li>`;
														
														$("#popularPostList").append(liTag);
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
						<!-- END sidebar-box -->
						
						<div class="sidebar-box search-form-wrap">
							<form action="/blogNew/board?cmd=list&categoryId=0&page=1" class="search-form" method="post">
								<div class="form-group">
									<span class="icon fa fa-search"></span> <input type="text" class="form-control" id="s" name="search" placeholder="Keyword">
								</div> 
								<div class="form-group text-right">
									<button type="submit" class="btn btn-black">Search</button>
								</div> 
							</form>
						</div>
						<!-- END sidebar-box -->
					</div>