# BCNF를 이용한 Decomposing

## BCNF의 정의

> *"A relation R is in BCNF if and only if: whether there is a nontrivial FD A(~1~)A(~2~)...A~n~ → B~1~B~2~...B~m~ for R, it is the case that {A~1~, A~2~, ..., A~n~} is a super key for R"*

즉, relation R의 non-trival FD에 대해서 left side가 primary key를 포함한 super key라면 relation R이 BCNF에 있다고 한다.



## 예제 1

**Movies1{title, year, length, genre, studioName, starName}** 과 같은 relation이 있다고 했을 때 {title, year, starName}을 primary key로 다른 모든 attributes를 결정한다고 할 수 있기 때문에 <u>super key는 {title, year, starName}이 포함되어 있는 set of attributes이다.</u>

하지만 다음과 같은 non-trivial FD가 존재한다.

*title year → length genre studioName*

left side인 {title, year}가 primary key인 {title, year, starName}을 포함하지 않기 때문에 relation ==Movies1은 BCNF에 있지 않다.==



## 예제 2

**Movies2{title, year, length, genre, studioName}** 과 같은 relation이 있다고 하자. 위 예제에서 starName attribute를 제거한 relation이다. 여기서의 primary key는 당연히 starName이 없기 때문에 {title, year}이다. 여기서 다음 조건들을 만족한다.

* title과 year 모두 혼자서 다른 attributes를 functionally determine 할 수 없다.
* {title, year}가 반드시 있어야만 다른 attributes를 non-trival functionally determine 할 수 있기 때문에 left side는 항상 super key가 된다. 

따라서, ==Movies2는 BCNF에 있다.==



## 2개의 attributes를 가진 relation은 항상 BCNF에 있다.

1. **{A,B}**

non-trivial FD에 대해서만 알면 되는데, 다른 FD가 주어져 있지 않고 자기 자신에 대한 FD인 trivial FD만 존재하기 때문에 BCNF 조건을 만족한다.

2. **A→B이고 B→A는 아닌 경우**

A가 유일한 primary key이고 모든 non-trivial FD가 left side에 A를 포함하기 때문에 BCNF 조건에 위배되지 않는다.

3. **B→A이고 A→B는 아닌 경우** : 2번과 같은 경우이다.
4. **A→B이고 B→A인 경우**

A와 B가 둘 다 primary key이다. 모든 FD는 left side로 반드시 A 혹은 B를 가져야 하므로 BCNF 조건을 만족한다.

2개의 attributes를 가지는 경우는 primary key가 1개인 경우 그 키가 super key이다. 그 이유는 만약에 A를 primary key라고 했을 때 B를 추가한 경우가 super key인데 그렇게 되면 1번의 경우와 같이 trivial FD이므로 신경쓰지 않아도 된다. 



## BCNF를 이용해서 Decomposing 하는 step

1. 주어진 relation R이 BCNF에 있는지 확인한다. 있으면 R이 답이다.
2. 만약 BCNF 조건을 위반한다면 조건을 위반하는 X→Y라는 FD에 대해  X의 Closure인 X^+^ 를 계산하자.
3. R~1~ = X^+^ 라고 하고 R~2~ = X와 X^+^를 제외한 나머지 attributes 라고 하자.
4. R~1~과 R~2~의 set of FDs를 S~1~, S~2~라고 하자.
5. 재귀적으로 R~1~과 R~2~를 알고리즘을 통해 Decomposing 한 후에 그 union을 반환하자.



## 예제

**R {title, year, studioName, president, presAddr}**이라는 relation이 있다고 하자. primary key는 {title, year}임을 알 수 있지만 non-trivial FD를 만족하지 않는 경우가 존재 한다.

1. **BCNF에 있는지 확인하기**

title year → studioName  
studioName → president (BCNF 조건 위배 : super key가 아니다)  
president → presAddr (마찬가지로 BCNF 조건 위배)

2. **조건을 위반하기 때문에 위반하는 FD들에 대해 처리하기**

2가지가 있기 때문에 과정을 2번 거쳐야 한다.

3. **1번째 조건 위배 : {studioName}의 closure 계산**

X^+^ = {studioName}  
→ X^+^ = {studioName} (trivial funtional dependency)  
→ X^+^ = {studioName, president} (studioName → president)  
→ X^+^ = {studioName, president, presAddr} (president → presAddr)

4. **R~1~과 R~2~ 초기화**

R~1~ = {studioName, president, presAddr} = X^+^  
R~2~ = {title, year, studioName} = X + X^+^를 제외한 나머지 attributes

5. **S~1~과 S~2~ 초기화**

   * Set of FDs for R~1~ = S~1~

   studioName → president  
   studioName → presAddr  
   president → presAddr (BCNF 조건 위반)  

   primary key인 studioName을 president가 포함하고 있는 super key가 아니므로 조건을 위반한다.

   * Set of FDs for R~2~ = S~2~

   title year → studioName

   {title, year}가 primary key인데 남은 attribute가 하나이므로 super key를 만족하기 때문에 BCNF 조건 만족.

6. **조건을 위반하기 때문에 재귀적으로 과정 다시 진행**

X^+^ ={president}^+^
→ X^+^ = {president} (trivial functional dependency)  
→ X^+^ = {president, presAddr} (president → presAddr)  
→ X^+^ = {president, presAddr}

7. **R~1~과 R~2~를 초기화**

R~1~ = {president, presAddr}  
R~2~ = {studioName, president}

8. **S~1~과 S~2~를 초기화**

   * Set of FDs for R~1~ = S~1~

   Attributes가 2개인 realtion은 항상 BCNF 안에 있다는 것을 위에서 증명했음!

   *  Set of FDs for R~2~ = S~2~

   위와 같음

9. **마무리**

처음 주어진 relation에 대해서 2개의 FD가 BCNF의 조건에 위배되어 첫번째에 대해 알고리즘을 진행했는데 그 안에서 2번째 경우가 나왔으므로 따로 알고리즘을 사용할 필요가 없고 나눠진 대로 적용하면 된다. 따라서 Decomposing 된 결과는 다음과 같다.

{president, presAddr}  
{studioName, president}  
{title, year, studioName}



## JC의 간단한 Decomposition

1. **Some attributes를 functionally determin하는 모든 possible key를 찾아라**
   * Directly functionally determine하는 minimal attribute의 집합
2. **Key에 의해 directly functionally determine 되는 attributes를 grouping 해라**
   * BCNF 안에 있다는 것은 non-trivial FD의 left side에 반드시 key를 포함해야 한다!
   * Key의 closure는 BCNF에 있는 relation과 일치한다.
   * Multiple attributes를 가지는 key는 하나의 relation이 될 수 있다.
3. **반복되는 attributes를 짧은 relation으로 줄여라**
4. **(선택적으로, artificial key를 사용한다면) relation에 그 relationship을 추가해라**



## 예제

**OriginalMovie{title, year, length, filmType, studioName, starName}**이 있다고 하자.

* Functionally determine하는 possible key를 전부 찾아 closure를 찾는다.

{title, year, starName, length, fileType, studioName}  
(title year starName → length fileType studioName)

{title, year, length, fileType, studioName}  
(title year → length fileType studioName)

* 중복을 제거한다.

반복되는 attributes가 length, fileType, studioName이므로 첫번째에서 중복을 제거해주면 다음과 같은 결론이 나온다.

{title, year, starName}  
{title, year, length, filmType, studioName}

이렇게 위에서 설명한 방법을 진행하면 주어진 relation은 BCNF 안에 있게 된다.

