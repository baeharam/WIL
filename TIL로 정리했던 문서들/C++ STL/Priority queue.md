# Priority queue(우선순위 큐)

PS를 하다가 예전부터 정리하려고 했는데 지금에서야 정리한다.. (게으름) 보통 실제로 우선순위 큐를 구현하기 위해선 힙을 구현하는데 구현하는 것 자체가 복잡하다. 하지만 C++에 미리 정의된 우선순위 큐를 사용하면 정말 간단하다.(C++만세)

## 템플릿 인자의 구성과 의미

`<queue>` 헤더파일에 정의되어 있으며 역시 `std` namespace에 있다. 구성은 다음과 같다.

```c++
template<class T, class Container = std::vector<T>, class Compare = std::less<typename Container::value_type>
```

* **T**

저장된 원소들의 타입으로 C++17이후부터 `Container::value_type`과 같은 타입이 아닐 경우 behavior는 undefined이다.

* **Container**

원소들을 저장하기 위해서 내장된 container로 [SequenceContainer](https://en.cppreference.com/w/cpp/concept/SequenceContainer)의 조건을 만족해야 하는데 원소들을 선형으로 저장하는 container를 말한다. STL에서는 array, vector, deque,list 등이 있다. 또한 iterator의 경우 [RandomAccessIterator](https://en.cppreference.com/w/cpp/concept/RandomAccessIterator)의 조건을 만족해야 한다. 추가적으로, `front()`, `push_back()`, `pop_back()`을 보통에 사용하는 의미로 제공해야 한다.

* **Compare**

반드시 [Strict weak ordering](http://adnoctum.tistory.com/206)을 제공해야 하는데 처음 보는 용어라 공부해 보니 간단하게 말해서, 대소 관계가 정의되지 않으면 동일하다는 것을 말한다. 자세한 내용은 링크를 참고하자.

Compare 함수의 매개변수에서 첫번째 인자가 weak ordering으로 두번째 인자보다 먼저 온다면 `true`를 리턴한다. 하지만 우선순위 큐는 가장 큰 값을 첫번째로 내보내기 때문에, 실제적으로 첫번째 인자가 마지막에 나온다. 이것은 큐의 가장 앞에는 Compare 함수에 의해 부여된 weak ordering에 따라서 마지막 값이 오게 된다는 말과 같다.

## 멤버함수들

* [top](https://en.cppreference.com/w/cpp/container/priority_queue/top) : 제일 첫번째 원소에 접근하여 리턴한다.
* [empty](https://en.cppreference.com/w/cpp/container/priority_queue/empty) : container가 비어있는지 확인하고 비어있으면 true를 리턴한다.
* [size](https://en.cppreference.com/w/cpp/container/priority_queue/size) : 원소의 개수를 리턴한다.
* [push](https://en.cppreference.com/w/cpp/container/priority_queue/push) : 원소를 삽입하고 내장된 container를 정렬한다.
* [emplace](https://en.cppreference.com/w/cpp/container/priority_queue/emplace) : C++11부터 추가된 함수로 in-place로 원소를 생성하고 내장된 container를 정렬한다
* [pop](https://en.cppreference.com/w/cpp/container/priority_queue/pop) : 제일 첫번째 원소를 제거한다.
* [swap](https://en.cppreference.com/w/cpp/container/priority_queue/swap) : C++11부터 추가된 함수로 내장된 container 안의 원소들을 swap한다.

이외에도 우선순위 큐를 인자로 받는 다른 함수나 helper class가 존재하나 아직 필요성을 못 느껴서 여기서는 정리하지 않았다.

## 예제

```c++
#include <functional>
#include <queue>
#include <vector>
#include <iostream>

// 큐의 값들을 출력하는 함수
template<typename T> void print_queue(T& q){
    while(!q.empty()){
        std::cout<<q.top()<<" ";
        q.pop();
    }
    std::cout<<'\n';
}

int main(){
    std::priority_queue<int> q;
    
    // 여기서 for문을 이렇게 쓸 수 있는지 처음 알았다...ㄷㄷ
    for(int n : {1,8,5,6,3,4,0,9,7,2})
        q.push(n);
    print_queue(q); // 내림차순
    
    // 레퍼런스에는 명시가 없는데 std::greater는 <funtional> 헤더파일을 include 해야함
    std::priority_queue<int, std::vector<int>, std::greater<int> > q2;
    
    for(int n: {1,8,5,6,3,4,0,9,7,2})
        q2.push(n);
    print_queue(q2); // 오름차순
    
    // 원소들을 비교하기 위해 lambda를 사용한다고 하는데 뭔지 모름...
    auto cmp = [](int left, int right){
        return (left^1) < (right^1);
    };
    std::priority_queue<int, std::Vector<int>, decltype(cmp)> q3(cmp);
    
    for(int n: {1,8,5,6,3,4,0,9,7,2})
        q3.push(n);
    print_queue(q3);
}
```

결과:

```
9 8 7 6 5 4 3 2 1 0 
0 1 2 3 4 5 6 7 8 9 
8 9 6 7 4 5 2 3 0 1
```



## 출처

* [std::priority_queue](https://en.cppreference.com/w/cpp/container/priority_queue)
* [Strict weak ordering](http://adnoctum.tistory.com/206)
* [STL priority queue 활용법](http://koosaga.com/9)

