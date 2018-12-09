## Digital Certificate

* 여권의 디지털 버전이다.
* 인터넷 상에서 사람이나 기관의 신원을 보증한다.
* 사용자와 공개키를 연관시켜준다.
* CA(Certificate Authority)에 의해 생성된다.

<br>

## Certificate Authority

인증서를 발급하는 신뢰할 수 있는 기관이며 사용자가 CA와 통신하기 위해선 CA의 공개키를 알아야 한다. 따라서 모든 사용자는 CA의 공개를 알고 있고 아래와 같은 과정으로 상대방의 인증서를 얻어낸다. A와 B가 통신을 한다고 하자.

* A가 CA에게 B의 인증서를 요구한다.
* CA는 B의 인증서를 자신의 개인키로 암호화시켜 A에게 전송한다.
* A는 CA의 공개키를 알고 있으므로 복호화하여 B의 인증서를 얻어낸다.

B의 인증서는 CA에게서 얻어내는 방법 말고도 B에게서 얻는 방법도 있다.

<br>

## X.509 Certificate Format

인증서의 표준 형식으로 다음과 같은 값들을 가진다.

* 버전(Version): 가장 최신버전은 3이다.
* 고유번호(Certificate Serial Number): CA가 할당한 정수로 된 고유번호이다.
* 서명 알고리즘 식별자(Signature Algorithm Identifier)
* 발행자(Issuer Name)
* 유효기간(Validity)의 시작과 끝
* 소유자(Subject Name)
* 소유자의 공개키 정보(Subject Public Key Information)

SSL 인증서 예시는 아래와 같이 보여진다.

<img src="./images/ssl certificate.png">

<br>

## PKI Components

<img src="./images/pki component.png" width="400px">

공개기 환경(Public Key Infrastructure)은 위와 같이 구성된다.

* CA
  * 인증서를 발행(issue), 취소(revoke), 관리(manage)한다.
* RA(Registration Authority)
  * RA는 CA의 일을 나눠서 하고 CA를 분리하기 위해 있는 기관으로 여러가지 일을 맡아서 한다.
  * 새로운 사용자에 대한 등록정보(Registration Info)를 확인하고 받아들인다.
  * 사용자 대신 키를 생성하기도 한다.
  * 키의 백업이나 회복(recovery)에 대한 요청을 확인하고 받아들인다.
  * 인증서의 취소에 대한 요청을 확인하고 받아들인다.
  * 하지만 RA는 인증서를 발급하지는 않는다는 점을 주의하자.
* Key Recovery Server
  * 개인키를 잃어버렸을 경우 인증서를 폐기하고 새로운 키의 쌍을 생성하거나 서버로부터 저장한 키를 획득하는 방법이 있다.
  * 미리 키를 저장하는데 사용하는 용도가 바로 이 서버이다.
* Certificate Directory(X.500)
  * 사용자가 인증서를 보관하기도 하지만 X.500 표준의 이 디렉토리 또한 인증서를 보관한다.

<br>

## Certificate Creation Steps

* 키 생성(Key Generation)
  * RA와 사용자 모두 키의 쌍을 생성할 수 있지만 RA가 키를 생성할 경우 RA가 개인키를 알게 되고 사용자에게 어떻게 보낼지에 대한 문제가 발생한다.
* 등록(Registration)
  * 사용자가 키를 생성했다는 가정하에 RA에게 공개키를 보내게 되는데 RA는  해당 공개키가 정말 사용자의 것인지 알아야 한다.
* 검증(Verification)
  - 따라서, RA는 CSR(Certificate Signing Request)을 개인키로 암호화해서 보내기를 요청하거나 아니면 RA가 설정한 랜덤한 숫자를 사용자의 공개키로 암호화해서 보내는 방법을 사용한다.
  - CSR이란 인증서를 요청할 때 사용자가 보내는 메시지를 말한다.
* 서명 생성(Certificate Creation)
  * CA가 인증서를 생성한다.
  * 생성한 후에 RA(혹은 사용자), Certificate Directory에 보내고 필요하다면 개인키를 Key Recovery Server에 보낸다.

<br>

## CA는 Certificate를 어떻게 서명하는가?

CA는 X.509 표준 포맷에서 마지막 값을 제외한 나머지 값을 message digest algorithm을 이용해 MD(Message Digest)를 생성한다. 생성된 MD를 CA의 개인키로 암호한 뒤에 인증서의 마지막 값으로 넣은 후 보낸다.

사용자가 인증서를 받게 되면 똑같이 마지막 값을 제외한 모든 값들에 대해 digest algorithm으로 MD를 생성하여 비교를 하는데, 받은 인증서의 마지막 값을 CA의 공개키로 복호화하여 MD와 비교한다. 이 때, 일치하면 검증이 된다.

<br>

## Certificate Hierarchy

사용자가 CA의 공개키를 알고 있어야 하는데 CA의 공개키에 대한 인증서는 누가 생성해줄까? CA는 계층구조로 되어있기 때문에 각 CA의 인증서를 발급해주는 CA가 있어서 root CA까지 올라가게 된다.

Root CA의 경우 스스로 신뢰할 수 있는 자명한 기관으로 여겨져서 root CA의 인증서는 사용하는 곳에 하드코딩으로 프로그램 된다. 단, 국가가 다르다면 root CA가 다를 수 있기 때문에 이 경우 서로 인증서를 발급해주는 cross-certification을 한다.

<br>

## Certification Revocation

여러가지 문제에 대해서 인증서를 폐기해야 할 때가 있다.

* 개인키가 노출된 경우
* CA가 인증서를 만드는데 실수한 경우
* 인증서의 소유자가 떠나는 경우

따라서, 해당 인증서가 폐기되었는지 확인을 해야 하는데 오프라인 방법인 CRL(Certification Revocation List)과 온라인 방법인 OCSP(Online Certificate Status Protocol)가 있다.

* CRL
  * 정기적으로 갱신되고 발행된다.
  * 유효기간이 끝난 인증서는 포함하지 않는다.
  * 사용자는 반드시 가장 최근의 CRL을 확인해야 한다.
  * CRL의 크기가 점점 커져서 전송시간이 길어지기 때문에 변화된 부분만 발행하는 Delta CRL을 사용하기도 한다.
  * 단, CRL이 발행되고 난 뒤에 취소된 인증서의 경우 확인을 할 수 없다는 문제가 있다.
* OCSP
  * 사용자는 서버(OCSP responder)에게 OCSP 요청을 인증서과 같이 보내고 서버는 X.500 디렉토리에서 해당 인증서의 취소여부를 판단한다.
  * 이후 유효한지 하지 않은지 사용자에게 응답해준다.
  * 인증서의 즉각적인 상태정보를 알려주기 때문에 CRL과 다르게 실시간 확인이 가능하다는 장점이 있다.