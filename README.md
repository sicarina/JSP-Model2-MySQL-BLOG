#### 나만의 블로그 만들기 JSP+Model2+MySQL

- git주소 : <https://github.com/sicarina/JSP-Model2-MySQL-BLOG>

![blog]()

#### 1. 사용자 생성 및 권한 주기 및 DB 생성
- create user 'boardAdm'@'localhost' identified by 'boardPw1!';
- GRANT ALL PRIVILEGES ON *.* TO boardAdm@localhost;
- create database board;
- use board;

#### 2. 테이블
```sql
CREATE TABLE user(
	id int auto_increment primary key,      -- 아이디
    username varchar(100) not null unique,-- 아이디명
    password varchar(100) not null,       -- 패스워드
    salt varchar(100) not null,           -- hash salt
    email varchar(100) not null,          -- 메일
    emailChk boolean not null,            -- 메일인증여부
    address varchar(300),                 -- 주소
    profile text,                         -- 프로필 사진 URL
    insDt timestamp not null              -- 가입일시
) engine=InnoDB default charset=utf8;
```

```sql
CREATE TABLE adminInfo(
	email varchar(100) not null,
    password varchar(100) not null
) engine=InnoDB default charset=utf8;
```

```sql
CREATE TABLE category(
	id int auto_increment primary key,      -- 아이디
    name varchar(100) not null unique      -- 카테고리명
) engine=InnoDB default charset=utf8;
```

```sql
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
```

```sql
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
```


INSERT INTO adminInfo VALUES('TEST@gmail.com', 'PASSWORD');
INSERT INTO category VALUES(null, '카테고리명');


#### 3. 실행 영상 및 작업 삽질..
<https://blog.naver.com/sicarina/221706421207>
