## refspec

git을 사용하다보면 에러메시지에 `refspec` 이란게 나올 때가 있는데 어떤 건지 알아보자. git은 커밋을 40자리 해시값으로 키를 만들어 키-값 쌍으로 저장한다. 이 해시값은 사람이 기억하기 힘들기 때문에 파일에 저장이 되는데, `.git/refs/` 에 모든 작업이 저장된다. 이 디렉토리에는 `heads` , `remotes` , `tags` 디렉토리가 있고 `heads` 는 로컬, `remotes` 는 원격 저장소들을 관리한다. 즉, 어떤 특정한 작업을 가리키는 `refs` 가 "브랜치" 가 된다. 예를 들어, 로컬의 `master` 브랜치라고 하면 다음과 같이 해시값이 나온다.

```bash
$ cat .git/refs/heads/master
9479803e800dc881699539cdfc58d097a663e9b0
```

이제 `refspec` 을 보면, **"원격 저장소의 refs를 로컬 저장소에 어떻게 매핑시킬 것인가"** 에 관한 정보를 담고 있다고 할 수 있다. `.git/config` 파일을 보면 원격 저장소를 추가했을 때 설정된 `refspec` 정보를 볼 수 있다.

```bash
[remote "origin"]
	url = git@github.com:baeharam/git-practice.git
	fetch = +refs/heads/*:refs/remotes/origin/*
```

원격 저장소를 "origin" 이라는 이름으로 로컬에 추가했을 때 다음과 같은 `refspec` 이 생기는데 여기서 `fetch` 부분을 보도록 하자.

```
fetch = +refs/heads/*:refs/remotes/origin/*
```

3가지 부분으로 나눌 수 있다.

* `+` : fast-forward로 merge 할 수 없어도 가져오라는 말이다.
* `refs/heads/*` : 원격 저장소 "origin"의 모든 브랜치 패턴
* `refs/remotes/origin/*` : 원격 저장소 "origin" 의 모든 브랜치를 매핑시킬 패턴

즉, 정리해서 말하자면 fast-forward 방식이 아니더라도 원격 저장소의 모든 브랜치를 지정한 패턴(경로)으로 매핑해서 가져오라는 의미이다. `git push` 의 경우도 풀네임으로 작성하자면 다음과 같이 쓴다. 예를 들어, `origin` 의 `master` 브랜치에 푸시한다고 가정하자.

```bash
$ git push origin refs/heads/master:refs/heads/master
```

`:` 앞쪽에 있는 `refs/heads/master` 는 원격 저장소에 푸시할 로컬 브랜치이고 뒤쪽에 있는 `refs/heads/master` 는 업데이트할 원격 브랜치이다.

<br>

## 참고

* [Git push/fetch 내부 동작 정리](https://hongsii.github.io/2018/11/08/git-push-fetch/)
* [Git - What is “Refspec”](https://stackoverflow.com/questions/44333437/git-what-is-refspec)
* [Git push with full refspec not working](https://stackoverflow.com/questions/48422135/git-push-with-full-refspec-not-working)
* [브랜치 통합하기](https://backlog.com/git-tutorial/kr/stepup/stepup1_4.html)