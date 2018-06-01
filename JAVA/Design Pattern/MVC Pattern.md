# MVC Pattern(모델-뷰-컨트롤러 패턴)

> 모델-뷰-컨트롤러 패턴을 성공적으로 사용하면, 사용자 인터페이스로부터 비즈니스 로직을 분리하여 애플리케이션의 시각적 요소나 그 이면에서 실행되는 비즈니스 로직을 서로 영향 없이 쉽게 고칠 수 있는 애플리케이션을 만들 수 있다. (위키백과)

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/MVC-Process.svg/500px-MVC-Process.svg.png" height="400px">

위 그림과 같이 모델, 뷰, 컨트롤러 3개의 컴포넌트로 구분되며 각 컴포넌트가 담당하는 기능은 다음과 같다.

## Model

> *The model is the central component of the pattern. It expresses the application's behavior in terms of the problem domain, independent of the user interface. It directly manages the data, logic and rules of the application.*
>
> 모델은 패턴의 중심이 되는 컴포넌트이며 UI와 독립적으로 어플리케이션의 기능을 담당한다. 또한, 어플리케이션의 로직이나 데이터, 규칙들을 직접 관리한다.

학생이라는 모델이 있다고 했을 때를 보면,

```java
public class Student {
	private String rollNo;
	private String name;

	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
```

다음과 같이 getter, setter 메서드가 있고 학생의 학번과 이름을 데이터베이스로부터 받아오고 저장하는 역할을 하는 것을 알 수 있기 때문에 모델이라고 볼 수 있다.

## View

> *A view can be any output representation of information, such as a chart or a diagram. Multiple views of the same information are possible, such as a bar chart for management and a tabular view for accountants.*
>
> 뷰는 차트나 다이어그램과 같이 정보를 나타내는 어떤 것도 될 수 있다. 경영관리에 차트나 회계 표와 같이 동일한 정보를 다수의 뷰로도 나타낼 수 있다.

모델의 코드와 이어서 학생의 학번과 이름을 표시하는 것을 뷰라고 할 수 있다.

```java
public class StudentView {
	public void printStudentDetails(String studentName, String studentRollNo) {
		System.out.println("Student: ");
		System.out.println("Name: " + studentName);
		System.out.println("Roll No: " + studentRollNo);
	}
}
```

## Controller

> *Controller acts on both model and view. It controls the data flow into model object and updates the view whenever data changes. It keeps view and model separate.*
>
> 컨트롤러는 모델과 뷰에 모두 영향을 주며 모델의 데이터 흐름을 제어하고 데이터가 변할 경우 뷰를 갱신합니다. 또한 뷰와 모델의 역할을 분리합니다.

모델과 뷰를 관리하며 사용자의 요청을 처리하는 컴포넌트가 바로 컨트롤러이다. 학생들의 정보 갱신이나 정보를 보여주는 역할을 모델,뷰를 이용해서 처리하는 것이다.

```java
public class StudentController {
	private Student model;
	private StudentView view;

	public StudentController(Student model, StudentView view) {
		this.model = model;
		this.view = view;
	}
    // Model 이용
	public void setStudentName(String name) {
		model.setName(name);
	}
	public String getStudentName() {
		return model.getName();
	}
	public void setStudentRollNo(String rollNo) {
		model.setRollNo(rollNo);
	}
	public String getStudentRollNo() {
		return model.getRollNo();
	}
    // View 이용
	public void updateView() {
		view.printStudentDetails(model.getName(), model.getRollNo());
	}
}
```

## 모델-뷰-컨트롤러의 상호작용

각각의 컴포넌트를 알아봤으니 마무리하면서, 어떤식으로 상호작용하는지 정리하도록 하자.

* **모델**은 어플리케이션의 데이터 관리를 맡고 있고 컨트롤러로부터 사용자의 요청을 받는다.
* **뷰**는 모델이 특정 포맷으로 표현되는 것이다.
* **컨트롤러**는 사용자의 요청에 응답하여 모델과 상호작용하는데 요청에 대해 선택적으로 검증을 거친 후에 모델로 전달한다.

학생에 대한 모델-뷰-컨트롤러 패턴을 적용한 UML은 다음과 같다.

![default](https://user-images.githubusercontent.com/35518072/40815484-c8b05ec0-6581-11e8-8a34-fbeb507c2513.JPG)

## 출처

[모델-뷰-컨트롤러](https://ko.wikipedia.org/wiki/%EB%AA%A8%EB%8D%B8-%EB%B7%B0-%EC%BB%A8%ED%8A%B8%EB%A1%A4%EB%9F%AC)  
[Model-View-Controller](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)  
[MVC Pattern 이란?](https://hanee24.github.io/2018/02/14/what-is-mvc-pattern/)  
[Design Patterns MVC Pattern](https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm)