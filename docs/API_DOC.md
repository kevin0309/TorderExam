# ToderExam API document
## Intro
### Request 형식
#### header
- `Content-Type`: application/json
- 회원 인증이 필요한 API의 경우 다음과 같은 header를 요구함
    key|type|value
    ---|---|---
    `api-key`|String|'Bearer {JWT token}'
- `api-key` value의 예시
    ```
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6InRvcmRlci1leGFtLVlIIiwiZXhwIjoxNjMwMzQ1ODI0LCJpYXQiOjE2MzAzNDIyMjQsInVzZXJJZCI6InRlc3RlciJ9.8O0qHDSwBddiDxMgFWEWX54kRv2Jk2s15e31KzitcFqPCX6aUtdNMegq_gnRWqra8KnsZMYgf7YVcByjowhMXw
    ```

### Response 형식
#### header
- `Content-Type`: application/json;charset=UTF-8
#### body
- 데이터 형식은 다음과 같음
    key|type|value
    ---|---|---
    `response`|(AnyType)|요청에 대응하는 결과 / 응답실패시 null
    `error`|Object|응답실패시 HTTP 에러코드 및 실패원인 / 응답성공시 null
    `timestamp`|String|서버 응답처리 완료시각
- 성공예시 (로그인 요청의 경우)
    ```json
    {
        "response": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6InRvcmRlci1leGFtLVlIIiwiZXhwIjoxNjMwMzQ1ODI0LCJpYXQiOjE2MzAzNDIyMjQsInVzZXJJZCI6InRlc3RlciJ9.8O0qHDSwBddiDxMgFWEWX54kRv2Jk2s15e31KzitcFqPCX6aUtdNMegq_gnRWqra8KnsZMYgf7YVcByjowhMXw",
        "error": null,
        "timestamp": "2021-08-31T01:50:24.751"
    }
    ```
- 실패예시 (로그인 요청의 경우)
    ```json
    {
        "response": null,
        "error": {
            "status": 500,
            "message": "user password not matched"
        },
        "timestamp": "2021-08-31T03:36:46.903"
    }
    ```

