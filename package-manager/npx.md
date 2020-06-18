## NPX

webpack, CRA 같이 패키지를 실행하기 위해서 npx를 많이 써왔는데, 어떤 도구인지 모르고 써와서 정리하고자 한다. npx는 패키지를 실행하는 도구로, *@npm 5.2.0* 부터 자동으로 같이 설치된다. 기존에 로컬 패키지를 실행할 때는 `package.json` 에 스크립트를 추가하는 방식이었다. 예를 들어, webpack을 실행해서 번들링을 한다고 하면, 로컬로 설치했을 경우 `node_modules` 폴더에 있을테니 아래와 같이 넣어줘야 한다.

```json
{
  "scripts": {
    "webpack": "./node_modules/.bin/webpack"
  }
}
```

이제 이걸 명령어로 실행하는 방식이다.

```bash
$ npm run webpack
$ yarn webpack
```

물론 실행은 되지만 뭔가 설정부터 시작해서 복잡하다... 이때, npx를 사용하는데, 아래와 같이 동작한다.

```bash
$ npx <command>
```

* `$PATH` 에 `<command>` 가 있을 경우 실행한다.
* 없을 경우 `./node_modules/.bin` 에서 찾아서 실행한다.
* 없을 경우 **최신버전을 설치해서** 실행하고 **삭제한다.**

이 명령어가 또 유용할 때는 아래와 같이 CRA를 통해 새로운 리액트 프로젝트를 만들 때다.

```bash
$ npx create-react-app new-react-app
```

패키지를 글로벌로 유지하지도 않고 관리하지도 않기 때문에 전역을 오염시키지 않는다는 점에서 참 편리하다.

## 참고

* [npx란 무엇인가?](https://geonlee.tistory.com/32)
* [npx - npm](https://www.npmjs.com/package/npx)

