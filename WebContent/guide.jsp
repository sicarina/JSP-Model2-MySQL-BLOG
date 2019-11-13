<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
	<%@ include file="/include/header.jsp"%>		
		<section class="site-section pt-5 pb-5">
			<div class="container">
				<div class="row blog-entries">
					<%@ include file="/include/sidebar.jsp"%>
					
					<div class="col-md-12 col-lg-8 main-content">
						<div class="row mb-5">
							<div class="col-md-12">
								<h1 class="mb-4">User Guide</h1>
								<div class="post-content-body">
									<h4 style="color:green;">1. DB 스키마 및 계정 생성</h4>
									<p>
										root 계정으로 들어가서 board 스키마 생성<br/>
									</p>
									<br/>
									<h4 style="color:green;">2. DB 계정생성</h4>
									<p>
										Navigator의 Administration 탭에서 Users and Privilleges 를 사용하거나 쿼리를 이용해 사용자 생성<br/>
										&nbsp; &nbsp; - ID는 boardAdm, PW는 boardPW1!<br/>
										&nbsp; &nbsp; - 권한은 board 스키마에 대해 모든 권한을 부여
									</p>
									<br/>
									<h4 style="color:green;">3. DB 테이블 생성</h4>
									<pre>
USE BOARD;

CREATE TABLE user(
	id int auto_increment primary key,      -- 아이디
    username varchar(100) not null unique,   -- 아이디명
    password varchar(100) not null,         -- 패스워드
    salt varchar(100) not null,            -- hash salt
    email varchar(100) not null,         -- 메일
    emailChk boolean not null,            -- 메일인증여부
    address varchar(300),               -- 주소
    profile text,                     -- 프로필 사진 URL
    insDt timestamp not null            -- 가입일시
) engine=InnoDB default charset=utf8;

desc user;
select * from user;


CREATE TABLE adminInfo(
	email varchar(100) not null,
    password varchar(100) not null
) engine=InnoDB default charset=utf8;

desc adminInfo;
select * from adminInfo;


CREATE TABLE category(
	id int auto_increment primary key,      -- 아이디
    name varchar(100) not null unique      -- 카테고리명
) engine=InnoDB default charset=utf8;

desc category;
select * from category;


CREATE TABLE board(
	id int auto_increment primary key,      -- 아이디
    categoryId int not null,            -- 카테고리 아이디
    title varchar(100) not null,         -- 제목
    content longtext,                  -- 내용
    readCount int default 0,            -- 조회수
    delFg boolean not null,               -- 삭제여부
    insId int not null,                -- 입력한 유저 ID
    insDt timestamp not null,            -- 입력일시
    updId int,                        -- 수정한 유저 ID
    updDt timestamp,                  -- 수정일시
    foreign key(categoryId) references category(id),
    foreign key(insId) references user(id),
    foreign key(updId) references user(id)
) engine=InnoDB default charset=utf8;

desc board;
select * from board;


CREATE TABLE comment(
	id int auto_increment primary key,      -- 아이디
    boardId int,                     -- 게시판 ID
    content varchar(300) not null,         -- 내용
    commentId int,                     -- 댓글 ID (상위 댓글)
    delFg boolean not null,               -- 삭제여부
    insId int not null,                -- 입력한 유저 ID
    insDt timestamp not null,            -- 입력일시
    updId int,                        -- 수정한 유저 ID
    updDt timestamp,                  -- 수정일시
    foreign key(boardId) references board(id),
    foreign key(insId) references user(id),
    foreign key(updId) references user(id)
) engine=InnoDB default charset=utf8;

desc comment;
select * from comment;
									</pre>
									<h4 style="color:green;">4. adminInfo 테이블 자료 입력</h4>
									<p>
										adminInfo 테이블에 이메일 인증할 때 사용할 Gmail ID/PW를 아래와 같이 입력<br/>
										반드시 하나의 계정정보만 입력해야함! 많이 넣으면 안됩니다. 아마도?<br/>
										INSERT INTO adminInfo VALUES('TEST@gmail.com', 'PASSWORD');
									</p>
									<br/>
									<h4 style="color:green;">5. category 테이블 자료 입력</h4>
									<p>
										category 테이블에  사용할 카테고리를 입력<br/>
										INSERT INTO category VALUES(null, '카테고리명');<br/>
										&nbsp; &nbsp; - 글 작성시 카테고리가 들어가게 되므로 반드시 하나 이상 입력해 주세요.<br/>
									</p>
									<br/>
									<h4 style="color:green;">6. 홈페이지 실행해서 사용해보기</h4>
									<p>
										회원가입부터 시작해서 사용해보세요!<br/>
									</p>
									<br/>
									<h4 style="color:green;">7. 기타</h4>
									<p>
										<a href="https://blog.naver.com/sicarina/221702331300" target="_blank">블로그에 작성했던 흔적들이 있습니다.</a><br/>
										<a href="https://blog.naver.com/sicarina/221706421207" target="_blank">실행영상</a><br/>
									</p>
								</div>
							</div>
						</div>
					</div>
					<!-- END main-content -->
				</div>
			</div>
		</section>
	<%@ include file="/include/footer.jsp"%>
</html>