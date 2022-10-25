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

   ![image-20221019145023106](./images/exception.png)

2. advice :  예외 처리 자바 파일 저장( exception 내 자바 파일을 연결)

   ![image-20221019145241064](./images/advice.png)

3. api : 외부 api 연동 자바 파일 저장

   ![image-20221019151933756](./images/api.png)

4. config : 환경 설정 파일 저장

   ![image-20221019145757549](./images/config.png)

5. controller :  요청을 처리하는 파일 저장

   ![image-20221019145922350](./images/controller.png)

6. dto : DB에서 원하는 형태로 데이터를 CRUD하는 객체 파일 저장

   ![image-20221019150214438](./images/dto.png)

7. entity : DB 테이블과 매칭되는 객체 파일 저장

   ![image-20221019150402069](./images/entity.png)

8. repository : Jpa 기본 메서드를 extends 하는 인터페이스 파일 저장

   ![image-20221019150554535](./images/repository.png)

9. service : 메서드를 선언하는 인터페이스 파일, 실제 구현하는 자바 파일 저장

   ![image-20221019150832286](./images/service.png)