# 콘텐츠 플랫폼 서비스

## 🗂️ Summary
- JWT 기반 사용자 인증
- 콘텐츠를 조회, 구매, 삭제 및 콘텐츠 이벤트 기간 등록을 통한 무료/유료 전환
- 조회 수 및 구매 수에 따른 인기 콘텐츠 조회 Pagination 처리
- 비동기 이벤트 처리 : 대용량 트래픽을 고려하여 콘텐츠 조회 이력 저장 및 삭제 




## 🛠️ 주요 기술
- Java 17
- SpringBoot 3.3.4
- SpringDataJPA
- SpringSecurity
- JUnit
- Swagger
- H2 DB


## 실행 방법
```shell
./gradlew bootRun
```


## Swagger 

URL : http://localhost:8080/swagger-ui/index.html

<img width="821" alt="swagger_index" src="https://github.com/user-attachments/assets/118af622-94ab-47d4-a947-481136fb50fa">
<img width="821" alt="swagger_signup" src="https://github.com/user-attachments/assets/c6bd02f1-6e87-41ea-a097-c55860c66791">
<img width="821" alt="swagger_signin" src="https://github.com/user-attachments/assets/79712bb8-7619-4331-900c-c85d715b88b4">
<img width="821" alt="swagger_authorizing" src="https://github.com/user-attachments/assets/9e27e0fd-3d67-4e6d-beec-2fb02a1188eb">
<img width="821" alt="swagger_available_authorization" src="https://github.com/user-attachments/assets/3acb5aab-81b5-4219-8d05-9262a0ae178d">
<img width="821" alt="swagger_purchase" src="https://github.com/user-attachments/assets/6d20970d-09e1-4f1f-bdf2-036f7454c868">


## 📑 API 엔드포인트 및 상세 설명
### 인증
- `POST /api/auth/signup` : 사용자 회원가입
- `GET /api/auth/signin` : 사용자 로그인, JWT 토큰 반환

### 콘텐츠
- `POST /api/content/` : 콘텐츠 등록
- `GET /api/content/{contentId}` : 콘텐츠 정보 조회 및 비동기 이벤트 기반으로 조회 내역 저장 
- `DELETE /api/content/{contentId}` : 콘텐츠 삭제(인증된 사용자만 가능), 비동기 이벤트 기반으로 조회 이력도 함께 삭제
- `POST /api/content/event` : 콘텐츠 이벤트 등록(인증된 사용자만 가능)
- `GET /api/content/{contentId}/history` : 콘텐츠별 조회한 사용자 및 조회일자 히스토리 조회
- `GET /api/contentview/top` : 조회수를 기준으로 인기 콘텐츠 조회 및 Pagination 제공

### 구매
- `POST /api/purchase` : 이벤트 기간에 해당하는 경우 무료, 해당하지 않는 경우 유료로 콘텐츠 구매 (인증된 사용자만 가능)
- `GET /api/purchase/top` : 구매수를 기준으로 인기 콘텐츠 조회 및 Pagination 제공



## 💡 고려사항 및 해결방안
### 1. 대용량 트래픽 고려한 옵저버 패턴과 비동기 이벤트 
- 옵저버 패턴 사용 : 이벤트 처리 로직이 다른 시스템 컴포넌트와 독립적으로 동작하도록 결합도를 낮추고 응집도를 높임으로써 유지 보수을 향상시켰습니다.
  
- 비동기 처리 : 콘텐츠 조회/삭제가 발생할 때 즉시 조회 내역을 저장/삭제하는 대신, 이벤트를 발생시키고 해당 이벤트를 비동기로 처리함으로써 사용자는 콘텐츠를 빠르게 조회하고, 서비스의 응답 시간을 줄여 성능을 향상시켰습니다.


### 2. 데이터 대용량으로 축적되는 케이스를 고려한 연관관계 미설정 
- 콘텐츠 조회 내역 도메인 특성상 매우 자주 추가되거나 대용량으로 축적되는 케이스를 고려하여 이벤트 기반 연관관계를 생략하고 비동기 처리를 통해 조회 내역을 독립적으로 관리함으로써 성능 최적화하였습니다.
- 이벤트 기반 비동기 처리(예: Kafka, RabbitMQ)를 이용해 별도의 통계 시스템에서 조회 내역을 처리에 용이


### 3. 확장성을 고려한 Pagination
- 인기 순위 작품 10개 사이즈 고정 조회 아닌, size 와 page 요청 파라미터를 추가 및 Pageable을 활용한 Pagination 기능을 구현하여 인기 작품 조회 API의 확장성을 높였습니다.

### 4. 데이터 무결성 보장
- 객체의 불변성 유지 : 객체가 생성된 이후에는 상태를 변경할 수 없도록 Setter 를 생략하고 빌더 패턴을 활용하여 작업의 영향도를 최소화하였습니다.
