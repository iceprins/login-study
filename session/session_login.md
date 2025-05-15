# Session Login Project

세션(session) 방식의 인증 흐름을 직접 구현한 학습용 프로젝트입니다.
회원가입, 로그인, 로그아웃 기능을 포함하며, 로그인 상태를 세션을 통해 유지합니다.

1차적으로 필수적인 기능 위주로 구현하고, 조금씩 리팩토링 해볼 예정입니다.

---

## 프로젝트 개요

| 항목 | 설명 |
|------|------|
| 목적 | 세션 기반 인증 방식 학습 및 구현 |
| 기술 스택 | Spring Boot, Spring MVC, JPA, MySQL |
| 인증 방식 | `HttpSession` 기반 세션 로그인 |
| 테스트 도구 | Postman |

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

## DB 테이블 구조

| 컬럼       | 타입           | 설명         |
| -------- | ------------ | ---------- |
| id       | bigint       | 사용자 ID(PK) |
| name     | varchar(100) | 이메일        |
| email    | varchar(100) | 비밀번호       |
| password | varchar(100) | 사용자 이름     |

## API 명세

### 회원가입
- POST /api/signup
- Request Body 파라미터

```json
{
	"email": "test@example.com",
	"password": "1234",
	"name": "홍길동"
}
```

- Response
```json
{
	"isSuccess": true,
	"code": 200,
	"message": "회원가입 성공",
	"data": null
}
```
