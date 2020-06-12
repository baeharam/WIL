## 여러개의 커밋들을 cherry-pick 하는 방법

`cherry-pick` 을 사용하는 이유는 보통, `rebase` 하기가 귀찮고 커밋을 하나만 가져오고 싶을 때 많이 사용하는데, 커밋관리가 복잡해지면서 "범위"를 지정해서 가져오는 경우가 생겼다. 이때는 몰라서 하나씩 가져왔는데 찾아보니 1.7.2+ 버전부터 범위를 지정하는 기능이 추가되었다고 한다.

```bash
$ cherry-pick A^..B
```

이렇게 할 경우, A커밋부터 B커밋까지 가져오는 것이다. **주의할 점은 A가 B보다 이전 커밋이어야 한다는 것이다.** 만약에 반대로 하면 어떻게 될까?

```
error: empty commit set passed
fatal: cherry-pick failed
```

위와 같은 에러문구가 나오면서 실패하게 된다. 또한 당연히 충돌이 발생할 경우도 있는데, 동일한 파일을 생성 및 수정했을 경우 발생하게 된다. 이렇게 되면 

```
The previous cherry-pick is now empty, possibly due to conflict resolution.
```

위와 같은 문구가 나면서 `--continue` 를 할 건지 아니면 `--skip` 을 할 건지 물어본다. 하지만 **어디서 충돌이 발생한지는 알려주지 않는다.** 이런 점에선 `rebase -i` 가 훨씬 좋다고 생각한다.

여기서 궁금한 점은 일반적인 `cherry-pick` 의 경우는 충돌이 발생했을 때 알려주는데 왜 범위를 기준으로 하면 안 알려주는가였다. [1.7.2 릴리즈 노트](https://raw.githubusercontent.com/git/git/master/Documentation/RelNotes/1.7.2.txt)를 보면 다음과 같이 나와있다.

> *"git cherry-pick" learned to pick a range of commits (e.g. "cherry-pick A..B" and "cherry-pick --stdin"), so did "git revert"; these **do not support the nicer sequencing control** "rebase [-i]" has, though.*

강조했다시피 **"연달아 있는 커밋들에 대한 제어"** 를 제공하지 않기 때문에 충돌여부를 표시하지 않는 듯 싶다.

## 참고

* [How to cherry pick a range of commits and merge into another branch?](https://stackoverflow.com/questions/1994463/how-to-cherry-pick-a-range-of-commits-and-merge-into-another-branch)
* [How to cherry-pick multiple commits](https://stackoverflow.com/questions/1670970/how-to-cherry-pick-multiple-commits)