# 📔 SpringEssentialGuide
책을 통한 스프링 부트 공부

<br>

## 🛡️ REST API 명세를 문서화
- 명세란 해당 API가 어떤 로직을 수행하는지 설명하고 이 로직을 수행하기 위해 어떤 값을 요청하며, 이에 따른 응답값으로는 무엇을 받을 수 있는지를 정리한 자료입니다.
- API는 개발 과정에서 계속 변경되므로 작성한 문서도 주기적인 업데이트가 필요합니다. 또한 명세 작업은 번거롭고 오래 걸리기 때문에 등장한 것이 Swagger입니다.

1. gradle 이므로 의존성 추가합니다
```java
implementation group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.9.2'
implementation group: 'io.springfox', name: 'springfox-swagger2', version: '2.9.2'
```

2. Swagger 설정 코드 추가
`SwaggerConfiguration` 클래스 생성합니다.

3. 애플리케이션 실행한 후 `http://localhost:8080/swagger-ui.html`로 접속하면 Swagger 페이지가 출력됩니다.

4. 실행 화면
5. 

<br>

---

<br>

## 🛡️ 로깅이란


<br>

---

<br>

## 🛡️ 테스트 코드
