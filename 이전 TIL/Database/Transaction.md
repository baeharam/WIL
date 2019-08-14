# Transaction(트랜잭션)

트랜잭션은 데이터베이스에서 수행의 기본 작업단위로 다음과 같이 정의된다.

**"A group of operations(i.e, query executions) that need to be performed together, entirely."**

이걸로부터 여러개의 쿼리를 묶어서 실행한다는 사실을 알 수 있으며, 예전에 배웠던 ACID 속성을 그대로 가진다. ACID 속성은 트랜잭션이 충족해야 하는 기술적인 요건이며 다음과 같다.

* **Atomicity (원자성)** : 트랜잭션의 처리는 완전히 끝마치지 않았을 경우에, 전혀 이루어지지 않은 것과 같아야 한다.
* **Consistency (일관성)** : 트랜잭션이 실행을 성공적으로 완료하면 언제나 일관성 있는 데이터베이스 상태로 유지하는 것을 의미한다.
* **Isolation (고립성)** : 트랜잭션의 부분적인 상태를 다른 트랜잭션에 제공해서는 안된다.
* **Durability (지속성)** : 성공적인 트랜잭션의 수행 후에는 반드시 데이터베이스에 반영되어야 한다.

데이터베이스에서 트랜잭션을 수행하는 이유는 Consistency를 유지하기 위함이다. 다음의 예를 보도록 하자.

> 비행기 좌석을 예매하는 시스템이 있다고 하자. 유저1과 유저2가 존재하며 예약이 다음과 같이 이루어질 경우 문제가 발생할 수 있다.
>
> 1. 유저1이 좌석 A가 비어있는 것을 발견했다.  
> 2. 유저2도 좌석 A가 비어있는 것을 발견했다.
> 3. 유저1이 좌석 A를 예매했다!
> 4. 유저2도 좌석 A를 예매했다.

이렇게 되면 좌석 A에 대해서 중복예약이 발생한다. 실제 시스템에서 그러지는 않겠지만 만약 이런 상황에 대해 아무런 대책도 없다면 유저1이 좌석 A를 먼저 예매했음에도 유저2가 좌석 A를 예매한 것처럼 될 것이다. 그래서 다음과 같은 순서로 서비스가 이루어져야 한다.

>1. 유저1이 좌석 A가 비어있는 것을 발견했다.  
>2. 유저1이 좌석 A를 예매했다!
>3. 유저2가 좌석 A가 비어있는 것을 발견했다.
>4. 유저2가 좌석 A를 예매하려 하지만 에러가 발생한다.

유저1의 발견과 동시에 예매를 하게 하면 유저2가 발견할 때는 이미 예매가 된 상태이므로 비어있다는 표시가 있어도 실제 예매는 불가능한 상황이다.

이런식으로 여러개의 쿼리를 묶어서 한번에 실행하고 Consistency를 유지하는 것을 트랜잭션이라고 하는 것이다.

# Declaring Transactions (트랜잭션의 수행)

```mysql
START TRANSACTION;
...
COMMIT; 또는 ROLLBACK;
```

* **Trasaction statement** : `START TRANSACTION` 이후로 나오는 쿼리들을 atomically 묶어서 실행하겠다는 의미이다. 만약 명시적으로 써주지 않으면 모든 쿼리는 하나의 트랜잭션으로 취급된다.
* **Commit statement** : `COMMIT` 명령어를 씀으로, 이제까지 실행한 쿼리들의 결과값을 실제 데이터베이스에 반영하겠다는 의미이다.
* **Rollback statement** : `ROLLBACK` 명령어를 씀으로, 이제까지 실행한 쿼리들의 결과값을 실제 데이터베이스에 반영하지 않고 다시 되돌리겠다는 의미이다.

```mysql
START TRANSACTION;
SELECT title, year FROM movies WHERE studioName="MARVEL";
UPDATE movies SET year=2015 WHERE title="About time";
COMMIT;
```

위의 예제에선 트랜잭션을 시작한 후 `SELECT`로 title,year를 movies 에서 뽑아왔고, movies의 "About time"이란 영화의 year 값을 2015로 `UPDATE` 시켰다. 그리고 마지막에 `COMMIT`을 해줌으로 실제 데이터베이스에 반영된 것이다.

# Read-Only Transaction

```mysql
SET TRANSACTION READ ONLY;
START TRANSACTION;
```

직역하면 알 수 있다시피 `SELECT` 쿼리만 가능한 트랜잭션의 옵션이다. (기본 트랜잭션은 `READ WRITE`임) `READ WRITE` 옵션 보다 트랜잭션의 수행을 더 병렬적으로 수행하게 할 때 유용하다고 한다. [MySQL](https://dev.mysql.com/doc/refman/5.6/en/innodb-performance-ro-txn.html)에선 Write 할 때 트랜잭션 id가 필요하기 때문에 overhead를 줄여준다는데, 심도있게는 잘 모르겠다.

# Possible issues in isolation levels

트랜잭션 A와 트랜잭션 B가 병렬수행되고 있다고 하자.

* **Phantom Read** : A가 `SELECT`를 여러번 하는데 그 사이에 B가 `INSERT`를 하고 `COMMIT`을 하게 되면 2번째로 하는 `SELECT`의 결과가 달라진다. 이걸 ghost tuple이 나타났다고 하여 Phandom read라고 한다.
* **Nonrepeatable Read** : A가 `SELECT`를 여러번 하는데 그 사이에 B가 `UPDATE`를 하고 `COMMIT`을 하게 되면 2번째로 하는 `SELECT`의 결과가 역시 달라진다.
* **Dirty Read** : A가 `SELECT`를 여러번 하는데 그 사이에 B가 여러개의 쿼리를 실행한 후 아직 `COMMIT`은 하지 않은 상태에서 A가 `COMMIT`하지 않은 데이터를 `SELECT`하는 현상이다.

# Isolation Levels of Transaction

```mysql
SET TRANSACTION ISOLATION LEVEL <level>;
START TRANSACTION;
...
```

`SET`을 통해 트랜잭션의 isolation level을 정할 수 있으며 SQL은 총 4개의 isolation level을 지원한다.

* `SERIALIZABLE` : 가장 엄격한 isolation level로 다른 트랜잭션의 어떠한 병렬수행도 허용하지 않는다.
* `REPEATABLE READ` : Phantom read만 허용하는 isolation level로 트랜잭션의 병렬수행 중에 `COMMIT`한 `INSERT`만 허용한다.
* `READ COMMITTED` : Phantom read와 Nonrepeatable read를 허용하는 isolation level로 트랜잭션의 병렬수행 중에 `COMMIT`한 `INSERT`와 `UPDATE`만 허용한다.
* `READ UNCOMMITTED` : 모두 다 허용하는 것으로 `COMMIT`이 됬던 안 됬던 상관없다.

트랜잭션의 isolation level이 높아질수록 병렬수행을 제한하는 것이므로 속도가 느려지는 것은 당연하다. 따라서 데이터베이스 디자이너가 적당한 isolation level을 고르는 것이 중요하다.