# TorderExam
## 개요
> 해당 프로젝트는 (주)티오더의 기본적인 태블릿 메뉴 서비스를 간단하게 구현한 샘플 프로젝트로  
> 사용자 인증과정 및 메뉴화면 조회, 장바구니 담기, 주문내역 확인, 계산서 발행 및 결제요청 등의  
> 기능을 구현하였다.
## 개발환경
- Java : JDK 1.8.0
- API Server : Spring Boot 2.5.4
- DB : MySQL 8.0.12
## 실행방법
```
java -jar ./bin/exam-1.0.4-SNAPSHOT.jar

Entry-point
http://localhost:8080/login.html
```
## 실행환경
- 해당 프로젝트는 크롬브라우저의 태블릿기능에서 개발되었음
- 최적 해상도 : 1024px * 768px (iPad)
## API 명세서
- More details -> [link](https://github.com/kevin0309/TorderExam/wiki/API-document)
- API code|method|URI|link
    ---|---|---|---
    USR001|`PUT`|/api/user/join|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#USR001)
    USR002|`POST`|/api/user/login|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#USR002)
    USR003|`POST`|/api/user/info|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#USR003)
    MNU001|`GET`|/api/menu/tree|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#MNU001)
    MNU002|`PUT`|/api/menu/order|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#MNU002)
    MNU003|`GET`|/api/menu/order|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#MNU003)
    MNU004|`GET`|/api/menu/payment|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#MNU004)
    MNU005|`PUT`|/api/menu/payment|[link](https://github.com/kevin0309/TorderExam/blob/main/docs/API_DOC.md#MNU005)
## 구현과정
### Database 설계
- Database Diagram
    ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/ERD.JPG?raw=true)
- table|description
    ---|---
    user|사용자 정보
    user_password|사용자 비밀번호 변경 내역
    menu|주문할 상품(음식메뉴)
    menu_type|menu의 종류(마른안주, 주류 등) / 해당 테이블은 트리구조로 재귀순환할 수 있게 설계됨
    menu_option|주문할 상품의 옵션메뉴(치즈토핑 추가, 양념 추가 등) / 해당 테이블은 1.0.4 버전 기준으로 사용되고있지 않음
    ordering|주문내역 트랜잭션
    ordering_menu|각 주문내역 트랜잭션에 대한 menu 리스트
    payment|결제요청 트랜잭션
    payment_order|각 결제요청 트랜잭션에 대한 ordering 리스트
- Indexes
    - user.id : 유저 아이디로 검색하는 기능을 위해 설정
### Database 구축
- 개인적으로 사용하는 서버PC가 있어 서버PC에 해당 Database를 구축함
### API 서버의 구현
- https://github.com/kevin0309/TorderExam/issues/3
### 프론트엔드 페이지의 구현
- 프론트엔드 페이지 구현에는 다음과 같은 언어/라이브러리가 사용됨
    - HTML5
    - Vanilla JS
    - CSS3
    - jQuery 1.11.1
    - jQuery Cookie
    - Bootstrap 4
- 기본적으로 모든 페이지는 정적 리소스(HTML)로 구현되었으며 API Server의 End-point에 접근하여 데이터를 주고받음
- 로그인 페이지의 구현
    https://github.com/kevin0309/TorderExam/blob/ae45e6c2218ad480018d7333af9e55a0b6a6ef7f/source/src/main/resources/static/login.html
- 메인 페이지의 구현
    https://github.com/kevin0309/TorderExam/blob/ae45e6c2218ad480018d7333af9e55a0b6a6ef7f/source/src/main/resources/static/index.html
    1. 메뉴 목록의 조회
    2. 장바구니
       - 장바구니 기능은 JS만을 사용해 구현되었음 (서버통신X)
    3. 주문목록
    4. 계산서
## 실행 결과 화면
- 로그인 화면
    1. 기본(로그인)  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%99%94%EB%A9%B4/1-%EA%B8%B0%EB%B3%B8.JPG?raw=true)
    2. 회원가입  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%99%94%EB%A9%B4/2-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.JPG?raw=true)
        - 회원가입폼은 화면에 표시된 바와 같이 정규식을 통해 필터링하는 기능이 구현되어있음
    3. 회원가입 실패  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%99%94%EB%A9%B4/3-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85%EC%8B%A4%ED%8C%A8.JPG?raw=true)
        - API error의 경우 프론트단에서는 따로 처리하는 로직을 구현하지 않고 그대로 보여주었음 (개발용)
    4. 회원가입 성공  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%99%94%EB%A9%B4/4-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85%20%EC%84%B1%EA%B3%B5%EB%A9%94%EC%8B%9C%EC%A7%80.JPG?raw=true)
        - 회원가입 성공시 위와 같은 알림메세지 출력후 로그인화면으로 이동됨
    5. 로그인 실패  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%99%94%EB%A9%B4/5-%EB%A1%9C%EA%B7%B8%EC%9D%B8%EC%8B%A4%ED%8C%A8.JPG?raw=true)
    6. 로그인 성공  
        - 로그인을 성공하면 메인페이지로 이동된다.
