# Index

자료구조의 한 종류이다, 탐색을 할 때 BST가 가장 빠른 것으로 아는데 그걸 발전시킨 것이 B+ 트리이다. 1~7 VALUE를 가지고 있다고 했을 때, 6을 찾기 위해선 6보다 작은 ROOT의 5를 고르면 5가 가리키는 VALUE인 5로 가서 거기서부터 탐색을 한다. 

인덱스는 책에서 특정 주제를 찾을 때 이용하는 것과 같다. 인덱스가 없다면 전체 테이블의 첫번째 튜플부터 끝까지 찾아야 하기 때문에 굉장히 비효율적이다.

i2에서 사용자가 100명이 넘을 때 컴퓨터가 멈췄다. 

```mysql
SELECT * FROM article_list WHERE article_id="12345";
```

이런 쿼리가 i2에서 많이 사용되던 쿼리였는데 항상 튜플 처음부터 끝까지 탐색하기 때문에 굉장히 느렸다. 그러나 인덱스가 있을 경우, DBMS는 해당 튜플을 바로 찾을 수가 있다.

인덱스를 생성하는 방법은 다음과 같다.

```mysql
CREATE INDEX traverse_articles ON article_list(article_id);
```

이 쿼리 하나로 탐색 속도는 굉장히 빨라졌다. B+ 트리가 작동하는 원리는 BST와 비슷한데 key와 value를 이용해서 탐색하는 측면이 다르다. 

인덱스는 평균적으로 탐색 속도가 굉장히 빠르긴 하지만 처음 튜플에 대해선 속도가 느릴 수도 있다.

튜플과 인덱스가 저장되는 방식은 하드디스크의 page라는 곳에 저장되며 엄청난 양의 메모리를 가지고 있다. 따라서 하나의 튜플에 접근할 때 하나의 page가 메모리에 올라가게 되기 때문에 DBMS의 성능을 결정하는 중요한 요인이 된다. 당연히 인덱스의 메모리는 튜플이 차지하는, 즉 테이블이 차지하는 인덱스보다 작다.

또한 인덱스는 단번에 튜플을 찾아내야 하기 때문에 대부분의 DBMS가 인덱스를 key로 설정한다. 

시스템에 따라서 자주 사용하는 쿼리는 다르며 SELECT를 할 때 B+ 트리가 빠르긴 하지만 INSERT나 UPDATE를 할 때는 B+ 트리를 재구성해야 하므로 trade-off로서 성능저하가 발생할 수 있다. 따라서 database log를 분석하여 어떤 쿼리가 자주 쓰이는지 보고 인덱스를 만들지 말지 결정할 수 있다.

예제

모든 튜플이 10개의 page에 있다고 하고 다음 쿼리를 보자

```mysql
SELECT * FROM stars_in WHERE star_name = s;
```

인덱스가 없이는 같은 star_name을 갖는 튜플 3개가 3개의 page에 있다고 하면 SELECT를 했을 때 10개의 page를 체크해야 하기 때문에 시간이 너무 오래 걸린다.

```mysql
SELECT * FRMO stars_in WHERE title=t AND year=y;
```

이것도 10개의 page를 체크해야 한다.

```mysql
INSERT INTO stars_in VALUES(t,y,s);
```

마지막 page에 튜플 1개만 추가하면 되니까 read/write cost 2가 든다.