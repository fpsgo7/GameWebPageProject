/*회원 가입*/
Insert into users (EMAIL,nickname,password) values ('1@1','회원1','$2a$10$cMrNo3akE/TONxwuC4yqoO2Ft9vmY7v7lb3tf3AqjxVhEJ0hDkyzC');/*비번111*/
Insert into users (EMAIL,nickname,password) values ('2@2','회원2','$2a$10$U.sHemhtKgx5rWSf.SWDduY1xmuTJoNIEUdNAQRa9p.KDKnupZGLy');/*222*/
/* 캐릭터 생성 */
Insert into CHARACTERS (EMAIL, HIGH_SCORE,NICKNAME) values('1@1', 150 , 'jhon');
Insert into CHARACTERS (EMAIL, HIGH_SCORE,NICKNAME) values('2@2', 150 , 'jhon');
/* 자유 게시판 글 생성 */
Insert into FREE_BOARD(created_date, modified_date, content, title, writer_id) values(now(),now(),'내용1','제목1','1@1');
Insert into FREE_BOARD(created_date, modified_date, content, title, writer_id) values(now(),now(),'내용2','제목2','2@2');

