# Game Web Page Project
> 게임 웹 페이지 프로젝트의 웹 페이지 파트로
> 기본적인 게시판 기능과, 게임과 관련한 기능을 추가하였다.
## 목차
1. [개요](#개요)
   - [프로젝트 소개](#프로젝트-소개)
   - [프로젝트 기능](#프로젝트-기능)
   - [사용기술](#사용기술)    
2. [실행 화면](#실행-화면)
   - [사용자관련 회원 가입과 로그인](#사용자관련-회원-가입과-로그인)
   - [사용자관련 기타](#사용자관련-기타)
   - [자유게시판 화면](#자유게시판-화면)
   - [댓글](#댓글)
   - [랭킹](#랭킹)
3. [DB 설계](#DB-설계)
4. [개발 내용](#개발-내용)

## 개요


### 프로젝트 소개
기본적인 웹 프로그래밍의 기본 소양인 게시판을 만들어보고자 시작하였으며 
게임 프로그래밍도 관심 있었기에 게임과 관련된 웹 프로젝트를 시작하였습니다.

### 프로젝트 기능
- 게시판 - CRUD 기능
- 사용자 - Security 회원가입 로그인, OAuth2 구글 로그인, 회원정보 수정
- 댓글 - CRUD 기능
- 랭크 - 페이징 기능
- 게임 http 요청 - 게임의 http 요청을 받아 데이터베이스를 수정해준다.

### 사용기술
백엔드
- Java11
- SpringBoot
- JPA
- Spring Security
- OAuth2.0
- Gradle


DB
- Mysql


프론트 엔드
- Html/Css
- JavaScript
- Thymeleaf
- Bootstrap

## 실행 화면

### 사용자관련 회원 가입과 로그인
#### 1. 회원가입
> 알맞은 정보를 입력한후 회원 가입 버튼을 클릭해주면된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/d138d136-d35e-485e-9010-5da3e2913db1)
> 여기서는 글자수와 이메일 중복 여부등, 문제가 있으면 위의 알림 창에서 알려주며 회원가입을 막는다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/51014da5-bff7-4240-9501-3ccdc0a2af12)

#### 2. 일반 로그인
> 아이디와 비밀번호를 입력한후 Submit 이라 적인 버튼을 클릭하면 로그인이된다.


![로그인](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/d063091d-30f1-455a-88a2-013b56e67c77)


#### 3. OAuth2 로그인
> 로그인 창에서 "Sign in with Google" 라고 적힌 버튼을 클릭하여 구글 로그인을 시도하면된다.

![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/c969d7c4-9727-465b-8b22-238478282021)
   

### 사용자관련 기타
#### 4. 회원 정보 수정
> 헤더의 (회원이름)님의 회원정보 수정 링크로 들어가면되며
> 바로 회원정보 수정이 가능하지 않고 누군가의 잘못된 수정을 막기위해 비밀번호로
> 사용자인지 확인후 회원 수정이 가능하다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/3fec3890-5a81-4a26-b60c-048d305a4c7f)


>회원 수정 창에서는 이름 수정, 비밀번후 수정, 회원 탈퇴가 가능하나 단 oaUTH2 로그인 시에는
>이름을 구글 계정의 이름을 사용하기 때문에 이름 수정이 없다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/9b3760ae-aa61-45f1-a8e9-82360ac2d28e)



#### 5. 캐릭터 정보
> 자신의 게임 캐릭터 정보를 보여주는 창으로 아직 게임을 시작하지 않아
> 캐릭터가 없을 경우에는 게임에서 캐릭터를 생성해달라 하며


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/200fe275-5baf-4212-9c58-607a47b91d30)


> 게임을 시작하여 게임 캐릭터가 있다면 해당 계정 캐릭터의 이름과 점수가 뜬다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/ad02b7bb-8abc-460b-8d3e-8f6040ad96fd)

### 자유게시판 화면
#### 1. 자유게시판 리스트 화면 겸 메인화면
![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/6dcc0b9b-fd70-495e-a3ff-0e9dbc613762)


헤더에서는 자유게시판 화면, 랭킹 , 캐릭터 정보 , 회원 정보 화면으로 이동할 수 있는 링크와 로그아웃 버튼이있다.
그리고 메인 화면에는 자유게시판 글들과 글 등록 버튼이 있다.
로그인 중이 아닐 때에는 "캐릭터 정보", "회원정보 수정" "글등록" 링크와 버튼은 사라지며, 로그아웃 버튼 대신 로그인 버튼이 있다.


#### 2. 글등록 화면
> 간단하게 제목과 내용을 입력후 등록 버튼을 클릭하면 등록이 된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/f9c32b0e-7470-4dc2-bc41-095b1fce6829)


#### 3. 글 화면
> 글 제목과 언제 쓰여졌는지 누가 작성하였는지 적혀있으며 댓글을 작성하는 공간이 존재한다
> 만약 내가 작성한 글이라면 수정 버튼과 삭제 버튼이 있으며 수정 버튼을 누르면
> 수정화면으로 삭제 버튼을 누르면 글이 삭제된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/64e713d2-81f9-40a4-a074-d398702a0541)


#### 4. 글 수정화면
> 제목과 내욜을 입력하는 공간에 원하는 글자로 수정한후 수정 버튼을 클릭하면 글이 입력한대로 수정된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/5a5a4b64-081f-40c7-8cb2-a5cbc78d4b9c)
> 수정 버튼을 누른 후 해당 글로 이동하면 수정 되었음을 확인 할 수 있다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/c19dede1-75b2-4f69-8b6b-fce5d1a14144)


#### 5. 글 삭제
> 자신이 쓴 게시글에서 삭제 버튼을 누르면 삭제가 진행된다


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/b561ec56-786d-499c-99bb-276e800abb8d)

### 댓글 
#### 1. 댓글 작성
> 로그인 한뒤 작성할 수 있는 뷰가 나타나며 내용 작성 후 제출을 누르면 해당 게시글에 추가된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/e974b49f-ad57-4bca-a727-3621a4f75d24)


#### 2. 댓글 보기에서 수정과 삭제
> 자유 게시판 글에서 자신의 댓글에는 수정 삭제 버튼이 각각 있다.
여기서 삭제 를 누르면 자신의 댓글은 삭제된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/1ea7b9ca-9b69-476e-b9b6-29c678d0017b)
> 수정 버튼을 누른다면 댓글 수정을 위한 입력란이 나타나며 글 입력후 수정 버튼을 누르면
> 댓글이 수정된다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/62693e95-6701-4f0b-8185-7c26a476c503)


### 랭킹
> 페이징을 사용하였으며 점수와 시간값을 통해 정렬되어 있다.
> 각 유저의 게임 캐릭터의 최고 점수를 기준으로 한다.


![image](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/0254a0af-7e9a-463f-8eb2-efb792814de9)



## DB 설계


![Untitled](https://github.com/fpsgo7/GameWebPageProject/assets/101778043/8710f193-f367-463c-b2de-547c319fd60b)

## 개발 내용
### 로그인 과정

### 페이징 방법

