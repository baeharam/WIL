# 자바 애플릿과 더블 버퍼링

자바에 대해 배울 때 교수님께서 애플릿 관련 예제를 상당히 많이 사용하셨다. 하지만 아직 객체지향이 뭔지도 모른 시절이라 애플릿에 대해 공부할 여유가 없었다. 하지만 OODP에 대해 공부하서도 애플릿 예제를 상당수 사용하셔서 공부해야 할 필요를 느꼈다. Java 9부터는 Deprecated 되었고 유사한 기술인 Java Web Start가 대신한다고 하는데 그럼 공부할 필요가 있는 것일까? 생각이 들지만 감히 내가 할 생각은 아닌 것 같아서 공부한다. 하지만 시간이 없기 때문에 조금만 짚고 넘어가자.



## 자바 애플릿

애플릿은 자바에서 지원하는 하나의 플러그인이다. 전용 위젯 엔진이나 더 큰 프로그램의 범위에서 실행되는 조그만 응용 프로그램이다. 자바의 애플릿은 이러한 애플릿이 바이트 코드의 형태로 배포되는 것으로 웹브라우저나 애플릿 뷰어에 의해서 실행된다. 또한 바이트 코드는 Cross-Platform(플랫폼 독립적)이기 때문에 다양한 OS에서 실행할 수 있다. 

웹브라우저에 의해서 실행될 수 있기 때문에 HTML과도 많은 연관이 있지만 사용했던 예제가 전부 애플릿 뷰어였기 때문에 그 부분에서만 다루도록 하자. 먼저 자바 애플릿에서 실행할 수 있는 기본적인 메소드들이다.

