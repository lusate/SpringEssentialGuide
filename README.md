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


<br>

---

<br>

## 🛡️ 로깅이란
시스템의 상태나 동작 정보를 시간 순으로 기록하는 것을 의미합니다.<br>
디버깅하거나 개발 이후 발생한 문제를 해결할 때 원인을 분석하는 데 꼭 필요한 요소입니다. <br>
slf4j를 통해 로그를 찍을 수 있습니다.

ERROR - 로직 수행 중에 시스템에 심각한 문제가 발생해서 애플리케이션의 작동이 불가능한 경우.

WARN - 시스템 에러의 원이이 될 수 있는 경고 레벨.

INFO - 애플리케이션의 상태 변경과 같은 정보 전달을 위해 사용.

DEBUG - 애플리케이션의 디버깅을 위한 메시지를 표시하는 레벨.

TRACE - DEBUG 레벨보다 더 상세한 메시지를 표현하기 위한 레벨.

<br>

---

<br>

## 🛡️ 테스트 코드
단위 테스트 : 테스트 대상의 범위를 기준으로 가장 작은 단위의 테스트 방식.<br>
일반적으로 메서드 단위로 테스트를 수행하게 되며, 메서드 호출을 통해 의도한 결괏값이 나오는지 확인하는 수준으로 테스트.

통합 테스트 : 모듈을 통합하는 과정에서의 호환성 등을 포함해 애플리케이션이 정상적으로 동작하는지 확인하기 위해 수행하는 테스트 방식.<br>
단위 테스트는 모듈을 독립적으로 테스트하는 반면 통합 테스트는 여러 모듈을 함께 테스트해서 정상적인 로직 수행이 가능한지를 확인.

<br>
<br>

### 테스트 코드 작성 요령
**Given-When-Then 패턴**

**Given** - 테스트를 수행하기 전에 테스트에 필요한 환경을 설정하는 단계.<br>
- 테스트에 필요한 변수를 정의하거나 Mock 객체를 통해 특정 상황에 대한 행동을 정의.

**When** - 테스트의 목적을 보여주는 단계.<br>
- 실제 테스트 코드가 포함되며, 테스트르 통한 결괏값을 가져오게 됨.

**Then** - 테스트의 결과를 검증하는 단계.<br>
- When 단계에서 나온 결괏값을 검증하는 작업을 수행.<br>
- 테스트를 통해 나온 결과에서 검증해야 하는 부분이 있다면 이 단계에 포함.

<br>

---

<br>

## 🛡️ JUnit
- 테스트 프레임워크로 단위 테스트를 위한 도구를 제공. <br>
- 통합 테스트도 지원하고 어노테이션 기반의 테스트 방식을 지원.<br>
- 그래서 몇 개의 어노테이션만으로 간편하게 테스트 코드를 작성 가능.<br>
- 단정문(assert)을 통해 테스트 케이스의 기댓값이 정상적으로 도출됐는지 검토 가능.

<br>

**@WebMvcTest(테스트 대상.class)** <br>
웹에서 사용되는 요청과 응답에 대한 테스트를 수행 가능.<br>
대상 클래스만 로드해 테스트를 수행하며, 만약 대상 클래스를 추가하지 않으면 @Controller, @RestController, @ControllerAdvice 등의 컨트롤러 관련 빈 객체가 모두 로드된다.<br>
@SpringBootTest 보다 가볍게 테스트하기 위해 사용.

<br>

**@MockBean**<br>
@MockBean은 실제 빈 객체가 아닌 Mock(가짜) 객체를 생성해서 주입하는 역할을 수행.<br>
@MockBean이 선언된 객체는 실제 객체가 아니기 때문에 실제 행위를 수행하지 않음.<br>
그래서 해당 객체는 개발자가 Mockito의 given() 메서드를 통해 동작을 정의해야 한다.

**@Test**<br>
테스트 코드가 포함돼 있다고 선언하는 어노테이션이며, Jnit Jupiter에서는 이 어노테이션을 감지해서 테스트 계획에 포함시킨다.

**@DisplayName**<br>
테스트 메서드의 이름이 복잡해서 가독성이 떨어질 경우 이 어노테이션을 통해 테스트에 대한 표현을 정의.

<br>

---

<br>

## 🛡️ Spring Security


<br>

---

<br>

## 🛡️ JWT


<br>

---

<br>