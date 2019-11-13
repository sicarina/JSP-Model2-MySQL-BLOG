<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<%@ include file="/include/header.jsp"%>
		<section class="site-section pt-5">
			<div class="container">
				<div class="row blog-entries">
					<%@ include file="/include/sidebar.jsp"%>
					<div class="col-md-12 col-lg-8 main-content">
						<div class="row mb-4">
							<div class="col-md-6">
								<h2 class="mb-4">${categoryName}</h2>
							</div>
						</div>
						<div class="row mb-5 mt-5">
							<div class="col-md-12">
								<c:forEach var="board" items="${boards}">
									<div class="post-entry-horzontal">
										<a href="/blogNew/board?cmd=detail&id=${board.id}">
											<div class="image element-animate" data-animate-effect="fadeIn" style="background-image: url(${board.previewImg});"></div>
											<span class="text">
												<h2 style="display: -webkit-box; -webkit-box-orient: vertical; text-align: left; overflow: hidden; text-overflow: ellipsis; white-space: normal; line-height: 1.2; height: 1.2em; -webkit-line-clamp: 1; word-break:break-all">${board.title}</h2>
												<div class="post-meta">
													<span class="author mr-2"> ${board.username} </span>
													&bullet;<span class="mr-2"> ${board.insDt} </span>
													&bullet;<span class="ml-2"><span class="fa fa-comments"></span> ${board.readCount}</span>
												</div>
												<div style="display: -webkit-box; -webkit-box-orient: vertical; text-align: left; overflow: hidden; text-overflow: ellipsis; white-space: normal; line-height: 1.2; height: 2.3em; -webkit-line-clamp: 2; word-break:break-all">${board.content}</div>
											</span>
										</a>
									</div>
								</c:forEach>
							</div>
						</div>
		
						<div class="row mt-5">
							<div class="col-md-12 text-center">
								<nav aria-label="Page navigation" class="text-center">
									<ul class="pagination">
										<c:choose>
											<c:when test="${param.page ne 1}">
												<li class="page-item">
													<a href="/blogNew/board?cmd=list&categoryId=${param.categoryId}&page=${param.page-1}&search=${param.search}" class="page-link" aria-label="Previous">&lt;</a>
												</li>
											</c:when>
											<c:otherwise>
												<li class="page-item disabled">
													<a href="/blogNew/board?cmd=list&categoryId=${param.categoryId}&page=1&search=${param.search}" class="page-link" aria-label="Previous">&lt;</a>
												</li>
											</c:otherwise>
										</c:choose>
										<c:forEach var="page" begin="1" end="${maxPage}" step="1">
											<c:choose>
												<c:when test="${page eq param.page}">
													<li class="page-item active"><a href="/blogNew/board?cmd=list&categoryId=${param.categoryId}&page=${page}&search=${param.search}" class="page-link">${page}</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a href="/blogNew/board?cmd=list&categoryId=${param.categoryId}&page=${page}&search=${param.search}" class="page-link">${page}</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<c:choose>
											<c:when test="${param.page ne maxPage}">
												<li class="page-item">
													<a href="/blogNew/board?cmd=list&categoryId=${param.categoryId}&page=${param.page+1}&search=${param.search}" class="page-link" aria-label="Next">&gt;</a>
												</li>
											</c:when>
											<c:otherwise>
												<li class="page-item disabled">
													<a href="/blogNew/board?cmd=list&categoryId=${param.categoryId}&page=${maxPage}&search=${param.search}" class="page-link" aria-label="Next">&gt;</a>
												</li>
											</c:otherwise>
										</c:choose>
									</ul>
								</nav>
							</div>
						</div>
					</div>
					<!-- END main-content -->
				</div>
			</div>
		</section>
	<%@ include file="/include/footer.jsp"%>
</html>