## 여러가지 git diff

보통 `git diff` 를 사용하는 때는 내가 어떤 파일을 어떻게 작업했는지 보기 위해서인데, 기본 옵션으로는 index에 staging된 파일에 대한 것은 볼 수 없다. 따라서, index와 working directory를 비롯해 어떻게 쓸 수 있는지 정리한다.

* `git diff` : working directory와 index의 파일을 비교
* `git diff --cached` : 현재 HEAD와 index의 파일을 비교
* `git diff HEAD` : 현재 HEAD와 working directory를 비교

`cache` 의 의미가 `index` 와 동일하게 쓰인다고 한다.

<br>

## 참고

* [How do I show the changes which have been staged?](https://stackoverflow.com/questions/1587846/how-do-i-show-the-changes-which-have-been-staged)