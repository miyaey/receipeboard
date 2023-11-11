# Project name : Receipe board

## 프로젝트 개요

- RESTful API를 이용한 레시피 게시판 만들기 프로젝트

### 프로젝트 주요 기능

- 게시판 : CRUD 기능, 조회수, 페이징 및 검색 처리
- 댓글 : CRUD 기능
- 사용자 : Security 회원가입 및 로그인, 회원정보 수정, 회원가입 시 유효성 검사 및 중복 검사

### 🛠️Stack

- SpringBoot2.7
- Java Development Kit 11 (JDK11)
- MySQL 8.0.3
- SpringData JPA
- Validation
- Security
- thymeleaf
- Build Tool - maven 4.0

## 게시판 프로젝트 명세서

1. 게시판 페이징 처리 구현
2. 게시판 검색처리 및 페이징 구현
3. 게시판 댓글 작성 및 조회 구현
4. 게시판 댓글 Validation 유효성 검사
5. 게시판 수정 및 삭제 구현 Security 권한 기능과 연계하여 구현
6. 게시판 조회수 , 댓글수, 추천수 표시 기능 구현
7. 게시판 작성자만 수정, 삭제 가능하게 하기, Security 권한 기능과 연계하여 구현
8. 게시판 댓글 생성, 수정시간 LocalDateTime format
9. 게시판 추천기능 구현 – 동일 멤버의 동일 게시판, 댓글 중복 추천 방지
10. Security 회원가입 및 로그인 구현
11. 회원가입 Validation 유효성 검사
12. 회원가입 Validation 커스터마이징 중복 검사
13. Security 로그인 실패시 메시지 출력하기
14. Security 권한부여 및 권한없는 유저를 지정된 URL로 redirect하기
15. Security 권한부여 및 권한없는 유저의 특정 리소스접근시 메시지 출력하기(or loginPage로 redirect)
16. JPA 연관관계 매핑으로 글 작성자만 수정, 삭제 가능하게 하기

![image](https://github.com/miyaey/receipeboard/assets/148731548/2573b41b-5a23-424a-a6f2-11220876f10c)
<br/><br/>
ERD
<br/>
![receipeboard_db](https://github.com/miyaey/receipeboard/assets/148731548/98ca61d0-b92a-44cb-9953-76bf36b5c54b)

## 💻결과 화면
![레시피게시판_1](https://github.com/miyaey/receipeboard/assets/148731548/8053667c-1fdb-41b8-b03c-0b68a2993b1c)
![레시피게시판_2](https://github.com/miyaey/receipeboard/assets/148731548/d36b282f-8c3d-409f-ace9-24dce47e50e1)
![레시피게시판_3](https://github.com/miyaey/receipeboard/assets/148731548/3e8ef7b6-8b15-44d4-9d0e-11848af087fe)
![레시피게시판_4](https://github.com/miyaey/receipeboard/assets/148731548/a327dc4c-7262-473a-9aad-3f45ce84ced5)
![레시피게시판_5](https://github.com/miyaey/receipeboard/assets/148731548/ff934038-73f4-40f6-a928-25b86a99a1e1)
![레시피게시판_6](https://github.com/miyaey/receipeboard/assets/148731548/35fef6ba-751f-4ad5-ad7e-ad57533a2eb0)
![레시피게시판_7](https://github.com/miyaey/receipeboard/assets/148731548/50c7154a-de1a-46ca-be75-1b603a8e4a97)






