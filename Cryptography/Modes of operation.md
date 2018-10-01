# Modes of Operation (블록 암호 운용 방식)

Block cipher는 특정한 크기의 block을 암호화하는 기법을 말하는데 어떤 plaintext를 암호화 할 때 해당 block 크기에 맞춰서 입력되는 것이 아니기 때문에 가변적인 크기를 어떻게 block으로 만들 것이며 어떤 식으로 암호화를 해야하는지, 또한 취약점은 무엇인지 알아보기로 한다.



## Electronic CodeBook (ECB)

<img src="https://upload.wikimedia.org/wikipedia/commons/c/c4/Ecb_encryption.png">

<img src="https://upload.wikimedia.org/wikipedia/commons/6/66/Ecb_decryption.png">
$$
C_i = E_K(P_i)\\
P_i = D_K(C_i)
$$


위와 같이 일정한 크기의 block으로 쪼개서 각각을 독립적으로 암호화/복호화 하는 방식으로 가장 기본적인 운용 방식이다.

* 그래픽 데이터인 경우에 plaintext의 변화가 미미한 경우 ciphertext의 변화도 미미할 수 있다.
* 이러한 경우 공격자가 쉽게 confusion이나 diffusion을 깨뜨릴 수 있기 때문에 위험하다.
* block이 적게 나올 데이터에만 사용하도록 하자.

plaintext나 ciphertext의 1-bit에 에러가 발생했다고 했을 때 전체적인 데이터에 영향을 미치지만 다른 block으로 옮겨가지는 않는다.



##Cipher Block Chaining (CBC) 

ECB는 각 block이 독립적이어서 취약했기 때문에 각 block 간의 관계를 이용하기로 한다.

<img src="https://upload.wikimedia.org/wikipedia/commons/d/d3/Cbc_encryption.png">

<img src="https://upload.wikimedia.org/wikipedia/commons/6/66/Cbc_decryption.png">
$$
C_1 = E(K, [P_1\ XOR\ IV])\\
P_1 = IV\ XOR\ D(K, C_1)
$$


**IV(Initialization Vector)**라는 것을 사용하여 처음 plaintext와 XOR을 한다음 암호화를 하고, 이후 다시 IV 역할을 하는 것이 ciphertext가 되어서 계속 이어지는 방식이다.

* IV 값이 알려지게 될 경우 bit 단위로 미세하게 조정할 수 있다는 취약점이 있다.
* 2개의 메시지를 똑같은 IV를 사용해서 암호화 하면 ciphertext가 같을 수밖에 없기 때문에 IV는 그 때의 시간 값을 이용하는 것이 권고된다.
* 복호화를 할 때 ciphertext에 에러가 발생할 경우 대응되는 plaintext와 해당 ciphertext와 XOR해서 나오는 다음 block의 plaintext에 에러가 퍼지게 된다.



## Cipher FeedBack (CFB)

<img src="https://upload.wikimedia.org/wikipedia/commons/f/fd/Cfb_encryption.png">

<img src="https://upload.wikimedia.org/wikipedia/commons/7/75/Cfb_decryption.png">
$$
C_i = E_K(C_{i-1})\ XOR\ P_i\\
P_i = E_K(C_{i-1})\ XOR\ C_i\\
C_{0} = IV
$$


Plaintext가 아닌 IV를 암호화하는 방식으로 block cipher이지만 bit 단위로 다루기 때문에 stream cipher로도 부른다.

* Plaintext나 ciphertext의 block 크기가 사용하려는 암호 기법에서 요구하는 block 크기보다 작을 경우 사용되는 것이 보통이다.
* 암호화를 할 때 ciphertext를 가지고 XOR을 하기 때문에 암호화/복호화 전부 암호화 기법을 사용한다.
* 피드백으로 암호화가 이루어지기 때문에 에러가 퍼지는 것이 치명적이다.



## CTR (Counter)

<img src="https://upload.wikimedia.org/wikipedia/commons/3/3f/Ctr_encryption.png">

<img src="https://upload.wikimedia.org/wikipedia/commons/3/34/Ctr_decryption.png">

* ECB와 같이 독립적인 암호화/복호화가 적용되기 때문에 병렬처리가 가능하다.
* 일정하게 증가하는 counter를 사용해서 암호화/복호화를 하므로 counter가 알려지면 취약하다.
* CFB와 비슷하게 XOR을 사용하기 때문에 암호화 알고리즘만 구현하면 암호화/복호화를 할 수 있다.
* 암호화된 데이터에서 특정 부분만 복호화가 가능하다.



## OFB (Output FeedBack)

<img src="https://upload.wikimedia.org/wikipedia/commons/a/a9/Ofb_encryption.png">

<img src="https://upload.wikimedia.org/wikipedia/commons/8/82/Ofb_decryption.png">
$$
\begin{align}
&C_i = P_i\ XOR\ O_i\\
&P_i = C_i\ XOR\ O_i\\
&O_i = E_K(I_i)\\
&I_i = O_{i-1}\\
&I_0 = IV
\end{align}
$$


CFB와 거의 비슷하지만 CFB의 피드백이 ciphertext로 이루어지는 것과 다르게 OFB의 피드백은 ciphertext가 나오기 직전 값으로 피드백을 한다.

* CFB와는 다르게 ciphertext에 에러가 발생해도 그 직전 값을 사용하기 때문에 다음 block으로 에러가 퍼질 가능성이 적다고 할 수 있다.



## 사진 출처

* [위키백과](https://ko.wikipedia.org/wiki/%EB%B8%94%EB%A1%9D_%EC%95%94%ED%98%B8_%EC%9A%B4%EC%9A%A9_%EB%B0%A9%EC%8B%9D)