## Index
1. [User (/api/user)](#User)
    - [USR001 (/join)](#USR001)
    - [USR002 (/login)](#USR002)
    - [USR003 (/info)](#USR003)
2. [Menu (/api/menu)](#Menu)
    - [MNU001 (/tree)](#MNU001)
    - [MNU002 (/order)](#MNU002)
    - [MNU003 (/order)](#MNU003)
    - [MNU004 (/payment)](#MNU004)
    - [MNU005 (/payment)](#MNU005)

## User
### USR001
- Description
    > 사용자 회원가입
- Auth : 비회원
- End-point
    method|URI
    ---|---
    `PUT`|/api/user/join
- Request
    key|type|value
    ---|---|---
    `id`|String|
    `pw`|String|
    `pw_confirm`|String|
- Request value constraints
    key|desc|regex
    ---|---|---
    `id`|(중복X)영문(소문자)으로 시작하고 영문(소문자), 숫자를 포함하여 5~20자 제한|`^[a-z][a-z\|_\|\\-\|0-9]{4,19}$`
    `pw`|8자 이상 영문 대문자, 영문 소문자, 숫자, 특문 모두 포함하도록 제한|`^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$`
- Response
    key|type|value
    ---|---|---
    `id`|String|
    `is_success`|boolean|성공시 true
### USR002
- Description
    > 사용자 로그인
- Auth : 비회원
- End-point
    method|URI
    ---|---
    `POST`|/api/user/login
- Request
    key|type|value
    ---|---|---
    `id`|String|
    `pw`|String|
- Response
    key|type|value
    ---|---|---
    {response}|String|JWT token
### USR003
- Description
    > 사용자 정보 수정
- Auth : 회원
- End-point
    method|URI
    ---|---
    `POST`|/api/user/info
- Request
    key|type|value
    ---|---|---
    `pw`|String|
    `pw_confirm`|String|
- Request value constraints
    key|desc|regex
    ---|---|---
    `pw`|8자 이상 영문 대문자, 영문 소문자, 숫자, 특문 모두 포함하도록 제한|```^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$```
    `pw`|이전에 사용했던 비밀번호는 다시 사용할 수 없음|
- Response
    key|type|value
    ---|---|---
    `id`|String|
    `is_success`|boolean|성공시 true

## Menu
### MNU001
- Description
    > 모든 메뉴를 메뉴종류에 맞게 트리구조로 조회
- Auth : 회원
- End-point
    method|URI
    ---|---
    `GET`|/api/menu/tree
- Request
    key|type|value
    ---|---|---
- Response
    key|type|value
    ---|---|---
    {response}|Object(TreeNodeObject)|
    - TreeNodeObject
        key|type|value
        ---|---|---
        `desc`|String|메뉴종류 설명
        `children`|Array(TreeNodeObject)|자식노드 리스트
        `menus`|Array(MenuObject)|메뉴(음식) 리스트
        - MenuObject
            key|type|value
            ---|---|---
            `seq`|int|
            `name`|String|
            `type_seq`|int|메뉴종류
            `price`|int|
            `status`|String|메뉴상태
            `image_url`|String|
    - TreeNodeObject를 통해 재귀적인 구조로 `Tree` 구조가 형성됨
- Response 예시
    ```json
    {
        "response": {
            "desc": "root",
            "children": [
                {
                    "desc": "메뉴",
                    "children": [
                        {
                            "desc": "신메뉴",
                            "children": [],
                            "menus": [
                                {
                                    "seq": 1,
                                    "name": "할매네 술국",
                                    "type_seq": 4,
                                    "price": 9000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/TOD_YHM010/M00350/1629783656item_700X700_toJPEGBot.jpg"
                                },
                                {
                                    "seq": 2,
                                    "name": "할매콤볼",
                                    "type_seq": 4,
                                    "price": 8000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM012/000119/1629770236item_700X700_toJPEGBot.jpg"
                                },
                                {
                                    "seq": 9,
                                    "name": "양념먹태",
                                    "type_seq": 4,
                                    "price": 14000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM012/000120/1629770261item_700X700_toJPEGBot.jpg"
                                }
                            ]
                        },
                        {
                            "desc": "마른안주",
                            "children": [],
                            "menus": [
                                {
                                    "seq": 3,
                                    "name": "통통오다리",
                                    "type_seq": 5,
                                    "price": 7000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900011/item_300X300_toJPEGBot.jpg"
                                },
                                {
                                    "seq": 4,
                                    "name": "버터구이오징어",
                                    "type_seq": 5,
                                    "price": 9000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900005/item_300X300_toJPEGBot.jpg"
                                }
                            ]
                        },
                        {
                            "desc": "안주류",
                            "children": [],
                            "menus": [
                                {
                                    "seq": 5,
                                    "name": "수제소시지",
                                    "type_seq": 6,
                                    "price": 5000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900018/item_300X300_toJPEGBot.jpg"
                                }
                            ]
                        },
                        {
                            "desc": "튀김류",
                            "children": [],
                            "menus": [
                                {
                                    "seq": 6,
                                    "name": "양념감자튀김",
                                    "type_seq": 7,
                                    "price": 6000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900097/item_300X300_toJPEGBot.jpg"
                                }
                            ]
                        }
                    ],
                    "menus": []
                },
                {
                    "desc": "주류",
                    "children": [
                        {
                            "desc": "주류",
                            "children": [],
                            "menus": [
                                {
                                    "seq": 7,
                                    "name": "참이슬",
                                    "type_seq": 8,
                                    "price": 4500,
                                    "status": "판매중",
                                    "image_url": "http://d25d5hdteapulp.cloudfront.net/b/5/3/b537702ac47869f558dfa82cbdd1cecab4743117"
                                }
                            ]
                        }
                    ],
                    "menus": []
                },
                {
                    "desc": "음료",
                    "children": [
                        {
                            "desc": "음료수",
                            "children": [],
                            "menus": [
                                {
                                    "seq": 8,
                                    "name": "콜라",
                                    "type_seq": 9,
                                    "price": 2000,
                                    "status": "판매중",
                                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900053/1581936336item_300X300_toJPEGBot.jpg"
                                }
                            ]
                        }
                    ],
                    "menus": []
                }
            ],
            "menus": []
        },
        "error": null,
        "timestamp": "2021-08-31T03:58:09.77"
    }
    ```

### MNU002
- Description
    > 새로운 주문을 삽입
- Auth : 회원
- End-point
    method|URI
    ---|---
    `PUT`|/api/menu/order
- Request
    key|type|value
    ---|---|---
    `menus`|Array(MenuObject)|
    - MenuObject
        key|type|value
        ---|---|---
        `menu_seq`|int|
        `qtt`|int|
- Response
    key|type|value
    ---|---|---
    {response}|boolean|성공시 true
### MNU003
- Description
    > 주문접수 상태인 주문목록을 조회
- Auth : 회원
- End-point
    method|URI
    ---|---
    `GET`|/api/menu/order
- Request
    key|type|value
    ---|---|---
- Response
    key|type|value
    ---|---|---
    `orders`|Array(OrderObject)|
    - OrderObject
        key|type|value
        ---|---|---
        `timestamp`|String|주문한 시각
        `status`|String|
        `order_menus`|Array(OrderMenuObject)|
        - OrderMenuObject
            key|type|value
            ---|---|---
            `menu`|Object(MenuObject)|
            `qtt`|int|
            - MenuObject
                key|type|value
                ---|---|---
                `seq`|int|
                `name`|String|
                `type_seq`|int|메뉴종류
                `price`|int|
                `status`|String|메뉴상태
                `image_url`|String|
- Response 예시
    ```json
    {
        "response": {
            "orders": [
                {
                    "timestamp": "오후 08:59:04",
                    "status": "주문접수",
                    "order_menus": [
                        {
                            "menu": {
                                "seq": 7,
                                "name": "참이슬",
                                "type_seq": 8,
                                "price": 4500,
                                "status": "판매중",
                                "image_url": "http://d25d5hdteapulp.cloudfront.net/b/5/3/b537702ac47869f558dfa82cbdd1cecab4743117"
                            },
                            "qtt": 2
                        },
                        {
                            "menu": {
                                "seq": 8,
                                "name": "콜라",
                                "type_seq": 9,
                                "price": 2000,
                                "status": "판매중",
                                "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900053/1581936336item_300X300_toJPEGBot.jpg"
                            },
                            "qtt": 1
                        }
                    ]
                }
            ]
        },
        "error": null,
        "timestamp": "2021-08-31T04:19:18.162"
    }
    ```
### MNU004
- Description
    > 주문목록을 기반으로 계산서 조회
- Auth : 회원
- End-point
    method|URI
    ---|---
    `GET`|/api/menu/payment
- Request
    key|type|value
    ---|---|---
- Response
    key|type|value
    ---|---|---
    {response}|Array(OrderMenuObject)|
    - OrderMenuObject
        key|type|value
        ---|---|---
        `menu`|Object(MenuObject)|
        `qtt`|int|
        - MenuObject
            key|type|value
            ---|---|---
            `seq`|int|
            `name`|String|
            `type_seq`|int|메뉴종류
            `price`|int|
            `status`|String|메뉴상태
            `image_url`|String|
- Response 예시
    ```json
    {
        "response": [
            {
                "menu": {
                    "seq": 7,
                    "name": "참이슬",
                    "type_seq": 8,
                    "price": 4500,
                    "status": "판매중",
                    "image_url": "http://d25d5hdteapulp.cloudfront.net/b/5/3/b537702ac47869f558dfa82cbdd1cecab4743117"
                },
                "qtt": 2
            },
            {
                "menu": {
                    "seq": 8,
                    "name": "콜라",
                    "type_seq": 9,
                    "price": 2000,
                    "status": "판매중",
                    "image_url": "https://s3.ap-northeast-2.amazonaws.com/images.orderhae.com/OK_YHM002/900053/1581936336item_300X300_toJPEGBot.jpg"
                },
                "qtt": 1
            }
        ],
        "error": null,
        "timestamp": "2021-08-31T04:28:05.092"
    }
    ```
### MNU005
- Description
    > 계산서를 기반으로 결제요청을 생성
- Auth : 회원
- End-point
    method|URI
    ---|---
    `PUT`|/api/menu/payment
- Request
    key|type|value
    ---|---|---
- Response
    key|type|value
    ---|---|---
    `seq`|int|
    `total_price`|int|
    `status`|String|
    `timestamp`|String|