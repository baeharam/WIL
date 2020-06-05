# SQL 연습 겸 정리

기본적인 것 말고 모르는 것만 정리하기로 한다.

* **SELECT DISTINCT**

```mysql
SELECT DISTINCT column1, column2.. FROM table_name;
```

중복된 tuple을 제외하고 distinct tuples만 가져온다.

* **ORDER BY**

```SQL
SELECT column1, column2.. FROM table_name ORDER BY column1, column2, ... ASC|DESC;
```

기본이 Ascending order (오름차순)이며 ORDER BY 뒤에 오는 column 기준으로 정렬한다.

* **UPDATE**

```mysql
UPDATE table_name SET column1=value1, column2=value2,... WHERE condition;
```

특정 value를 수정한다. 조심해야 할 것은 condition 부분을 주의해서 써야 한다는 것!

* **DELETE**

```mysql
DELETE FROM table_name WHERE condition;
```

condition 을 만족하는 tuple을 삭제한다.

* **LIMIT**

```mysql
SELECT column_name(s) FROM table_name WHERE condition LIMIT number;
```

LIMIT의 개수만큼 tuple을 가져온다.

* **SHOW**

```mysql
SHOW FULL COLUMNS FROM table_name;
```

MAX/MIN 함수를 공부하다가 문자열의 경우 어떤 식으로 비교를 할까? 하는 의문점이 들어서 찾아봤더니 MySQL에는 수십개의 Character set이 있었고 각각의 Character set에 대한 collation이라고 부르는 비교 기준이 있었다. 이 SHOW 함수는 각 column에 대해서 어떤 collation을 사용하고 있는지 보여준다. (물론 다른 속성도 보여줌)

* **LIKE**

```mysql
SELECT column_name(s) FROM table_name WHERE column_name LIKE "a_";
SELECT column_name(s) FROM table_name WHERE column_name LIKE "a%";
```

a_는 _(underscore)가 문자 1개를 의미하며 a%에선 %가 아무 문자열을 의미한다. 앞 뒤 각각 쓸 수 있고 양쪽 동시에 쓸 수도 있으며 WildCard라고 불리는 문자이다. a\_라는 것은 a로 시작하는 2개의 단어를 의미하며 a%라는 것은 a로 시작하는 문자열을 의미한다.

* **IN**

```mysql
SELECT column_name(s) FROM table_name WHERE column_name IN (value1, value2,...);
```

column의 값들 중 IN 뒤에 오는 값들이 속한 tuple을 가져온다.

* **BETWEEN**

```mysql
SELECT column_name(s) FROM table-name WHERE column_name BETWEEN value1 and value2;
```

column의 값들 중 value1 이상 value2 이하 되는 값들이 속한 tuple을 가져온다.

* **JOIN**

```mysql
SELECT column_name(s) FROM table1 INNER JOIN table2 ON table1.column_name=table2.column_name;

SELECT column_name(s) FROM table1 LEFT JOIN table2 ON table1.column_name=table2.column_name;

SELECT column_name(s) FROM table1 RIGHT JOIN table2 ON table1.column_name=table2.column_name;
```

Table 2개를 특정 조건에 따라 Natural Join 시켜서 임시 table을 생성하는 함수이다.

1. **(INNER) JOIN** : Natural Join 한 값들을 가져오는데 table1, table2에 값이 모두 있어야 한다.
2. **LEFT (OUTER) JOIN** : Natural Join 한 값들을 가져오는데 table2의 값은 없어도 된다.
3. **RIGHT (OUTER) JOIN** : Natural Join 한 값들을 가져오는데 table1의 값은 없어도 된다.
4. **FULL (OUTER) JOIN** : <u>MySQL에선 지원되지 않기 때문에</u> LEFT JOIN 과 RIGHT JOIN을 UNION해서 사용한다. Natural Join 한 값들을 가져오는데 table1에 있거나 table2에만 있으면 된다.

* **GROUP BY**

```mysql
SELECT column_name(s) FROM table_name WHERE condition GROUP BY column_name(S);
```

