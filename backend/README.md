## 백엔드 폴더구조

root

├── exception 

│   └── OOException.java

├── advice 

│   └── OOAdvice.java

├── api

│   └── OOApi.java

├── config 

│   └── OOConfig.java

├── controller 

│   └── OOController.java

├── dto 

|   ├── OODto 

|   |   ├── OOAuthRequest.java

|   |   ├── OOAuthResponse.java

|   |   ├── OOFindAllResponse.java

|   |   ├── OOFindResponse.java

|   |   ├── OOSaveRequest.java

|   |   ├── OOSaveResponse.java

|   |   ├── OOUpdateRequest.java

|   |   ├── OOUpdateResponse.java

|   |   └── OODeleteResponse.java

├── entity 

│   └── OO.java

├── repository 

│   └── OORepository.java

├── service 

|   ├── OOService.java

│   └── OOServiceImpl.java

└── NayaApplication.class



---------------------------------------------------------------------------------------------------------------------------------------

## 패키지 설명

1. exception : 예외 처리 자바 파일 저장
   ![exception](/uploads/1e23028b151d48e238d9cda138d9f25e/exception.PNG)

2. advice :  예외 처리 자바 파일 저장( exception 내 자바 파일을 연결)

   ![advice](/uploads/57e38cb21b564725c417d6cc7ef14e03/advice.PNG)

3. api : 외부 api 연동 자바 파일 저장

   ![api](/uploads/31e34add2afcee4b83515d7be0b01820/api.PNG)

4. config : 환경 설정 파일 저장

   ![config](/uploads/792cd0ab63d315e0dda6be6aa68925c8/config.PNG)

5. controller :  요청을 처리하는 파일 저장

   ![controller](/uploads/5954528e95c42bc316b99364fa6c2429/controller.PNG)

6. dto : DB에서 원하는 형태로 데이터를 CRUD하는 객체 파일 저장

   ![dto](/uploads/b4a02e4cc87e6c79e0cd2581ea83b99e/dto.PNG)

7. entity : DB 테이블과 매칭되는 객체 파일 저장

   ![entity](/uploads/e6fbfe4d36a65cf338a09dcfd7ece965/entity.PNG)

8. repository : Jpa 기본 메서드를 extends 하는 인터페이스 파일 저장

   ![repository](/uploads/d70615aa72b2968e66ee72411e2b9630/repository.PNG)

9. service : 메서드를 선언하는 인터페이스 파일, 실제 구현하는 자바 파일 저장

   ![service](/uploads/754fd0b9a60bb07c7514a3c7d6900a82/service.PNG)