- 메인 화면
    1. 기본  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/1-%EA%B8%B0%EB%B3%B8.JPG?raw=true)
    2. 메뉴선택  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/2-%EB%A9%94%EB%89%B4%EC%84%A0%ED%83%9D.JPG?raw=true)
        - 각각의 메뉴들은 API Server를 통해 받아오기 떄문에 실시간으로 변경점이 반영됨
    3. 다른 메뉴로 이동  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/3-%EB%8B%A4%EB%A5%B8%EB%A9%94%EB%89%B4%EC%9D%B4%EB%8F%99.JPG?raw=true)
    4. 장바구니  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/4-%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%EC%97%90%20%EB%8B%B4%EA%B8%B0.JPG?raw=true)
        - 음식 이미지를 클릭시 장바구니에 담기게 됨
        - 장바구니에 있는 `+`, `-`, `삭제` 버튼을 통해 추가적인 조작이 가능
    5. 장바구니에서 주문하기  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/5-%EC%A3%BC%EB%AC%B8%ED%95%98%EA%B8%B0.JPG?raw=true)
        - 장바구니에서 `주문하기` 버튼 클릭 시 주문내역 화면으로 전환되며 주문내역을 확인할 수 있음
    6. 추가 주문접수  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/6-%EC%B6%94%EA%B0%80%20%EC%A3%BC%EB%AC%B8%EC%A0%91%EC%88%98.JPG?raw=true)
        - 추가로 주문을 접수한 뒤 주문내역 화면
        - 여러개의 주문이 표시되는 것을 확인할 수 있음
    7. 계산서  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/7-%EA%B3%84%EC%82%B0%EC%84%9C%20%ED%99%95%EC%9D%B8.JPG?raw=true)
        - 계산서는 주문내역을 기반으로 주문한 모든 메뉴가 정리되어 나타나게됨
    8. 결제완료  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/8-%EA%B2%B0%EC%A0%9C%EC%99%84%EB%A3%8C.JPG?raw=true)
        - 계산서의 `결제하기` 버튼을 클릭하여 결제를 진행할 수 있음
        - 실제 로직상으로는 결제요청이 새로 생기는 단계지만 해당 어플리케이션은 이를 결제 완료되었다고 가정함
    9. 결제완료 이후 주문내역  
        ![image](https://github.com/kevin0309/TorderExam/blob/main/docs/%EA%B2%B0%EA%B3%BC%ED%99%94%EB%A9%B4/%EB%A9%94%EC%9D%B8%ED%99%94%EB%A9%B4/9-%EA%B2%B0%EC%A0%9C%EC%99%84%EB%A3%8C%20%EC%9D%B4%ED%9B%84%20%EC%A3%BC%EB%AC%B8%EB%82%B4%EC%97%AD.JPG?raw=true)
        - 결제완료 후 주문내역이 비어있는것을 확인할 수 있음
