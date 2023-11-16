/*회원 가입*/
Insert into users (EMAIL,nickname,password) values ('1@1','회원1','$2a$10$cMrNo3akE/TONxwuC4yqoO2Ft9vmY7v7lb3tf3AqjxVhEJ0hDkyzC');/*비번111*/
Insert into users (EMAIL,nickname,password) values ('2@2','회원2','$2a$10$U.sHemhtKgx5rWSf.SWDduY1xmuTJoNIEUdNAQRa9p.KDKnupZGLy');/*222*/
/* 캐릭터 생성 */
Insert into Game_Characters (EMAIL, HIGH_SCORE,NICKNAME) values('1@1', 150 , 'jhon');
Insert into Game_Characters (EMAIL, HIGH_SCORE,NICKNAME) values('2@2', 150 , 'jhon');
/* 랭크 구현을 위한 점수들이다..*/
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('2ex', 170 , 'jhon', '2023-11-30 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex', 130 , 'jhon','2023-10-21 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('2@3eax', 450 , 'jhon','2023-09-20 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('2@3sex', 250 , 'jhon','2023-05-27 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3exd@2', 123 , 'jhon','2023-12-21 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('2@32ex', 1654 , 'jhon','2023-12-11 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('2@113ex', 187 , 'jhon','2023-12-11 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex56@2', 987 , 'jhon','2023-12-13 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex233@2', 321 , 'jhon','2023-12-15 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex34@2', 123 , 'jhon','2023-12-16 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex4@2', 215 , 'jhon','2023-12-17 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('2@321ex', 184 , 'jhon','2023-12-19 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex1@32ex', 753 , 'jhon','2023-12-01 23:59:59');
Insert into Game_High_Scores (EMAIL, HIGH_SCORE,game_character_nickname,lasted_time) values('3ex1@3ex', 73 , 'jhon','2023-12-10 23:59:59');


/* 자유 게시판 글 생성 */
Insert into FREE_BOARD(created_date, modified_date, content, title, writer_id) values(now(),now(),'내용1','제목1','1@1');
Insert into FREE_BOARD(created_date, modified_date, content, title, writer_id) values(now(),now(),'내용2','제목2','2@2');

