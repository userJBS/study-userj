insert into USER (ID,USER_ID, PASSWORD, NAME, EMAIL) VALUES ('1','id01', 'pass01', 'userj8164', 'user4825@naver.com');
insert into USER (ID,USER_ID, PASSWORD, NAME, EMAIL) VALUES ('2','id02', 'pass02', 'account02', 'email02@naver.com');



 insert into question (id, contents, title, writer_id, create_date, count_of_answer) values ('1', '안녕하세요~ USER-J 입니다.',  '안녕하세요~!', '1' , current_timestamp(), '0')
 insert into question (id, contents, title, writer_id, create_date, count_of_answer) values ('2', '자바 사랑ㅋㅋㅋㅋㅋ',  'I Love Javaㅋㅋㅋㅋ', '2' , current_timestamp(), '0')

-- insert into answer (id, contents, create_date, question_id, writer_id) values (1, '안녕하세요~ㅋㅋㅋ', current_timestamp(), '1', '2')
-- insert into answer (id, contents, create_date, question_id, writer_id) values (2, '의견 테스트~!', current_timestamp(), '1', '1')
 	