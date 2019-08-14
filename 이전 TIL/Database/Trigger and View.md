# Trigger (트리거)

> A user can define an event for an insertion, deletion, or update of a particular relation or a transaction end.

트리거란 테이블이나 뷰에 대해 어떤 이벤트가 발생했을 때 원하는 이벤트를 자동으로 처리하는 프로세스로 로그데이터를 남길 때 사용한다고 한다. 즉 `INSERT`, `UPDATE`, `DELETE`와 같은 쿼리가 발생했을 때 해당 테이블이 아닌 다른 테이블에 이러한 쿼리를 실행하는 하나의 프로세스를 정의하여 사용하는 것이다. movies 테이블과 starsin 테이블이 있다고 하면 다음과 같이 실행할 수 있다.

```mysql
DELIMITER $$
CREATE TRIGGER movie_trigger
AFTER INSERT ON movies
FOR EACH ROW
BEGIN
UPDATE starsin SET movieYear=movieYear+1 WHERE movieTitle="Love Actually";
END $$
DELIMITER;
```

굉장히 복잡해보이지만 알고보면 간단하다. 한가지씩 알아보자.

* `DELIMITER $$` : `DELIMITER`는 쿼리를 끝낼 때 세미콜론(;)을 쓰는데 그걸 다른 걸로 바꾸는 쿼리이다. 여기선 쿼리가 상당히 많기 대문에 $$로 명확히 구분할 수 있게 바꾸어주었다.
* `CREATE TRIGGER movie_trigger` : movie_trigger라는 이름의 트리거를 생성한다.
* `AFTER INSERT ON movies` : movies 테이블에 `INSERT`를 수행한 후 트리거를 수행한다.
* `FOR EACH ROW` : 각 행에 영향을 준다.
* `BEGIN`/`END` : 트리거의 수행내용이 이 안에 정의된다.
* `DELIMITER;` : 다시 쿼리가 끝나는 기호를 세미콜론(;)으로 바꾼다.

이렇게 정하면 movies 테이블에 데이터를 삽입했을 때, starsin 테이블의 movietTitle이 "Love Actually"인 튜플에 대해서 movieYear 값을 1 증가시키는 트리거가 생성된 것이라고 할 수 있다.

트리거는 테이블 당 한개밖에 가질 수 없는 데 그 종류에 따라서 다르다. 예를 들어, `BEFORE UPDATE`와 `AFTER UPDATE`는 둘 다 가질 수 있고 `BEFORE UPDATE`와 `BEFORE INSERT`도 둘 다 가질 수 있다. 따라서 어떤 트리거가 있는지 알아볼 경우가 생기는데 그 때 다음 쿼리를 사용한다.

```mysql
SHOW TRIGGERS\G;
```

`\G`는 옵션이긴 하지만 해보니까 제대로 표시가 안되서 가독성을 위해 하는 것이 좋을 것 같다. 이걸 통해서 어떠한 트리거가 있는지 자세히 확인할 수 있다. 트리거를 삭제할 때는 비슷하게 쓰면된다.

```mysql
DROP TRIGGER trigger_name;
```



# View (뷰)

> Artificial relations defined by the data from existing relations

처음 뷰가 쿼리로 생성된 임시 테이블인 줄 알았는데 그게 아니라, 하나 이상의 테이블로부터 유도된 이름을 가진 가상 테이블이다. 실제 데이터를 저장하고 있지 않기 때문에 데이터베이스에 물리적으로 저장되는 것이 아니라 SQL Server의 metadata로 저장된다고 하는데 [다음](https://stackoverflow.com/a/14749113/9437175)을 보자.

뷰는 다음과 같은 장점을 가질 수 있다.

* 뷰에 대해서만 쿼리를 실행하는 것으로 간단한 인터페이스를 가질 수 있다.
* 뷰가 참조하는 테이블을 변경할 경우 뷰에 자동으로 반영된다.
* 테이블의 일부 데이터만을 참조해서 보여주기 때문에 user에게 보일 필요가 없는 데이터나 봐서는 안되는 데이터를 감출 수 있다.
* 일부 데이터베이스 엔진에선 (예 : MS SQL Server) 뷰가 인덱스를 가질 수 있게 한다.

뷰를 생성하면 `SHOW TABLES` 쿼리로 볼 수 있다.

## 뷰의 생성 및 삭제

* 생성

```mysql
CREATE VIEW view_creation AS
SELECT * FROM movie_list LIMIT 5;
```

뷰를 연습하기 위해 날려본 쿼리인데 예제로 괜찮은 것 같아서 적는다. `AS` 앞에 있는 것이 뷰의 이름이고 뒤에 있는 것이 뷰로 만들 쿼리문이다.

* 삭제

```mysql
DROP VIEW view_creation;
```

아주 간단하게 `DROP`만 써주면 된다.

# 뷰의 특징

* 참조하는 테이블에 column을 추가해도 뷰에 추가되지 않는다.
* 참조하는 테이블에서 뷰에 포함되었던 column을 삭제하면 에러를 발생시킨다.
  * 뷰에 `INSERT`, `DELETE`나 `UPDATE`를 쓰기 위해선 오직 1개의 테이블만 참조하여야 하며 각 row가 1대 1로 매칭되어야 한다.

