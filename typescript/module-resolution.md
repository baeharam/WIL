## 모듈 처리 메커니즘

타입스크립트에서 모듈을 어떻게 처리하는지에 대해 알아야 특정 모듈의 타입 정의 파일을 작성하거나 아니면 타입 정의를 찾지 못한다는 에러가 발생할 때에 그 문제를 해결할 수 있다. 따라서, 공식 문서를 보고 정리해본다.

## classic과 node

모듈 처리 메커니즘은 2가지 방식으로 나뉘는데 CommonJS를 기본적인 모듈 시스템으로 사용하는 node방식과 AMD/ESM/System을 모듈 시스템으로 사용하는 classic 방식으로 나뉜다.

### classic

모듈을 불러올 때는 상대경로 혹은 절대경로로 가져올 수 있는데 그 방식에 따라서 나뉜다. 현재경로를 `/root/src/folder/A.ts` 라고 가정한다.

* 상대경로

```typescript
import { b } from "./moduleB"
```

1.  `/root/src/folder/moduleB.ts`
2.  `/root/src/folder/moduleB.d.ts`

* 절대경로

```typescript
import { b } from "moduleB"
```

1. `/root/src/folder/moduleB.ts`
2. `/root/src/folder/moduleB.d.ts`
3. `/root/src/moduleB.ts`
4. `/root/src/moduleB.d.ts`
5. `/root/moduleB.ts`
6. `/root/moduleB.d.ts`
7. `/moduleB.ts`
8. `/moduleB.d.ts`

### node

Node.js가 모듈을 처리하는 메커니즘을 따라가며, 현재경로를 동일하게 `/root/src/moduleA.ts` 라고 하자.

* 상대경로

```typescript
import { b } from "./moduleB"
```

1. `/root/src/moduleB.ts`
2. `/root/src/moduleB.tsx`
3. `/root/src/moduleB.d.ts`
4. `/root/src/moduleB/package.json` 에서 `types` 속성
5. `/root/src/moduleB/index.ts` 
6. `/root/src/moduleB/index.tsx`
7. `/root/src/moduleB/index.d.ts`

* 절대경로

```typescript
import { b } from "moduleB"
```

1. `/root/src/node_modules/moduleB.ts`
2. `/root/src/node_modules/moduleB.tsx`
3. `/root/src/node_modules/moduleB.d.ts`
4. `/root/src/node_modules/moduleB/package.json` 에서 `types` 속성
5. `/root/src/node_modules/@types/moduleB.d.ts`
6. `/root/src/node_modules/moduleB/index.ts`
7. `/root/src/node_modules/moduleB/index.tsx`
8. `/root/src/node_modules/moduleB/index.d.ts`
9. `/root/node_modules/moduleB.ts`
10. `/root/node_modules/moduleB.tsx`
11. `/root/node_modules/moduleB.d.ts`
12. `/root/node_modules/moduleB/package.json` 에서 `types` 속성
13. `/root/node_modules/@types/moduleB.d.ts`
14. `/root/node_modules/moduleB/index.ts`
15. `/root/node_modules/moduleB/index.tsx`
16. `/root/node_modules/moduleB/index.d.ts`
17. `/node_modules/moduleB.ts`
18. `/node_modules/moduleB.tsx`
19. `/node_modules/moduleB.d.ts`
20. `/node_modules/moduleB/package.json` 에서 `types` 속성
21. `/node_modules/@types/moduleB.d.ts`
22. `/node_modules/moduleB/index.ts`
23. `/node_modules/moduleB/index.tsx`
24. `/node_modules/moduleB/index.d.ts`



## 실험해보기

Typescript 컴파일러인 tsc를 사용해서 실제로 모듈을 어떻게 가져오는지 볼 수 있다. 실행하고자 하는 파일이 `moduleResolution.ts` 라면 다음과 같이 실험해보자.

```bash
$ tsc moduleResolution.ts --moduleResolution
```

아래는 `lodash` 에서 `debounce` 함수를 불러온 결과이다.

```typescript
import { debounce } from 'lodash';
```

```
======== Resolving module 'lodash' from '/Users/haram/haram/ts-practice/test.ts'. ========
Module resolution kind is not specified, using 'NodeJs'.
Loading module 'lodash' from 'node_modules' folder, target file type 'TypeScript'.
Found 'package.json' at '/Users/haram/haram/ts-practice/node_modules/lodash/package.json'.
'package.json' does not have a 'typesVersions' field.
File '/Users/haram/haram/ts-practice/node_modules/lodash.ts' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash.tsx' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash.d.ts' does not exist.
'package.json' does not have a 'typings' field.
'package.json' does not have a 'types' field.
'package.json' has 'main' field 'lodash.js' that references '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js'.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js' exist - use it as a name resolution result.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js' has an unsupported extension, so skipping it.
Loading module as file / folder, candidate module location '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js', target file type 'TypeScript'.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js.ts' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js.tsx' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js.d.ts' does not exist.
File name '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js' has a '.js' extension - stripping it.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.ts' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.tsx' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.d.ts' does not exist.
Directory '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js' does not exist, skipping all lookups in it.
File '/Users/haram/haram/ts-practice/node_modules/lodash/index.ts' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash/index.tsx' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash/index.d.ts' does not exist.
Directory '/Users/haram/haram/ts-practice/node_modules/@types' does not exist, skipping all lookups in it.
Directory '/Users/haram/haram/node_modules' does not exist, skipping all lookups in it.
Directory '/Users/haram/node_modules' does not exist, skipping all lookups in it.
Directory '/Users/node_modules' does not exist, skipping all lookups in it.
Directory '/node_modules' does not exist, skipping all lookups in it.
Loading module 'lodash' from 'node_modules' folder, target file type 'JavaScript'.
Found 'package.json' at '/Users/haram/haram/ts-practice/node_modules/lodash/package.json'.
'package.json' does not have a 'typesVersions' field.
File '/Users/haram/haram/ts-practice/node_modules/lodash.js' does not exist.
File '/Users/haram/haram/ts-practice/node_modules/lodash.jsx' does not exist.
'package.json' has 'main' field 'lodash.js' that references '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js'.
File '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js' exist - use it as a name resolution result.
Resolving real path for '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js', result '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js'.
======== Module name 'lodash' was successfully resolved to '/Users/haram/haram/ts-practice/node_modules/lodash/lodash.js' with Package ID 'lodash/lodash.js@4.17.15'. ========
```



### 다양한 옵션들

이런 기본적인 메커니즘 외에도 `tsconfig.json` 파일의 다양한 컴파일 옵션들을 통해서 단계가 추가되거나 바뀔 수 있다. `paths` , `types` , `typeRoots` 등 옵션들이 많은데, 때에 따라 적용해서 사용하면 쓸모있을 것이다.



### 참고

* [타입스크립트 컴파일러가 모듈 타입 선언을 참조하는 과정](https://medium.com/naver-fe-platform/타입스크립트-컴파일러가-모듈-타입-선언을-참조하는-과정-5bfc55a88bb6)
* [Module Resolution](https://www.typescriptlang.org/docs/handbook/module-resolution.html#module-resolution-strategies)