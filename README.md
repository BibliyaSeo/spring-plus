# SPRING PLUS

## 기술 스택
- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Database**: H2 (개발 환경)
- **ORM**: Spring Data JPA, QueryDSL
- **Build Tool**: Gradle
- **IDE**: IntelliJ IDEA

## 진행 레벨

### Level. 1

#### 1. 코드 개선 퀴즈 - @Transactional의 이해
- 할 일 저장 API(`/todos`) 호출 시 에러 발생  
- 원인: `Connection is read-only` 에러 → 트랜잭션 설정 문제  
- 해결: `@Transactional`의 옵션을 수정하여 정상적으로 저장 가능하도록 코드 개선

#### 2. 코드 추가 퀴즈 - JWT의 이해
- `User` 엔티티에 `nickname` 컬럼 추가 (중복 허용)  
- JWT 토큰에서 닉네임을 꺼내 화면에 표시할 수 있도록 코드 수정

#### 3. 코드 개선 퀴즈 - JPA의 이해
- 할 일 검색 조건 확장
  - `weather` 조건으로 검색 가능 (옵션)
  - 수정일 기준 기간 검색 가능 (옵션)
- JPQL 활용, 필요 시 여러 쿼리 작성 가능

#### 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해
- `todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다()` 테스트 실패 → 수정 필요  
- 테스트 코드 보완하여 정상적으로 통과하도록 개선

#### 5. 코드 개선 퀴즈 - AOP의 이해
- `UserAdminController.changeUserRole()` 메소드 실행 전 AOP 동작 필요  
- `AdminAccessLoggingAspect` 코드를 수정하여 올바른 시점에 로그가 출력되도록 개선

---

### Level. 2

#### 6. JPA Cascade
- 할 일 생성 시, 생성한 유저가 담당자로 자동 등록되어야 함  
- `cascade` 옵션 활용하여 User와 Todo 연관관계 처리

#### 7. N+1 문제 해결
- `CommentController.getComments()` 호출 시 N+1 문제 발생  
- Fetch Join 등을 활용하여 N+1 문제 해결

#### 8. QueryDSL
- 기존 JPQL 기반 `findByIdWithUser` 메소드를 QueryDSL로 변경  
- N+1 문제 없이 동작하도록 구현

#### 9. Spring Security
- 기존 Filter & Argument Resolver 기반 인증 → Spring Security로 교체  
- JWT 기반 토큰 인증 유지  
- 접근 권한 및 사용자 권한(Spring Security Role) 정상 동작 보장

---

### Level. 3 (도전 기능)

#### 10. QueryDSL 검색 기능
- 새로운 API로 일정 검색 기능 구현
- 검색 조건:
  - 제목 (부분 검색 가능)
  - 생성일 범위 (최신순 정렬)
  - 담당자 닉네임 (부분 검색 가능)
- 반환 데이터:
  - 일정 제목
  - 담당자 수
  - 댓글 개수
- QueryDSL Projections 활용
- 페이징 처리 포함

#### 11. Transaction 심화
- 매니저 등록 요청 시 로그를 남기도록 기능 확장
- 로그 테이블 (`log`) 생성
  - 매니저 등록은 실패할 수 있어도, 로그는 반드시 남아야 함
  - `@Transactional` 옵션을 활용해 독립적으로 처리
- 로그에는 생성 시간 필수, 그 외 내용은 자유롭게 기록

---

## 실행 방법
1. 프로젝트 클론
2. 실행 ./gradlew bootRun
3. H2 console http://localhost:8080/h2-console
