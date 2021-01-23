# WIL(What I Learned)

기존에 체계없이 정리하던 TIL(Today I Learned)의 효과는 정리할 때 뿐이라는 것을 깨달았다. 따라서, 계속해서 모르는 것을 학습함에 있어서 매일(Today)에 집중하기 보다 어떤 것(What)을 배웠는지에 집중하기로 한다. 무엇을 배웠든 문서를 생성하고 그 문서를 주제별로 링크하는 방식을 사용한다.

### :book: 규칙

* 기술/프레임워크/라이브러리/프로그램 등의 이름으로 폴더와 주제를 만든다.
* 배운 것을 문서(.md)로 만든 뒤 해당하는 주제 하위에 링크한다.
* 만든 문서의 제목은 명확하게 "한글"로 적고 제목 그대로 링크한다.
* 문서의 제목 가장 처음에는 "[주제]"가 붙는다.
* 예시: "[GIT] 여러개의 커밋을 합치는 방법"이라고 지을 수 있다.

### :thinking: 효용성

배운 것을 정리하는 이유는 **"까먹기 때문"** 이다. 사람은 몸에 체득되지 않은 지식 및 기술을 반드시 까먹을 수밖에 없다. 따라서, 효용성을 최대화하기 위해서 이를 정리하며 다시 찾아볼 땐 `Ctrl + F` 로 검색해서 찾아보기로 한다.

<br>

### 정리되는 문서들

### Javascript

* [reject vs throw](./javascript/reject-throw.md)

### Typescript

* [모듈 처리 메커니즘](./typescript/module-resolution.md)
* [Tagged union](./typescript/tagged-union.md)
* [유틸리티 타입](./typescript/utility.md)
* [const assertion](./typescript/const-assertion.md)

### git

* [HEAD^ 와 HEAD~의 차이점](./git/head-caret-tilde.md)
* [여러개의 커밋들을 cherry-pick 하는 방법](./git/cherry-pick-range.md)
* [Detached HEAD](./git/detached-head.md)
* [여러가지 git diff](./git/diff.md)
* [refspec](./git/refspec.md)
* [.gitignore 인식 실패할 때](./git/gitignore-failure.md)
* [git status --porcelain](./git/status-porcelain.md)
* [git stash apply가 커밋과 충돌할 때 되돌리는 법](./git/stash-conflict-rollback.md)

### CSS

* [쌓임 맥락과 쌓임 순서 확실하게 이해하기](./css/stacking-context-property.md)

### HTML

* [tabindex](./html/tabindex.md)
* [readonly vs disabled](./html/readonly-disabled.md)
* [Element.closest()](./html/closest.md)
* [DOM 엘리먼트를 가져오는 방법들](./html/get-dom.md)

### Sass

* [@import vs @use의 차이점](./sass/import-use.md)
* [@content와 @at-root](./sass/content-at-root.md)

### Node Package Manager

* [yarn.lock의 역할](./package-manager/yarn-lock.md)
* [npx](./package-manager/npx.md)

### Cypress

* [명령어의 실행 순서](./cypress/execution-order.md)

### 보안

* [Bearer 토큰](./보안/bearer-token.md)
* [target="_blank"의 취약성](./보안/_blank-vulnerability.md)

### Shell

* [프로세스 치환](./shell/process-substitution.md)