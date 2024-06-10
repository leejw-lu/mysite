-- guestbook_log
update guestbook_log set count=count+1 where date = current_date();
insert into guestbook_log values(current_date(), 1);

-- select date(reg_date) from guestbook where no=32 ;
update guestbook_log set count=count-1 where date = (select date(reg_date) from guestbook where no=32 );

select * from guestbook;
select * from guestbook_log;

-- mysite sql
insert into board values(null, 'Hi', 'contents', 0, now(), (select max(g_no)+1 from board) , 1, 0, 4);
insert into board select null, 'Hi', 'contents', 0, now(), max(g_no)+1 , 1, 0, 4 from board;

select max(g_no)+1 from board;

select a.name, b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'), b.g_no, b.o_no, b.depth from user a, board b where a.no=b.user_no and contents like '%13%' order by b.g_no desc, b.o_no asc limit 6 , 3;

update board set hit=hit+1 where no= ?;

select * from user;
select * from board;

select a.name, b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth from user a, board b where a.no=b.user_no limit 3 , 3;
select count(*), ceil(count(*)/3) from board;

insert into board values(null, 'Test', 'contents', 0, now(), 1, 1, 1, 7);
insert into user values(null, '123','123', password('123'), 'female', current_date());

insert into board select null, ?, ?, ?, now(), max(g_no)+1 , ?, ?, ? from board