* [init()](https://docs.oracle.com/javase/9/docs/api/java/applet/Applet.html#init--) : 자바 애플릿을 실행할 때 초기화되어야 할 처리들을 해주는 메소드이다. 애플릿이 시스템에 load 되었다고 알려주는 역할도 한다.
* [start()](https://docs.oracle.com/javase/9/docs/api/java/applet/Applet.html#start--) : init() 메소드가 실행되고 바로 이어서 실행되는 메소드로 웹브라우저의 관점에서는 웹페이지를 방문할 때마다 호출된다고 한다. 또한 애플릿이 실행되어야 한다고 알려주는 역할을 한다.
* [stop()](https://docs.oracle.com/javase/9/docs/api/java/applet/Applet.html#stop--) : 애플릿이 멈춰야 할 때 알려주는 역할을 한다. 웹브라우저의 관점에선 페이지를 이동할 때 실행되므로 같은 애플릿에서 반복적으로 실행될 수 있다.
* [destroy()](https://docs.oracle.com/javase/9/docs/api/java/applet/Applet.html#destroy--) : 애플릿을 다시 실행하는 경우에 할당한 자원을 모두 되돌려주어야 하므로 stop() 메소드 다음에 실행된다.
* [paint(Graphics g)](https://docs.oracle.com/javase/9/docs/api/java/awt/Container.html#paint-java.awt.Graphics-) : start() 메소드가 호출되고 바로 다음에 실행되는 메소드이며 repaint() 메소드를 호출할 때도 실행된다. container에 어떠한 components를 그리는 기능을 한다.

이렇게 정리를 해보았는데 Container와 Component라는 용어가 나와서 살짝 짚고 넘어간다.

* [Container](https://docs.oracle.com/javase/9/docs/api/java/awt/Container.html) : 다른 Component를 수용할 수 있는 GUI Component (따라서, Container도 일종의 Component)
* Container의 종류
  * AWT Container : Panel / Frame / Applet / Dialog / Window
  * Swing Container : JPanel / JFrame / JApplet / JDialog / JWindow
* [Component](https://docs.oracle.com/javase/9/docs/api/java/awt/Component.html) : Container에 포함되어야 비로소 화면에 출력되는 GUI 객체
  * 텍스트 필드는 Container가 없으면 화면에 출력이 안되므로, Component
  * 모든 Swing components는 Container에 속한다.

일단 이 정도만 알면 나머지는 메소드만 이해하면 되니까 예제를 이해할 수 있을 듯 싶다. 이제 Double Buffering이 적용된 코드를 보면서 애플릿과 같이 이해를 해보도록 하자.



## 코드를 보자 (OODP 수업) - Double Buffering 미적용

코드의 양이 한번에 보기엔 너무 길기 때문에 쪼개서 보자.

```java
// ScrollingBanner.java
protected Thread bannerThread;
protected String text;
protected Font font = new java.awt.Font("Sans serif", Font.BOLD, 100);
protected int x, y;
protected int delay = 50;
protected int offset = 1;
protected Dimension d;
```

먼저 ScrollingBanner 클래스의 변수들이다. 텍스트를 애플릿으로 보여주기 위한 클래스이므로 그에 관련된 변수들을 갖는 것을 볼 수 있으며 Thread 객체인 bannerThread도 갖는 것을 알 수 있다.

```java
public void init() {
    // 원래 getparmeter()의 역할은 param이라고 되어있는 HTML 태그의 value 값을 가져온다.
  // "delay"로부터 parameter를 받아온다.
  String att = getParameter("delay");
    
  // att가 null이 아니면 int로 parsing한다.
  if (att != null) {
    delay = Integer.parseInt(att);
  }
    
  // "text"로부터 parameter를 받아온다.
  att = getParameter("text");
  if (att != null) {
    text = att;
  } else {
    text = "Scrolling banner.";
  }

  // set initial position of the text
  d = getSize();
  x = d.width;
  y = font.getSize();
}
```

애플릿을 실행할 때 초기화해야 하는 것들을 init() 메소드에 둔다. 여기서 delay는 Thread가 sleep() 메소드에 이용하는 것이고, text는 보나마나 애플릿으로 보여줄 문자열이다. d는 Dimension 객체로 [getSize()](https://docs.oracle.com/javase/9/docs/api/java/awt/Component.html#getSize--) 메소드를 통해서 현재 Component의 너비와 높이를 Dimension 객체 형태로 받아온다. 그렇게 받아온 Dimension 객체로 너비를 x에 저장하고 y는 Font 객체인 font를 통해 [getSize()](https://docs.oracle.com/javase/9/docs/api/java/awt/Font.html#getSize--) 메소드를 호출해서 폰트의 크기를 받아온다.

```java
public void paint(Graphics g) {
  // get the font metrics to determine the length of the text
  g.setFont(font);
  FontMetrics fm = g.getFontMetrics();
  int length = fm.stringWidth(text);

  // adjust the position of text from the previous frame
  x -= offset;

  // if the text is completely off to the left end
  // move the position back to the right end
  if (x < -length)
    x = d.width;

  // set the pen color and draw the background
  g.setColor(Color.black);
  g.fillRect(0,0,d.width,d.height);

  // set the pen color, then draw the text
  g.setColor(Color.green);
  g.drawString(text, x, y);
}
```

이제 애플릿에서 텍스트를 보여주는데 핵심 역할을 하는 paint() 메소드이다. Graphics 객체를 받아서 어떤 작업들을 진행하는지 살펴보자.

* 먼저 [setFont(font)](https://docs.oracle.com/javase/9/docs/api/java/awt/Graphics.html#setFont-java.awt.Font-) 메소드로 폰트의 종류를 셋팅한다.
* 그 다음 FontMetrics 객체를 받아온 뒤, [stringWidth(text)](https://docs.oracle.com/javase/9/docs/api/java/awt/FontMetrics.html#stringWidth-java.lang.String-) 메소드로 폰트에 대한 텍스트의 너비를 length에 저장한다.
* 제일 위에서 설정한 offset 변수로 원래 Component의 너비를 저장했던 x에서 뺀다. (위치 조정)
* 만약 x가 텍스트의 너비에 음수를 곱한 값보다 작다면, 즉 텍스트가 완전히 왼쪽으로 빠져서 Component에서 빠져나왔다면 x를 원래 Component의 너비로 초기화한다. (텍스트가 다시 나오는 기능)
* 색깔을 정하고 [fillRect(0, 0, d.width, d.height)](https://docs.oracle.com/javase/9/docs/api/java/awt/Graphics.html#fillRect-int-int-int-int-) 메소드를 통해서 정한 색깔로 각 좌표로 연결된 사각형을 채운다.
* 색깔을 정하고 [drawString(text, x, y)](https://docs.oracle.com/javase/9/docs/api/java/awt/Graphics.html#drawString-java.lang.String-int-int-) 메소드로 텍스트를 정한 색깔로 (x,y) 좌표부터 그린다. 여기서 알아챌 수 있는데 x가 처음에 Component의 너비였으니 아예 안보이고 offset인 1을 하나씩 빼면서 왼쪽으로 텍스트가 나오는 것이다. 

```java
// 애플릿의 시작, Thread 객체 생성해서 시작.
public void start() {
  bannerThread = new Thread(this);
  bannerThread.start();
}
public void stop() {
  bannerThread = null;
}

  // Thread에서 돌아가는 로직
public void run() {
	  // 현재 실행하고 있는 Thread 객체가 bannerThread면 delay만큼 중지한다.
  while (Thread.currentThread() == bannerThread) {
    try {
      Thread.currentThread().sleep(delay);
    }
    catch (InterruptedException e) {}
    // 강제로 paint() method를 한번 더 호출하고 싶을 때 사용 (방금 처리한 작업을 화면에 바로 보여주고 싶을 때)
    repaint();
  }
}
```

이제 Thread가 실행되는 부분이다. init() 메소드가 실행되면 바로 이어서 start() 메소드가 실행되고 그 안에선 Thread 객체를 생성하여 bannerThread에 할당한 후 Thread의 start() 메소드를 통해서 시작한다. start() 메소드는 run() 메소드를 호출하기 때문에 내부로 들어가서 while문을 실행한다.

while문 안에선 만약 현재 실행되고 있는 Thread 객체가 bannerThread라면 delay만큼 지연시키고 repaint() 메소드를 호출해서 paint() 메소드를 강제로 다시 호출한다. 

즉, 이 프로그램은 애플릿 뷰어가 종료될 때인 stop() 메소드가 호출될 때 bannerThread를 null로 만들기 때문에 while문의 조건은 Thread가 현재 실행되고 있다는 것을 말하고 계속 paint() 메소드를 호출함으로서 애플릿 뷰어에 텍스트가 계속 나타나도록 하는 것이다. 어느 정도의 로직은 이해됬지만 아직 찜찜한 부분이 paint(), repaint(), update() 메소드의 차이점이었다. 이것도 짚고 넘어가자

* [paint(Graphics g)](https://docs.oracle.com/javase/9/docs/api/java/awt/Component.html#paint-java.awt.Graphics-) : 인자로 전달받은 Component 객체에 무언가를 그린다.
* [update(Graphics g)](https://docs.oracle.com/javase/9/docs/api/java/awt/Component.html#update-java.awt.Graphics-) : 주어진 Component의 background를 칠하고 paint(Graphics g) 메소드를 호출한다. 이 때 계속해서 배경을 칠할 수 있기 때문에 **flicker(깜박거림)**이 발생한다. 이걸 없애기 위해 overriding 한다.
* [repaint()](https://docs.oracle.com/javase/9/docs/api/java/awt/Component.html#repaint--) : 사용자에 의해 호출될 수 있으며 GUI Thread에 어떠한 flag를 설정한다. GUI Thread는 주기적으로 flag를 체크하며 만약 flag가 설정되어있으면 update() 메소드를 호출하고 flag를 리셋한다.

> 출처 : [굉장히 친절한 설명](http://justobjects.org/cowcatcher/browse/stdjava/slides/java-awt/awt/graph/slide.0.4.html), [이것도](https://www.guiguan.net/repaint-paint-and-update/)

따라서 순서는 repaint() -> update() -> paint()로 이어지는 것을 알 수 있다. 위의 코드에선 run() 메소드에서 repaint() 메소드가 호출됨에 따라 update() 메소드가 호출되고 overriding 되어있지 않으므로 background를 칠하고 paint() 메소드를 호출한다. Thread가 지연시간인 50 밀리세컨드 (0.05초) 마다 repaint() 메소드를 호출하기 때문에 깜박거리게 된다. 이제 Double Buffering의 위대함을 보도록 하자.



## 코드를 보자 (OODP 수업) - Double Buffering 적용

```java
public class ScrollingBannerDB extends ScrollingBanner {

  protected Image image;          // The off-screen image (graphic components)
  protected Graphics offscreen;   // The off-screen graphics (graphic context)

  public void update(Graphics g) {
    // create the offscreen image if it is the first time
    if (image == null) {
      image = createImage(d.width, d.height);
      offscreen = image.getGraphics();
    }

    // draw the current frame into the off-screen image
    // using the paint method of the superclass
    // RAM의 렌더링한 결과를 video RAM으로 복사
    super.paint(offscreen);

    // copy the off-screen image to the screen
    // video RAM의 결과를 모니터로 출력
    g.drawImage(image, 0, 0, this);
  }

  // update(Graphics g)는 화면을 지우고 paint(Graphics g)를 호출하는데
  // 화면을 지우지 않고 paint를 호출하도록 오버라이딩 한다.
  public void paint(Graphics g) {
    update(g);
  }
}
```

처음엔 Double buffering이 하나의 라이브러리에 있는 클래스나 메소드일 줄 알았는데 단순히 하나의 버퍼를 구현하는 기법이었다. 위에서 offscreen이라는 변수를 주목하자. 이름을 보면 screen에서 off인, 즉 화면에 보이지 않는 부분을 버퍼로 사용하는 개념이다. 

update(Graphics g) 함수를 Overriding 함으로 flicker를 제거하고 있다. 차근차근 로직을 살펴보자.

* Image 객체인 image에 Component가 반들어지지 않았으면 만들고 offscreen에 할당한다.
* 그 offscreen component에 슈퍼 클래스인 ScrollingBanner 클래스로 그린다. 즉, 버퍼에 그린다.
* 버퍼에 그렸던 이미지를 image로 복사한다. 

기존 ScrollingBanner 클래스와는 다르게 repaint() 메소드가 update() 메소드를 호출하면 overriding 된 메소드가 호출되고 먼저 offscreen component에 그려서 화면에 보여지는 Image 객체인 image에 복사한다. 복사하는 기능은 [drawImage(image, 0, 0, this)](https://docs.oracle.com/javase/9/docs/api/java/awt/Graphics.html#drawImage-java.awt.Image-int-int-java.awt.image.ImageObserver-) 가 수행하며 이로써 **깜박거림이 사라진다.** 이후에 모든 로직이 끝나면 update() 메소드는 다시 paint() 메소드를 호출하는데 paint() 메소드 역시 update 메소드를 호출하기 때문에 자기 자신을 호출하는 형태가 되서, update() 메소드의 반복을 통해 버퍼를 이용해서 그림을 계속 그리는 형태가 된다.

이렇게 애플릿과 더블 버퍼링에 대해 간략하게 알아봤는데 쓰레드 까지 같이 쓰여서 좀 복잡했던 것 같다. 그래도 어느정도 이해한 것 같아 다행이다.