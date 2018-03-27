## 자바의 기초와 스트림에 대한 이해

### Default value

자바의 primitive type과 reference type은 모두 기본값을 가진다.

boolean : false  
byte : (byte) 0  
short : (short) 0  
int : 0  
long : 0L  
char : \u0000  
float : 0.0f  
double : 0.0d  
object reference : null



## Java stream classes

자바에는 굉장히 많은 스트림 클래스가 존재한다. 먼저 스트림이란 데이터의 흐름에 대한 경로이다. 즉, 데이터가 지나다니는 길이다. 기본적으로 데이터를 읽거나 쓰는 2가지 스트림이 존재한다. 자바에서는 이런 스트림들을 크게 Byte stream과 Character stream으로 구분하는데, 바이트 단위로 읽거나 쓰는 스트림과 문자 단위로 읽거나 쓰는 스트림을 가리킨다. 너무 많기 때문에 그림으로 조직도를 보고 어떻게 구성되어있는지 살펴보자.

![default](https://user-images.githubusercontent.com/35518072/37955934-f19a2d5a-31e4-11e8-998b-b29b4bc0df1e.png)

다음은 <u>입력 문자 스트림</u>이다. 모두 <u>Reader</u>라는 이름이 붙어있는 것을 알 수 있다.

| 클래스            | 설명                                            | Stream     |
| ----------------- | ----------------------------------------------- | ---------- |
| Reader            | 바이트 입력 스트림을 위한 추상 클래스           | 2차 Stream |
| BufferedReader    | 문자 버퍼 입력, 라인 해석                       | 2차 Stream |
| LineNumberReader  | 문자 입력시 라인 번호를 유지                    | 2차 Stream |
| CharArrayReader   | 문자 배열에서 읽어드림                          | 1차 Stream |
| InputStreamReader | 바이트 스트림을 문자 스트림으로 변환            | 2차 Stream |
| FileReader        | 파일에서 바이트를 읽어들여 문자 스트림으로 변환 | 1차 Stream |
| FilterReader      | 필터 적용 문자 입력을 위한 추상 클래스          | 2차 Stream |
| PushbackReader    | 읽어들인 문자를 되돌림                          | 2차 Stream |
| PipedReader       | PipedWriter에서 읽어들임                        | 1차 Stream |
| StringReader      | 문자열에서 읽어들임                             | 1차 Stream |

![default](https://user-images.githubusercontent.com/35518072/37955935-f1c2fb4a-31e4-11e8-8d0b-cd05c5eacf3b.png)

다음은 <u>출력 문자 스트림</u>이다. 모두 <u>Writer</u>라는 이름이 붙어있는 것을 알 수 있다.

| 클래스             | 설명                                   | Stream     |
| ------------------ | -------------------------------------- | ---------- |
| Writer             | 문자 출력 스트림을 위한 추상 클래스    | 2차 Stream |
| BufferedWriter     | 문자 스트림에 버퍼 출력, 줄바꿈 사용   | 2차 Stream |
| CharArrayWriter    | 문자 스트림에 문자 배열 출력           | 1차 Stream |
| OutputStreamWriter | 문자 스트림을 바이트 스트림으로 변환   | 2차 Stream |
| FileWriter         | 문자 스트림을 바이트 파일로 변환       | 1차 Stream |
| FilterWriter       | 필터 적용 문자 출력을 위한 추상 클래스 | 2차 Stream |
| PipedWriter        | PipedReader에 출력                     | 1차 Stream |
| StringWriter       | 문자열 출력                            | 1차 Stream |
| PrintWriter        | Writer 값과 객체를 출력                | 2차 Stream |

![default](https://user-images.githubusercontent.com/35518072/37955937-f1ea451a-31e4-11e8-9f4c-011d77e5efcd.png)

다음은 <u>입력 바이트 스트림</u>이다. 모두 <u>InputStream</u>이 붙어있는 것을 알 수 있다.

| 클래스                  | 설명                                              | Stream     |
| ----------------------- | ------------------------------------------------- | ---------- |
| InputStream             | 바이트 입력 스트림을 위한 추상 클래스             | 2차 Stream |
| FileInputStream         | 파일에서 바이트를 읽어들여 바이트 스트림으로 변환 | 1차 Stream |
| PipedInputStream        | PipedOutputStream에서 읽어들임                    | 1차 Stream |
| FilterInputStream       | 필터 적용 바이트 입력을 위한 추상 클래스          | 2차 Stream |
| LineNumberInputStream   | 바이트 입력시 라인 번호를 유지 (비추천)           | 2차 Stream |
| DataInputStream         | 기본 자료형 데이터를 바이트로 입력                | 2차 Stream |
| BufferedInputStream     | 바이트 버퍼 입력                                  | 2차 Stream |
| PushbackInputStream     | 읽어들인 바이트를 되돌림(pushback)                | 2차 Stream |
| ByteArrayInputStream    | 바이트 배열에서 읽어들임                          | 1차 Stream |
| SequenceInputStream     | 서로 다른 InputStream을 입력받은 순서대로 이어줌  | 2차 Stream |
| StringBufferInputStream | 문자열에서 읽어들임 (비추천)                      | 1차 Stream |
| ObjectInputStream       | 객체로 직렬화된 데이터를 역직렬화 하여 읽음       | 2차 Stream |

![default](https://user-images.githubusercontent.com/35518072/37955938-f21217a2-31e4-11e8-89e9-8cc90b915f9d.png)

다음은 <u>출력 바이트 스트림</u>이다. 1개를 제외한 모두가 <u>OutputStream</u>이름을 가진다.

| 클래스                | 설명                                     | Stream     |
| --------------------- | ---------------------------------------- | ---------- |
| OutputStream          | 바이트 출력 스트림을 위한 추상 클래스    | 2차 Stream |
| FileOutputStream      | 바이트 스트림을 바이트 파일로 변환       | 1차 Stream |
| PipedOutputStream     | PipedInputStream에 출력                  | 1차 Stream |
| FilterOutputStream    | 필터 적용 바이트 출력을 위한 추상 클래스 | 2차 Stream |
| DataOutputStream      | 바이트를 기본자료형으로 출력             | 2차 Stream |
| BufferedOutputStream  | 바이트 스트림에 버퍼 출력                | 2차 Stream |
| PrintStream           | Stream 값과 객체를 출력                  | 2차 Stream |
| ByteArrayOutputStream | 바이트 스트림에 바이트 배열 출력         | 1차 Stream |
| ObjectOutputStream    | 데이터를 객체로 직렬화하여 출력          | 2차 Stream |

1차 스트림과 2차 스트림이 자주 나오는데 무엇인지 찾아봤더니 1차 스트림은 목표지점 데이터에 직접 연결되는 스트림을 말하고 2차 스트림은 1차 스트림으로 구현할 수 없는 어떤 기능들을 사용하고 싶을 때 2차 스트림으로 1차 스트림을 <u>가공하여</u> 사용하면 그 기능을 구현할 수 있다고 한다.

이렇게 많은 스트림 클래스에 대해 알아보았는데 사용하는 경우가 각 용도에 따라 다르기 때문에 외울 필요는 전혀 없고 오라클의 자바 레퍼런스를 참고하면 빠르게 찾을 수 있으니 그걸 이용하도록 하자. 한가지 짚고 넘어갈 것은 보통 자바를 배울 때 입력으로 Scanner 클래스를 사용하는데 대표적인 문자 입력 스트림으로 사용되는 BufferedReader와 어떤 점이 다른지 보도록 하자. Stackoverflow의 답변을 참고했다.

### [Scanner vs BufferedReader](https://stackoverflow.com/a/21698084/9437175)

* *BufferedReader*는 *Scanner*에 비해 상당히 큰 버퍼 메모리를 가지고 있다. 만약 아주 긴 문자열을 읽어들일려고 한다면 *BufferedReader*를 사용하고 그게 아니라 스트림으로부터 token의 specific type을 parsing 하고 싶다면 *Scanner*를 사용하라.
* *Scanner*는 자기만의 구분자를 사용하여 tokenize 할 수 있으며 스트림을 primitive type으로 parsing 할 수 있지만 *BufferedReader*는 문자열을 읽어서 저장하는 것 뿐이다.
* *BufferedReader*는 synchronous하고 *Scanner*는 그렇지 않기 때문에 멀티 쓰레드 환경이라면 *BufferedReader*를 사용하는 것이 안전하다.
* *Scanner*는 IOException을 숨기지만 *BufferedReader*는 즉각 throw한다.

요약하자면 *Scanner*는 파싱을 할 수 있고 각 primitive type에 대한 여러가지 출력함수가 존재하지만 멀티 쓰레드에선 안전하지 않다는 것이고, *BufferedReader*는 단지 읽기 밖에 안하지만 버퍼 메모리의 크기가 매우 크고 멀티 스레드에서 안전하다는 것이다.  또 하나의 글을 소개하고자 하는데 Stream과 Buffer의 개념을 아주 잘 설명한 글이 있어서 정리하고자 한다.

### [Stream and Buffer](https://stackoverflow.com/a/15984820/9437175)

자바는 I/O를 위한 2가지 종류의 클래스가 존재한다. (Streams와 Readers/Writers)

Streams(*InputStream, OutputStream*과 이걸 확장한 모든 스트림 클래스)는 파일이나 네트워크 등 어떤 것으로부터 binary data를 읽고 쓰기 위한 것이다.

Readers와 Writers는 문자를 읽고 쓰기 위함이며 Streams 계층의 가장 위에 존재한다. 또한 이것들은 character encoding을 사용해서 binary data를 characters로 변환하거나 그 반대의 변환을 한다.

디스크로부터 1byte씩 데이터를 읽어오는 것은 굉장히 비효율적이다. 이것을 빠르게 하는 방법이 Buffer인데 1byte씩 읽는 대신, 어느 정도 큰 bytes를 읽어서 Buffer라는 메모리 공간에 저장하고 Buffer에 있는 데이터를 1byte씩 보는 것으로 대신하는 것이다. 다음 코드를 보자.

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
```

*System.in*은 *InputStream* 클래스이다. *System.in*으로부터 bytes를 읽어오는 *InputStreamReader*를 만들 수 있으며 그걸 *BufferedReader*로 감싸는 것이다. 그래서 결국은 *BufferedReader*가 *System.in*으로 부터 읽어오는 *InputStreamReader*를 읽어오는 것이 된다.

아직 스트림의 전부를 이해한 것은 아니지만 자바에 크게 2종류의 스트림 클래스가 있다는 것과 어떨 때 어떤 스트림을 쓰는 건지 정도는 알게 된 것 같다. 여기에 대해 더 공부하면 추가하도록 하자.