## 중재자 패턴

중재자 패턴이란 여러개의 객체들이 상호 연결되어있는 관계라면 Modify/Remove/Add 할 때 그에 관련된 모든 객체들도 fix 해야 한다. 하지만 상호 연결된 객체들 사이의 중재자 객체가 있다면 다른 객체들이 이 중재자 객체를 통해 연결될 수 있고 유지보수가 간결해진다. 즉, <u>객체간의 결합도를 낮춰주고 M:N의 관계를 M:1의 관계로 바꿔주는 패턴인 것이다.</u>

![mediator](https://user-images.githubusercontent.com/35518072/38123212-eb2148fa-3414-11e8-9bb7-2ccdf361bbd4.png)

> [사진출처](https://springframework.guru/gang-of-four-design-patterns/mediator-pattern/)

다음 그림처럼 여러개의 Object가 복합적으로 연결된 것에 비해 중간에 Mediator Object를 하나 둠으로써 Maintainability가 높아지는 개념인 것.

### JFrame을 활용한 중재자 패턴

![dsfsf](https://user-images.githubusercontent.com/35518072/38123343-9139d6f8-3415-11e8-92fa-a7a4f8d01eb2.jpg)

다음 UML을 보면 main 함수가 있는 MediatorDemo와 Mediator가 연결되었고 Mediator 객체는 다시 JFrame의 ContentPane 객체에 올라가는 JLabel과 JButton의 서브객체들과 연결되어 있다. 즉 MediatorDemo가 실행되면 Mediator 객체를 통해 여러개의 JButton과 하나의 JLabel을  생성하는 것이다. 코드를 살펴보자.

```java
class Mediator {
    BtnView btnView;
    BtnSearch btnSearch;
    BtnBook btnBook;
    BtnBorrow btnBorrow;
    LblDisplay show;

    void registerView(BtnView v) {
        btnView = v;
    }

    void registerSearch(BtnSearch s) {
        btnSearch = s;
    }

    void registerBook(BtnBook b) {
        btnBook = b;
    }
    
    void registerBorrow(BtnBorrow bb) {
    	btnBorrow = bb;
    }

    void registerDisplay(LblDisplay d) {
        show = d;
    }


    void book() {
        btnBook.setEnabled(false);
        btnView.setEnabled(true);
        btnSearch.setEnabled(true);
        btnBorrow.setEnabled(true);
        show.setText("booking...");
    }

    void view() {
        btnView.setEnabled(false);
        btnSearch.setEnabled(true);
        btnBook.setEnabled(true);
        btnBorrow.setEnabled(true);
        show.setText("viewing...");
    }

    void search() {
        btnSearch.setEnabled(false);
        btnView.setEnabled(true);
        btnBook.setEnabled(true);
        btnBorrow.setEnabled(true);
        show.setText("searching...");
    }
    
    void borrow() {
    	btnSearch.setEnabled(true);
        btnView.setEnabled(true);
        btnBook.setEnabled(true);
        btnBorrow.setEnabled(false);
        show.setText("borrowing...");
    }

}
```

register~메소드로 등록을 하고 다른 각각의 JButton 서브객체에 해당하는 메소드로는 버튼의 활성/비활성을 하고 JLabel로 메시지를 보여주는 기능을 하는데 이 객체에서 4개의 JButton과 1개의 JLabel에 대해 모두 책임지고 있다는 것을 알 수 있다. 객체를 Mediator에 register 시킬 때는 그 객체의 생성자가 호출되는 때이므로 main 함수에서 4개의 객체를 생성할 때 모두 register를 시키고 *actionPerformed* 메소드를 overriding 해서 user가 버튼을 누를 때마다 각 객체가 구현하고 있는 interface인 Command 객체로 각 메소드를 호출한다. 

이렇게 Mediator를 사용하면 JButton 이든, JLabel이든 어떤 element를 삭제하거나 추가할 때 Mediator에 등록만 해주면 main 함수에서 사용할 수 있기 때문에 유지보수가 굉장히 편리하다는 것을 알 수 있다. 이제 main함수를 잠깐 보자.

```java
class MediatorDemo extends JFrame implements ActionListener {

    Mediator med = new Mediator();

    // Constructor
    MediatorDemo() {
        JPanel p = new JPanel();
        
        // Add JButton objects into JPanel object.
        p.add(new BtnView(this, med));
        p.add(new BtnBook(this, med));
        p.add(new BtnSearch(this, med));
        p.add(new BtnBorrow(this, med));

        // Add LblDisplay object and JPanel objects into contentpane object.
        getContentPane().add(new LblDisplay(med), "North");
        getContentPane().add(p, "South");
        setSize(400, 200);
        setVisible(true);
    }

    // Event handler object
    // When event occurs, this function is called and ActionEvent object is passed by parameter
    // Cast ActionEvent object to call execute() function.
    public void actionPerformed(ActionEvent ae) {
        Command comd = (Command) ae.getSource();
        comd.execute();
    }

    public static void main(String[] args) {
        new MediatorDemo();
    }

}
```

main 함수에서는 보다시피 Mediator 객체인 med만 JPanel에 넘겨주는 것을 볼 수 있다. Mediator 객체에만 연결시켜서 동작하게 하는 것이다. 어쩌피 또 다른 객체에 연결시켜줄테니 말이다.



![default](https://user-images.githubusercontent.com/35518072/38125646-f77e5834-3425-11e8-8830-f7567ff37548.PNG)

실행을 시키면 다음과 같이 4개의 JButton 서브객체가 "south"에 생기고 1개의 JLabel 서브객체가 "north"에 생겨서 *actionPerformed*에 의해 버튼을 누를 때마다 상태가 바뀌게 된다. 이렇게 Mediator 패턴에 대해 알아봤는데 객체 하나로 여러개의 객체 관리를 쉽게 할 수 있다는 이점이 정말 대단한 것 같다. 잘 기억해두고 써먹도록 하자.

