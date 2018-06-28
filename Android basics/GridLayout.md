# GridLayout

GridLayout은 뷰를 다음과 같이 테이블 구조로 나열하는 형태이다.

<img src="https://i.stack.imgur.com/K9WPT.jpg">

TableLayout이 따로 있긴 하지만 셀을 정적으로 지정해야 하기 때문에 사용자에 맞춰 동적으로 제어하기가 어렵다. 하지만 GridLayout은 셀이 자동개행이 되며 여러가지 편리한 속성을 가진다.

# 속성

* orientation : 뷰의 배치 방향을 결정하며 기본값은 가로방향이다.
* columnCount : 가로 방향일 때 한 줄에 몇개의 뷰를 나열할 것인지 결정한다.
* rowCount : 세로 방향일 때 한줄에 몇개의 뷰를 나열할 것인지 결정한다.

orientation이 horizontal이면 columnCount만 적용되고 vertical이면 rowCount만 적용된다.

* layout_column : 뷰가 위치할 열 인덱스
* layout_row : 뷰가 위치할 행 인덱스
* layout_columnSpan : 가로 방향으로 여러 열을 하나의 뷰가 차지하는 경우
* layout_rowSpan : 세로 방향으로 여러 행을 하나의 뷰가 차지하는 경우
  * layout_gravity : 하나의 열 내에서 뷰의 정렬 위치 결정

GridLayout을 만들어서 index로 뷰의 위치를 지정해보았는데 계속 부딪혔던 문제는 3행까지 뷰가 있는데 5행에 뷰를 위치시키면 4행이 없기 때문에 밑으로 쭉 내려온다는 것이었다. 그러니 꼭 행이나 열을 확인하고 뷰를 위치시키도록 하자.

