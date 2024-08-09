# 📔 SpringEssentialGuide
책을 통한 스프링 부트 공부

<br>

## 🛡️ REST API 명세를 문서화
- 명세란 해당 API가 어떤 로직을 수행하는지 설명하고 이 로직을 수행하기 위해 어떤 값을 요청하며, 이에 따른 응답값으로는 무엇을 받을 수 있는지를 정리한 자료입니다.
- API는 개발 과정에서 계속 변경되므로 작성한 문서도 주기적인 업데이트가 필요합니다. 또한 명세 작업은 번거롭고 오래 걸리기 때문에 등장한 것이 Swagger입니다.

1. gradle 이므로 의존성 추가합니다
```java
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'io.springfox:springfox-boot-starter:3.0.0'
implementation group: 'io.springfox', name: 'springfox-swagger-ui', version: '3.0.0'
```

2. Swagger 설정 코드 추가
`SwaggerConfiguration` 클래스 생성합니다.

3. 애플리케이션 실행한 후 `http://localhost:8080/swagger-ui.html`로 접속하면 Swagger 페이지가 출력됩니다.


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
<br>

## 🛡️ JUnit 생명주기
**@BeforeAll** : 테스트를 시작하기 전에 호출되는 메서드를 정의. (All은 전체 테스트 동작에서 처음과 마지막에만 각각 수행된다.)
**@BeforeEach** : 각 테스트 메서드가 실행되기 전에 동작하는 메서드를 정의.
**@AfterAll** : 테스트를 종료하면서 호출되는 메서드를 정의.
**@AfterEach** : 각 테스트 메서드가 종료되면서 호출되는 메서드를 정의

<br>

---

<br>

## 🛡️ Spring Security
- 인증 : 사용자가 누구인지 확인하는 단계. (로그인)
  - 로그인에 성공하면 애플리케이션 서버는 응답으로 사용자에게 토큰을 전달. 로그인 실패하면 토큰 전달 받지 못함.
- 인가 : 인증을 통해 검증된 사용자가 애플리케이션 내부의 리소스에 접근할 때 사용자가 해당 리소스에 접근할 권리가 있는지를 확인하는 과정.
  - 로그인을 했는데 게시판 접근 등급으로 인해 접근이 허가되거나 거부되는 것.

**사용자가 애플리케이션에 접근하여 수행하려는 모든 작업과 접근하려는 데이터가 리소스에 해당**

**스프링 시큐리티**
- 애플리케이션의 인증, 인가 등의 보안 기능을 제공하는 스프링 하위 프로젝트 중 하나.
- 서블릿 필터를 기반으로 동작하며, DispatcherServlet이 배치 되어 있다.
- 사용하고자 하는 필터체인을 서블릿 컨테이너의 필터 사이에서 동작시키기 위해 DelegatingFilterProxy를 사용.
- 서블릿 컨테이너의 생명주기와 스프링 애플리케이션 컨텍스트 사이에서 다리 역할을 수행.
- DelegatingFilterProxy는 필터체인 프록시를 내부에 가지고 있다.
- 필터체인 프록시는 스프링 부트의 자동 설정에 의해 자동 생성된다.

<br>

![img.png](img.png)

1. 클라이언트로부터 요청을 받으면 서블릿 필터에서 SecurityFilterChain으로 작업이 위임되고 그중 UsernamePasswordAuthenticationFilter에서 인증을 처리.<br>
2. AuthenticationFilter는 요청 객체에서 username과 password를 추출해서 토큰을 생성. (로그인 시작)! <br>
3. 이후 AuthenticationManager에게 토큰을 전달. AuthenticationManager는 인터페이스이며, 일반적으로 사용되는 구현체는 ProvideManager.<br>
4. ProviderManager는 인증을 위해 AuthenticationProvider로 토큰을 전달. <br>
5. AuthenticationProvider는 토큰의 정보를 UserDetailService에 전달. <br>
6. **UserDetailService는 전달받은 정보를 통해 데이터베이스에서 일치하는 사용자를 찾아 UserDetails 객체를 생성.** <br>
7. **생성된 UserDetails 객체는 AuthenticationProvider로 전달되며, 해당 Provider에서 인증을 수행하고 성공하게 되면 ProviderManager로 권한을 담은 토큰을 전달.** <br>
8. **ProviderManager는 검증된 토큰을 AuthenticationFilter로 전달.** <br>
9. AuthenticationFilter는 검증된 토큰을 SecurityContextHolder에 있는 SecuityContext에 저장됨.

<br>

---

<br>

