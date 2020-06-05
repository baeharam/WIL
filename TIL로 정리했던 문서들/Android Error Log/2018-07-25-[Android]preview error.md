---
layout: post
category: android
title: "[Android]안드로이드 스튜디오 미리보기 에러"
---

안드로이드 스튜디오에서 레이아웃 작업을 할 때 XML로 직접 하던지 아니면 마우스로 하던지 할 텐데 처음 프로젝트를 생성하면 미리보기가 안될 때가 있다. 계속 까먹어서 이참에 그냥 정리해보기로 하고 쓴다. `res\values\styles.xml` 파일에 들어가보면 아래 코드가 나온다.

![image](https://user-images.githubusercontent.com/35518072/43201540-c363b6cc-9053-11e8-839d-3db51318a950.png)

이 코드에서 원래 빨간 밑줄 부분이 `Base`가 없이 그냥 `Theme.AppCompat.Light.DarkActionBar`라고만 쓰여져 있다. 여기서 `Base`를 추가해주면 깔끔하게 해결된다. 아직 뭐가 원인인지는 못 찾았으니 원인을 찾으면 추가하자.