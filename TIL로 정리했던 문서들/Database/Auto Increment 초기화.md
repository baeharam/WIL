디비 테스트를 하다보면 primary key에서 auto increment가 됨에 따라 다 지울 경우에도 그 이후의 id값으로 지정되는 경우가 많다. 이를 위해 auto increment를 초기화시켜야 한다. MySQL에선 다음과 같다.

```mysql
ALTER TABLE [테이블명] AUTO_INCREMENT = [시작할값]
```

만약 초기화 후에 테이블의 데이터들에 대해서 재조정하고 싶다면 다음과 같이 할 수 있다.

```mysql
ALTER TABLE [테이블명] AUTO_INCREMENT = 1;
SET @COUNT = 0;
UPDATE [테이블명] SET [AUTO_INCREMENT 열 이름] = @COUNT:=@COUNT+1;
```

