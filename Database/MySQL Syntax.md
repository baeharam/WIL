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