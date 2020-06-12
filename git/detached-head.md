## Detached HEAD

`git checkout upstream/test` 로 원격 저장소의 특정 브랜치를 로컬로 가져오려는 시도를 했다. 하지만 아래와 같은 경고문구가 발생하며 특정 "커밋"으로 체크아웃되었다.

```
You are in 'detached HEAD' state. You can look around, make experimental
changes and commit them, and you can discard any commits you make in this
state without impacting any branches by switching back to a branch.
```

여기서 "detached HEAD" 란 용어가 낯설어서 찾아보았더니, HEAD가 떨어진 상태를 말한다고 한다. 그렇다면 HEAD란 무엇인가? HEAD란 현재 활성화된 브랜치의 가장 최신 커밋을 "간접적으로" 가리키고 있는 하나의 포인터이다. 여기서 "간접적으로" 라고 하는 이유는 "브랜치" 자체를 통해서 커밋을 보기 때문이다.

그렇다면 이 현상이 왜 발생하는지 알아야 하는데, 현재 로컬 브랜치에 없는 브랜치로 옮기려는 시도를 해서 "이름 없는 브랜치"의 최신 커밋을 보게 된 것이다. 즉, "이름 없는" 브랜치이기 때문에 "detached"라는 수식어가 붙게 된 것이다. 이를 해결하기 위해선 2가지 방법이 있다.

```bash
$ git checkout --track upstream/test
```

`--track` 옵션을 사용하면 로컬에 동일한 이름의 브랜치가 생성되면서 해당 원격 브랜치를 트래킹하게 된다. 또는 2.2.3버전 부터 나온 `git switch` 를 통해서 해결할 수 있다.

```bash
$ git switch upstream/test
fatal: a branch is expected, got remote branch 'upstream/test'
```

아예 에러가 발생하면서 "detached HEAD" 상태로 가지 않는다. `--detach` 옵션을 붙여서 강제로 갈 수 있고 아니면 `--track` 을 사용해서 생성할 수 있다.

```bash
$ git switch --create test --track upstream/test
```

## 참고

* [Why did my Git repo enter a detached HEAD state?](https://stackoverflow.com/questions/3965676/why-did-my-git-repo-enter-a-detached-head-state)

