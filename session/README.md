# Session Login Project

세션(session) 방식의 인증 흐름을 직접 구현한 학습용 프로젝트입니다.
회원가입, 로그인, 로그아웃 기능을 포함하며, 로그인 상태를 세션을 통해 유지합니다.

1차적으로 필수적인 기능 위주로 구현하고, 조금씩 리팩토링 해볼 예정입니다.

---

## 프로젝트 개요

|   항목   | 설명                                  |
|:------:|:------------------------------------|
|   목적   | 세션 기반 인증 방식 학습 및 구현                 |
| 기술 스택  | Spring Boot, Spring MVC, JPA, MySQL |
| 인증 방식  | `HttpSession` 기반 세션 로그인             |
| 테스트 도구 | Postman                             |

---

## 프로젝트 패키지 구조

### 1차

```text
study.login.session
├── controller
│   └── AuthController.java
├── service
│   └── AuthService.java
├── repository
│   └── UserRepository.java
├── domain
│   ├── Users.java
│   └── dto
│       ├── UserLoginRequest.java
│       ├── UserLoginResponse.java
│       └── UserSignupRequest.java
├── global
│   ├── exception
│   │   ├── BaseException.java
│   │   ├── CustomExceptionHandler.java
│   │   ├── ErrorCode.java
│   │   └── ErrorResponse.java
│   └── response
│       ├── BaseResponse.java
│       ├── BaseResponseService.java
│       └── BaseResponseStatus.java
```

### 2차

```text
study.login.session
├── common
│   ├── crypto
│   │   └── AesEncryptor
│   ├── exception
│   │   ├── BaseException
│   │   ├── CustomExceptionHandler
│   │   ├── EncryptionException
│   │   ├── ErrorCode
│   │   └── ErrorResponse
│   └── response
│       ├── BaseResponse
│       ├── BaseResponseService
│       └── BaseResponseStatus
├── config
│   └── SecurityConfig
└── user
├── controller
│   └── AuthController
├── crypto
│   └── EncryptionService
├── domain
│   └── Users
├── dto
│   ├── request
│   │   ├── UserLoginRequest
│   │   └── UserSignupRequest
│   └── response
│       └── UserLoginResponse
├── mapper
│   └── UserLoginMapper
├── reader
│   └── UserReader
├── repository
│   └── UserRepository
├── service
│   └── AuthService
└── session
    └── SessionManager
```

## DB 테이블 구조

| 컬럼       | 타입           | 설명         |
|----------|--------------|------------|
| id       | bigint       | 사용자 ID(PK) |
| name     | varchar(100) | 이메일        |
| email    | varchar(100) | 비밀번호       |
| password | varchar(100) | 사용자 이름     |

## API 명세

### 회원가입

**POST /api/signup**
#### Request Body 파라미터

```json
{
    "email": "test@example.com",
    "password": "1234",
    "name": "홍길동"
}
```

#### Response
#### 성공
```json
{
    "message": "회원가입에 성공했습니다.",
    "code": 1001,
    "data": null,
    "success": true
}
```

#### 실패
회원가입에 실패했다면 HTTP 상태 코드와 함께 에러 객체가 돌아옵니다.

### 로그인
**POST /api/login**
#### Request Body 파라미터

```json
{
    "email": "test@example.com",
    "password": "1234"
}
```

#### Response
#### 성공
```json
{
  "message": "로그인에 성공했습니다.",
  "code": 1000,
  "data": {
      "name": "홍길동",
      "email": "test@example.com"
  },
  "success": true
}
```
#### 실패
로그인에 실패했다면 HTTP 상태 코드와 함께 에러 객체가 돌아옵니다.

### 로그아웃
**POST /api/logout**
#### Request Body 파라미터
```json
{
    "email": "test@example.com",
    "password": "1234"
}
```
#### Response
#### 성공
```json
{
  "message": "로그아웃 되었습니다.",
  "code": 1002,
  "data": null,
  "success": true
}
```
#### 실패
로그아웃에 실패했다면 HTTP 상태 코드와 함께 에러 객체가 돌아옵니다.


### 로그인 확인
**GET /api/me**
#### Request Body 파라미터

```json
{
    "email": "test@example.com",
    "password": "1234"
}
```

#### Response
#### 성공
```json
{
  "message": "로그인 된 사용자입니다.",
  "code": 200,
  "data": {
      "name": "홍길동",
      "email": "test@example.com"
  },
  "success": true
}
```
#### 실패
로그인 확인을 실패했다면 HTTP 상태 코드와 함께 에러 객체가 돌아옵니다.

## API 에러코드

| 상태코드 | 에러 코드                 | 한글 에러 메시지            |
|:----:|:----------------------|:---------------------|
| 409  | `CONFLICT_MEMBER`     | 이미 사용 중인 이메일입니다.     |
| 401  | `UNAUTHORIZED_MEMBER` | 로그인 정보가 잘못되었습니다.     |
| 500  | `ENCRYPTION_ERROR`    | 암호화 처리 중 에러가 발생했습니다. |
| 500  | `INTERNAL_ERROR`      | 서버에서 에러가 발생했습니다.     |