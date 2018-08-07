---
layout: post
category: Android
title: "[안드로이드]OkHttp와 Gson"
---

뭘 배우든지 항상 하는 http를 이용한 json 데이터 가져오는 연습을 해보자. 제목처럼 2개의 라이브러리를 활용할 수 있으며 굉장히 쉽게 조작할 수 있다.

# OkHttp

http 역시 네트워크를 통해 데이터를 가져오는 작업이므로 UI 스레드가 아닌 다른 작업 스레드를 생성하는 것이 좋다. 저번에 AsyncTask 클래스를 이용했으니 그대로 사용해보도록 하자. json 데이터는 [다음 사이트](https://jsonplaceholder.typicode.com/posts) 에 굉장히 잘 나와있다.

json 데이터가 가진 값이 userId, id, title, body이므로 해당되는 데이터 모델 클래스를 만들어서 가져온 데이터를 집어넣을 것이다. `User`라는 클래스를 만들고 해당하는 값을 String으로 만든 뒤에 생성자, getter, setter를 추가해주자. 이건 뭐 뻔하니 코드로 할 필욘 없을 것 같다.

이제 메인 액티비티에서 백그라운드 작업을 하기 위해서 AsyncTask 클래스를 상속하는 클래스를 만들어 주자.

```java
private static class HttpAsyncTask extends AsyncTask<String, Void, Void> {
    @Override
    protected String doInBackground(String... strings) {
        // json 데이터 가져오기
    }
}
```

이제 OkHttp 라이브러리를 이용해서 데이터를 가져와야 한다. 작업 스레드를 생성할 때 json 데이터가 있는 주소를 넘길 것이므로 그걸 받아서 요청을 하면 된다.

```java
private static class HttpAsyncTask extends AsyncTask<String, Void, Void> {
    
    // OkHttpClient 객체 생성
    private OkHttpClient client;
    
    @Override
    protected String doInBackground(String... strings) {
        List<User> userList = new ArrayList<>();
    	String url = strings[0];
        
        try{
        	// 요청 객체 생성
            Request request = new Request.Builder()
                .url(url)
                .build();

            // 응답 객체 생성 한 뒤에 요청한 결과 받음
            Response response = client.newCall(request).execute();    
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
```

try~catch로 묶은 이유는 `execute()`에서 IOException이 발생할 수도 있기 때문이다. 이제 받은 데이터를 가공해서 정의한 데이터 모델에 집어넣자.

```java
private static class HttpAsyncTask extends AsyncTask<String, Void, Void> {
    
    // OkHttpClient 객체 생성
    private OkHttpClient client;
    
    @Override
    protected String doInBackground(String... strings) {
        List<User> userList = new ArrayList<>();
    	String url = strings[0];
        
        try{
            // 요청 객체 생성
            Request request = new Request.Builder()
                .url(url)
                .build();

            // 응답 객체 생성 한 뒤에 요청한 결과 받음
            Response response = client.newCall(request).execute();

            // JSONArray 객체 생성한 뒤 가져온 데이터로 초기화
            JSONArray jsonArray = new JSONArray(response.body().string());

            // 가공해서 리스트에 집어넣기
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String userId = jsonObject.getString("userId");
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                String body = jsonObject.getString("body");

                User user = new User(userId, id, title, body);
                userList.add(user);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
```

이렇게 하면 데이터가 깔끔하게 가공되어 객체가 생성되고 리스트에 들어가게 된다. catch가 하나 더 추가된 이유는 JSONArray나 JSONObject를 다룰 때 JSONException이 발생할 수도 있기 때문이다.

이것도 비교적 간단하긴 하지만 더 쉽게 리스트 타입을 지정해서, 타입만 넘겨주면 라이브러리가 알아서 포맷을 잡아주는 기능도 있다. 한번 해보자.

<br>

# Gson

Gson 객체를 생성한 뒤에 타입을 정의해서 인자로 넘겨주기만 하면 된다.

```java
// Request, Response 모두 한 상태
Gson gson = new Gson();
Type listType = new TypeToken<ArrayList<User>>(){}.getType();
userList = gson.fromJson(response.body().string, listType);
```

3줄로 OkHttp에서 직접 포맷을 잡아준 것과 동일한 효과를 볼 수 있다.