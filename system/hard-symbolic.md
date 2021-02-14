# Hard link vs Symbolic link

파일 시스템에서 파일은 inode 로 표현이 되는데 기본적으로, 파일이라는 것이 inode 에 대한 링크이다. 하드 링크는 동일한 inode 에 대해 링크를 가진 다른 파일을 만드는 것과 동일하다.

파일을 지우면, 해당 inode 에 대한 링크를 지우는 것이며 그 파일의 inode 에 대한 모든 링크가 지워졌을 때만 inode 가 지워지게 된다. 심볼릭 링크는 단순히 링크 이름을 가리키는 포인터이다. 하드 링크는 원본 파일을 지우거나, 이름을 다시 짓거나, 이동시키는 것으로 영향을 받지 않는다. 단, 데이터 자체에 대한 변화는 영향을 준다. 이와는 반대로, 심볼릭 링크의 경우 기존에 가리키고 있던 파일의 이름을 바꾸면 해당 링크가 깨지게 된다

## 테스트

* 더미 파일 2개 생성

```bash
touch bar
touch foo
```

* 각각 하드 링크와 심볼릭 링크 생성

```bash
ln bar bar-hard
ln -s foo foo-sym
```

* 원본 이름 변경

```bash
mv bar bar-new
mv foo foo-new
```

* 링크로 `cat` 

```bash
cat bar-hard
cat foo-sym # 에러 발생! (No such file or directory)
```



## 참고

* https://stackoverflow.com/a/185903/11789111
* https://stackoverflow.com/a/1531795/11789111