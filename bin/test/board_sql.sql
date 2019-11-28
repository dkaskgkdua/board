drop table BOARD2;
CREATE TABLE BOARD2(
	BOARD_NUM          NUMBER(5),         --�� ��ȣ(�⺻Ű)
	BOARD_NAME         VARCHAR2(30),   --�ۼ���
	BOARD_PASS          VARCHAR2(30),   --��й�ȣ
	BOARD_SUBJECT      VARCHAR2(300),  --����
	BOARD_CONTENT    VARCHAR2(4000), --����
	BOARD_FILE             VARCHAR2(50),   --÷�ε� ���� ��(�ý��ۿ� ����Ǵ� ���ϸ�)
	BOARD_ORIGINAL     VARCHAR2(50),   --÷�ε� ���� ��(�ý��ۿ� ����� ������ ���� ���ϸ�)
	BOARD_RE_REF         NUMBER(5),         --�亯 �� �ۼ��� �����Ǵ� ���� ��ȣ
	BOARD_RE_LEV         NUMBER(5),         --�亯 ���� ����(������:0, �亯:1, �亯�� �亯2, �亯�� �亯�� �亯 :3)
	BOARD_RE_SEQ         NUMBER(5),         --�亯 ���� ����(������ �������� �����ִ� ����)
	BOARD_READCOUNT NUMBER(5),         --���� ��ȸ��
	BOARD_DATE DATE,                --���� �ۼ� ��¥
	PRIMARY KEY(BOARD_NUM)
);


drop sequence board_seq;
create sequence board_seq;
select level, empno, ename from emp start with mgr is null connect by prior empno = mgr;
select * from board2;
select level, board_num, board_name from board2 start with board_re_ref is null connect by prior board_num = board_re_ref;
/*delete from board where board_num in(select board_num from board START WITH board_num = 14 connect by prior board_num = board_parent);
*/
select max(board_num) from board2;
select nvl(min(board_re_seq), (select max(board_num) from board2) group by board_num) from board2 where board_re_lev <= 1 and board_re_seq > 1;
select max(board_re_seq)+1 from board2;
select max(board_num) from board2 where board_re_lev <= 1 and board_re_seq > 1;
delete from board2 where BOARD_RE_REF = 2 and BOARD_RE_LEV >=2 and BOARD_RE_SEQ >= 2 and BOARD_RE_SEQ < (select nvl(min(board_re_seq), 10) from board2 where board_re_lev <= 2 and board_re_seq > 2;