## 🛡️ JWT
- JSON Web Token으로 당사자 간에 정보를 JSON 형태로 안전하게 전송하기 위한 토큰.
- JWT는 URL로 이용할 수 있는 문자열로만 구성돼 있으며, 디지털 서명이 적용돼 있어 신뢰할 수 있다.
- 주로 서버와의 통신에서 권한 인가를 위해 사용된다.
- URL에서 사용할 수 있는 문자열로만 구성돼 있기 때문에 HTTP 구성요소 어디든 위치 가능.

<br>

**JWT는 점(.) 으로 구분된 세 부분으로 구성된다.**
**헤더.내용.서명**<br>

**헤더 : 검증과 관련된 내용을 담음. -> 두가지 정보로 alg와 typ를 가지고 있음.**
alg 속성에는 해싱 알고리즘을 지정. 해싱 알고리즘은 보통 SHA256 또는 RSA를 사용하며, 토큰을 검증할 때 사용되는 서명 부분에서 사용.<br>
typ 속성에는 토큰의 타입을 지정.
<br>

**이렇게 완성된 헤더는 Base64Url 형식으로 인코딩돼 사용됨.**

<br>

**내용 : 토큰에 담는 정보를 포함. -> 이고셍 포함된 속성들은 클레임이라 하며, 크게 3가지로 분류.**
등록된 클레임, 공개 클레임, 비공개 클레임<br>

JWT 내용 예시
```json
{
  "sub": "wikibooks payload",
  "exp": "1602076408",
  "userId": "wikibooks",
  "username": "flature"
}
```

**서명 : 인코딩된 헤더, 인코딩된 내용, 비밀키, 헤더의 알고리즘 속성값을 가져와 생성됨.**

<br>

## 🛡️ JWT 디버거 사용.
웹 브라우저인 https://jwt.io/#debugger-io 로 가면 쉽게 JWT를 생성해 볼 수 있음.

<br>

---

<br>

## 🛡️스프링 시큐리티와 JWT 적용.
**UserDetails**
- getAuthorities() : 계정이 가지고 있는 권한 목록을 리턴.
- getPassword() : 계정의 비밀번호를 리턴.
- getUsername() : 계정의 이름을 리턴. -> 일반적으로 아이디를 리턴.
- isAccountNonExpired() : 계정이 만료됐는지 리턴. (true는 만료되지 않았다는 의미.)
- isAccountNonLocked() : 계정이 잠겨있는지 리턴. (true는 잠기지 않았다는 의미)
- isCredentialNonExpired() : 비밀번호가 만료됐는지 리턴. (true는 만료되지 않았다는 의미)
- isEnabled() : 계정이 활성화 되어 있는지를 리턴. (true가 활성화 상태)

<br>

---

<br>

## 🛡Spring Security, JWT 실습
`openssl rand -hex 32` 랜덤으로 암호 키를 생성한 후, 생성된 secret key를 application.yml에 설정.
해당 키는 토큰의 암호화 복호화에 사용됨. HS256 알고리즘을 사용하기 위해 32글자 이상으로 설정.

<br>

<br>

### 🌀generateToken()
인증 객체를 기반으로 Access Token과 Refresh Token 생성.<br>
Access Token : 인증된 사용자의 권한 정보와 만료 시간을 담음.<br>
Refresh Token : Access Token의 갱신을 위해 사용됨.<br>

<br>

### 🌀getAuthentication()
주어진 Access Token을 복호화하여 사용자의 인증 정보를 생성.<br>
토큰의 Claims에서 권한 정보를 추출하고, User 객체를 생성하여 Authentication 객체로 반환<br>
"auth" 클레임은 토큰에 저장된 권한 정보를 나타냄.<br>
가져온 권한 정보를 SimpleGrantedAuthority 객체로 변환하여 컬렉션에 추가<br>
UserDetails 객체를 생성하여 주체와 권한 정보, 기타 필요한 정보를 설정.<br>
UsernamepasswordAuthenticationToken 객체를 생성하여 주체와 권한 정보를 포함한 인증 객체를 생성.<br>

<br>

### 🌀validateToken()
주어진 토큰을 검증하여 유효성 확인
Jwts.parseBuilder를 사용하여 토큰의 서명 키를 설정하고 예외 처리를 통해 토큰의 유효성 여부를 판단.<br>
IllegalArgumentException이 발생하는 경우<br>
- 토큰이 올바른 형식이 아니거나 클레임이 비어있는 경우 등에 발생.<br>
claim.getSubject()는 주어진 토큰의 클레임에서 "sub" 클레임의 값을 반환
- 토큰의 주체를 나타냄. ex) 사용자의 식별자나 이메일 주소