Aggeregate functions (COUNT, MAX, MIN, SUM AVG) 와 자주 사용되며 결과를 1개 이상의 columns로 그룹화 시켜서 가져오는 함수이다. 단 조건이 있는데, SELECT 할 수 있는 columns는 aggregate function과 GROUP BY가 적용되는 columns라는 것이다. 이걸 지키지 않으면 에러가 발생한다.

* **HAVING**

```mysql
SELECT column_name(s) FROM table_name WHERE condition GROUP BY column_name(s) HAVING condition
```

WHERE는 아직 그룹화 되지 않은 table에 조건을 줄 때 사용하며, HAVING은 그룹화 된 table에 조건을 줄 때 사용된다. 따라서 WHERE로 조건을 준 임시 table에도 HAVING을 사용할 수 있다. 

* **저장 엔진**

MySQL은 크게 두 가지 기능으로 나눌 수 있는데, 첫 번째는 접속 기능과 SQL 문의 내용을 사전에 조사하는 기능으로 데이터베이스의 상위 부분이고, 두 번째는 상위 부분의 지시를 받아 실제로 검색이나 파일을 조작하는 하위 부분이다. 이 하위부분을 저장엔진 이라고 한다. MySQL 5.5 이후의 기본 엔진은 InnoDB이며 5.1까지는 MyISAM이 설정되어있다. MyISAM은 속도는 매우 빨랐으나 Transaction을 지원하지 않았다. ([설명 출처](http://recoveryman.tistory.com/187))

이러한 저장엔진을 보여주는 쿼리가 있어서 위의 개념을 한번 가져와봤다. 쿼리는 다음과 같다.

```mysql
SHOW CREATE TABLE table_name;
```

table의 상세적인 정보를 표시해주며 ENGINE과 CHARSET 또한 알 수 있다. 이 쿼리를 썼을 때 표시되는 데이터가 많아서 보기가 불편할 수 있는데 그 때는 세미콜론(;) 대신 `\G` 를 사용하면 좀 더 보기 편하게 표시되며 이 방법은 이 때 뿐만 아니라 데이터가 많을 때 많이 쓰는 방법이다.

* **Foreign key 확인 방법**

foreign key가 참조하는 테이블의 값이 삭제/업데이트가 됬을 때, foreign key가 있는 튜플에 대한 처리를 어떻게 할 것인가에 대해 공부하다가 데이터베이스 혹은 특정 테이블에 어떤 foreign key가 존재하는지 알아낼 수 있는 쿼리를 발견했다.

```mysql
# 데이터베이스에 존재하는 모든 foreign key
SELECT * FROM information_schema.table_constraints WHERE table_name="테이블명";
# 특정 테이블에 존재하는 모든 foreign key
# 위의 쿼리 + WHERE 조건
```

이런식으로 처리를 해주지 않는다면 Referential Integrity를 위반하기 때문에 반드시 이런 경우를 고려해주어야 한다.

* **DEFERABLE(MySQL 지원 안함)**

`DEFERABLE`은 해당 쿼리의 적용에 대한 constraints를 검사하는 옵션이며 다음과 같은 경우에 사용된다.

```mysql
CREATE TABLE A(
	X INT PRIMARY KEY,
    Y INT REFERENCES B(Y)
);
CREATE TABLE B(
	Y INT PRIMARY KEY,
    X INT REFERENCES A(X)
);
```

서로의 primary key를 참조하고 있는 foreign key를 같이 만든 경우다. 두 테이블에 대해서 다음 트랜잭션을 실행했을 때 `DEFERABLE` 옵션에 따라 어떤 일이 발생하는지 알아보자.

```mysql
START TRANSACTION;
INSERT INTO A VALUES(10,20);
INSERT INTO B VALUES(20,10);
COMMIT;
```

처음 `INSERT`에서 A에 10과 20을 넣지만 20은 foreign key이기 때문에 테이블 B에 존재해야 한다. B 테이블엔 아직 해당 튜플이 없기 때문에 statement 단위로 실행할 경우 constraint를 위반한다. 하지만 만약, 트랜잭션을 커밋하기 바로 전에 같이 실행한다면 얘기가 달라진다. Constraint 검사를 2개의 `INSERT` 쿼리가 수행되고 하기 때문에 두 foreign key에 대한 constraint 검사를 수행하기 때문이다.

`DEFERABLE` 에는 다음 3가지가 있다.

1. `NOT DEFERABLE` : Default 값으로 row 단위로 constraint를 체크한다.
2. `INITIALLY IMMEDIATE` : statement 단위로 constraint를 체크한다.
3. `INITIALLY DEFERRED`  : transaction 단위로 constraint를 체크한다.

다라서 위에서 발생한 문제는 `INITIALLY DEFERRED`로 해결할 수 있고 `NOT DEFERABLE`과 `INITIALLY IMMEDIATE`의 차이점이 헷갈릴 수 있는데 [다음 설명](https://stackoverflow.com/questions/5300307/not-deferrable-versus-deferrable-initially-immediate/44723053#44723053)이 명쾌하니 참고하도록 하자.

* [**CAST**](https://www.w3schools.com/sql/func_mysql_cast.asp)

```mysql
SELECT * FROM movie_list ORDER BY CAST(cumulative_audience AS UNSIGNED) DESC
```

말 그대로 캐스팅하는 쿼리문이다. DB 파이널 프로젝트를 하면서 `cumulative_audience` (누적관객) 기준으로 내림차순 정렬을 시도하려고 했으나 처음에 문자열로 컴마를 찍어서 넣는 바람에 데이터를 전부 바꿔야 했다. 그래서 방법이 없나 생각을 해보다가 `CAST`를 사용하면 숫자로 변환할 수 있다고 해서 내림차순 정렬을 할 수 있었다.

* **[MySQL 5.7.21 비밀번호 변경 방법](https://dev.mysql.com/doc/refman/5.7/en/resetting-permissions.html)**

```mysql
USE mysql
UPDATE mysql.user SET authentication_string=PASSWORD("NewPassword"), password_expired="N"
WHERE User="root" AND Host="localhost";
FLUSH PRIVILEGES;
```

DB 파이널 프로젝트 때문에 비밀번호를 통일하다 보니 원래 비밀번호에서 바뀌어서 다시 원래 비밀번호로 복구하려다 보니 찾게 되었고 5.7.21 버전의 경우 다음 쿼리로 리셋시킬 수 있다.

* **[Transaction의 특성](https://dev.mysql.com/doc/refman/8.0/en/commit.html)**

Transaction을 사용할 때는 3가지 조건으로 쿼리를 날릴 수 있다.

```mysql
START TRANSACTION 
WITH CONSISTENT SNAPSHOT
READ WRITE
READ ONLY
```

트랜잭션을 `READ ONLY`로 하는 경우, 트랜잭션 안에서 되는 쿼리는 SELECT 쿼리 밖에 없다.

* **[Isolation level](https://dev.mysql.com/doc/refman/5.7/en/innodb-transaction-isolation-levels.html#isolevel_serializable) 테스트 하기**

트랜잭션은 병렬로 수행될 때 isolation level에 따라서 보여지는 정보가 다르다는 것은 이전에 배웠던 내용이다. 지금은 isolation level을 테스트하는 방법에 대해서 알아보자. 

먼저, 병렬수행을 하기 위해 MySQL 모니터 2개를 킨다. MySQL 5.7.12 기준에선 isolation level이 default 값인 REPEATABLE READ로 설정되어 있다. 제일 개방적인 READ UNCOMMITTED 부터 테스트하기 위해서 다음과 같이 트랜잭션의 isolation level을 다시 설정한다.

```mysql
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
```

두개의 MySQL 모니터를 모니터1, 모니터2라고 하고 위 쿼리를 실행한 모니터를 모니터2라고 하자. 이 경우에 둘 다 트랜잭션을 수행하면서 모니터2로 ISOLATION LEVEL을 바꿔가며 테스트 하면 어떤 건지 확실히 와닿을 것이다.

* **[foreign key 에러로그 체크하기](https://stackoverflow.com/a/15535110/9437175)**

Constraint를 연습하면서 foreign key를 테이블에 추가하려는 도중에 에러가 생겨서 왜 생기는지 구글링 해봤더니 현재 InnoDB의 상태를 보여주는 쿼리가 있어서 적어본다. 여기서 LATEST FOREIGN KEY ERROR 부분을 보면 이유가 나온다.

```mysql
SHOW ENGINE INNODB STATUS;
```

* **[테이블 삭제 오류](http://blog.funexlab.com/mysql-%EC%82%AD%EC%A0%9C-%EC%97%90%EB%9F%AC-cannot-delete-or-update-a-parent-row-a-foreign-key-constraint-fails/)**

foreign key를 가진 테이블을 삭제했는데 그 foreign key에 해당하는 primary key를 가지는 테이블이 안 지워지는 에러가 발생했다.... 어떻게 해야할지 몰라 찾아봤더니 foreign key를 체크하는 속성을 잠시 disable 시키면 되었다.

```mysql
SET foreign_key_checks=0;
```

라고 생각을 하던 찰나에 여전히 데이터베이스에 foreign key가 존재하여 존재하지 않는 column을 참조 하고 있다는 에러가 발생했다. 그래서 데이터베이스의 foreign key를 조회하기 위한 쿼리를 검색해 보았다.

```mysql
SELECT * FROM information_schema.table_constraints WHERE constraint_schema='handong'
```

handong은 데이터베이스 이름이고 해당 데이터베이스에 있는 모든 constraints를 보여주기 때문에 foreign key 뿐만 아니라 primary key도 보여준다. 제발 foreign key 만들 때 조심하자!!

* **Materialized view를 trigger로 구현하기**

MySQL에서는 materialized view를 지원하지 않지만 table을 만들고 trigger를 설정하면 view의 refresh 효과를 추가할 수 있기 때문에 mimic(흉내) 할 수는 있다. 이걸 시연해보느라 애먹었다ㅠ

```mysql
CREATE TABLE materialized_view_movies
(
	title VARCHAR(30), year INT(2), length INT(2)
);
```

먼저 `movies`라는 table이 있는데 title,year,length,genre,studioName 이라는 attributes가 있다. 지금 만들 materialized view는 title,year,length만 뽑아내서 `movies`에 "haram"이라는 title의 영화가 추가됬을 때 materialized view에도 추가를 시키는 기능을 가진다. 따라서 먼저 해당되는 table인 `materialized_view_movies`를 만들어주었다.

이제 기존의 값들을 추가해주자.

```mysql
INSERT INTO materialized_view_movies SELECT title,year,length FROM movies;
```

기존의 movies 와 똑같은 값을 가지며 trigger만 추가해주면 끝난다.

```mysql
DELIMITER $$
CREATE TRIGGER movies_add_trigger
AFTER INSERT ON movies
FOR EACH ROW BEGIN
IF NEW.title="haram" THEN
INSERT INTO materialized_view_movies (title,year,length) VALUES(NEW.title, NEW.year, NEW.length);
END IF;
END$$
DELIMITER ;
```

실제로 시연해보면 `movies` 에 값이 추가되었을 때 title이 "haram"이라면 `materialized_view_movies`에도 해당 title, year, length가 추가되는 것을 알 수 있다.

* **[INDEX 확인하기](https://stackoverflow.com/a/5213364/9437175)**

INDEX에 대해서 복습하면서 primary key는 어떤 데이터베이스 엔진이든지 INDEX로 자동 설정된다는 것을 알았다. 그걸 보기 위해서 INDEX를 어떻게 확인할 수 있는지 알아본 것이 다음과 같다.

```mysql
SHOW INDEX FROM movies;
```

`movies`  테이블에 있는 INDEX를 전부 보여주는 쿼리이다. 데이터베이스 기준으로 INDEX를 전부 보여줄 수도 있다.

```MYSQL
SELECT DISTINCT TABLE_NAME,INDEX_NAME INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA='handong';
```

handong이라는 DB에 설정된 모든 INDEX를 보여준다.