<br>

### 🌀parseClaims()
클레임(Claims): 토큰에서 사용할 정보의 조각.<br> 
주어진 Access Token을 복호화하고, 만료된 토큰인 경우에도 Claims 반환.<br>
parseClaimsJws() 메서드가 JWT 토큰의 검증과 파싱을 모두 수행.<br>

<br>

### 🌀doFilter()
resolveToken 메서드를 사용하여 요청 헤더에서 JWT 토큰을 추출<br>
JwtTokenProvider의 validateToken() 메서드로 JWT 토큰의 유효성 검증<br>
토큰이 유효하면 JwtTokenProvider의 getAuthentication() 메서드로 인증 객체 가져와서 SecurityContext에 저장.<br>
- 요청을 처리하는 동안 인증 정보가 유지됨.<br>

chain.doFilter()를 호출하여 다음 필터로 요청을 전달.<br>

<br>

### 🌀실행
"members/sign-in" -> 모든 사용자에게 허용<br>
"members/test" -> USER 권한을 가진 사용자에게 허용<br>

Postman으로 DB에 저장한 회원 정보를 body에 담아서 "members/sign-in"으로 요청.<br>
그러면 성공적으로 Access Token 발급<br>
**발급받은 Access Token을 header에 넣어 "members/test"로 요청**

<br>

### 🌀 

<br>
<br>

### 로그인 구현 시 문제 발생
**문제1** <br>
entity로 User를 두고 했는데 `UserDetails principal = new User(claims.getSubject(), "", authorities);` 코드가 있어서 혼란이 옴.<br>
new User는 엔티티가 아니라 Security에서 import를 받아서 사용하는 라이브러리일 뿐.

<br>
<br>

**문제2** <br>
`Cannot resolve configuration property 'jwt.secret.key'` 
문제될 게 따로 없는데도 secret.key가 제대로 인식되지 않는 문제가 발생.<br>
알고보니 내가 `application.yml` 이 아닌 `application-db.yml`로 해놔서 문제였던 것.<br>
jwt 하는 부분은 이름 다르게 yml을 짜서 하고 싶었던 마음에 바꿔버렸다.<br>

```
spring:
  config:
    activate:
      on-profile: dev
```
<br>
이렇게 해주면 완성된다.

<br>
<br>

**문제3** <br>
JWT 헤더를 읽지 못하는 문제
```java
private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTH_HEADER);

    // null인 이유 : 헤더 누락 -> 클라이언트에서 요청을 보낼 때 Authorization 헤더를 포함하지 않음.
    log.info("headerAuth = {}", bearerToken);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
    }
    return null;
}
```

SecurityConfig 파일에서 코드를 잘못 다룸. -> 요청 자체에 문제가 생김.<br>
헤더를 못 읽는 건 말이 안됨. <br>
알고 보니 SecurityConfig 파일에서 SecurityFilterChain filterChain(HttpSecurity http) 를 잘못 작성 <br>

<br>

**문제4** <br>
작성 수정 후 다시 로그인 시도했는데 localhost:8080에서 "자격 증명 실패했습니다 " <br>
Member entity를 보니 구현받은 UserDetails에서 get으로 아이디와 비번을 받지 않아서 "Empty Bcrypt"가 발생 <br>
또한 데이터베이스에 있는 멤버의 비밀번호가 BcryptEnding 되지 않은 상태여서 실패. <br>
회원가입 시 비밀번호가 인코딩 된 상태로 DB에 들어가야 하는데 디코딩된 비번이 들어가 있어서 자격 증명이 실패한 것 <br>
<br>

인코딩된 비밀번호를 넣어서 다시 시도했더니 제대로 accessToken과 refreshToken이 생성됨.<br>
```json
{
  "grantType": "Bearer",
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIkMmEkMTAkZERDWmg5azhsT0ovNUNCdjk5RXBldUdsdkwxUUw5Qk1RRXBJUzVHUUo2R1JUa3BZQUN5L0siLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzIzMTc2MDk3fQ.bInvyajWQRBSM0qq9-dIcbanaX222wTd_pWYksSf75g",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjMxNzYwOTd9.78x0DDAtp91NyS1wuApbToVcohZiYqbRYpVYUVJAUaM"
}
```
<br>
test2 API를 호출하면 토큰이 만료되지 않는 이상 응답값으로 username이 출력됨.

<br>

---

<br>

## 🛡로그인 이후 회원가입




<br>

---

<br>