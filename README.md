![Footer__1_](./images/README/Footer.png)
  
[TOC]





------------------------------------------

# 1. !NAYA 소개
  ### **NA(나)를 소개하는 가장 쉬운 방법 NAYA 소개 카드 관리 서비스**
[logo_dark](./images/README/logo.png)
  

------------------------------------------------------
  
# 2. 🔍 개발 환경
  
## 2-1. 환경 설정
    
  ### **👨‍💻 Front-end**
    
    - 

    - 

    - 

    - 

  ### **👨‍💻 Back-end**
    
    - 
      
    - 

    - 

    - 

    ※ [설치 파일](./back/pythonProject/requirements.txt/)
    
  ### **👩‍💻 CI/CD**  
    
    - 
      
    - 
      
    - 
      
    - 
      
  

## 2-2. 서비스 아키텍처
  
![PT_35](./images/README/)
  
------------------------------------------------------
  

# 3. 📘 실행 방법
  
## docker를 활용한 실행 가이드
  
1. **git clone**
  
  ```bash
  git clone https://lab.ssafy.com/s07-bigdata-recom-sub2/S07P22B205.git
  ```
    
2. **[도커 설치](https://docs.docker.com/get-docker/) 및 도커 [컴포즈 설치](https://docs.docker.com/compose/install/)**
  
3. **Dockerfile 및 docker-compose.yml작성**
  
   - nginx Dockerfile
     ~~~docker
      FROM node:16.17.0 as builder
      # 작업 폴더로 소스 파일 복사
      COPY {git 폴더}/front/sharkshark /home/react
      WORKDIR /home/react
      # node 패키지 설치 후 빌드
      RUN npm install
      RUN npm run build

      FROM nginx
      # nginx 설정 복사
      COPY {nginx.conf 위치} /etc/nginx
      # 빌드 파일 복사
      COPY --from=builder /home/react/build /home/build
      # 포트 개방
      EXPOSE 80
      CMD ["nginx", "-g", "daemon off;"]
     ~~~

   - fastapi dockerfile
     ~~~docker
      FROM python:3.9
      # 작업 폴더로 실행 폴더 복사
      WORKDIR /code
      COPY {git 폴더}/back/pythonProject /code
      # 파이썬 패키지 설치 후 실행
      RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt
      CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
     ~~~

   - nginx.conf 파일
     ~~~bash
      user nginx;
      worker_processes auto;
      events {
        worker_connections 1024;
      }
      http{
        include mime.types;
        access_log /var/log/nginx/access.log;
        error_log /var/log/nginx/error.log;
        
        server {
          // 포트 지정
          listen 80;
          listen [::]:80;
          
          // 프론트 빌드파일 경로설정
          location / {
            root	/home/build;
            index	index.html index.htm;
            try_files 	$uri $uri/ /index.html;
          }
          // 백엔드 api 요청 포워딩
          location /api/{
            proxy_pass http://172.17.0.1:8000/;
          }
        }
      }
     ~~~

   - docker-compose.yml

     ~~~yml
      version: '3'
      services:
        nginx:
          build:
            context: .
            dockerfile: {nginx dockerfile 이름}
          ports:
            - 80:80
        api:
          build:
            context: .
            dockerfile: {fastapi dockerfile 이름}
          ports:
            - 8000:8000
          extra_hosts:
            - "localhost:host-gateway"

     ~~~

4. **도커 컨테이너 실행**
   - mysql 이미지 실행하기

     ~~~bash
      # mysql 이미지 가져오기
      docker pull mysql

      # 컨테이너 실행
      docker run --name mysql -e MYSQL_ROOT_PASSWORD={password} -d -p 3306:3306 mysql
     ~~~
  
   - 3306포트로 mySQL 접속하여 b205 스키마 생성

   - docker-compose 실행

     ~~~bash
     docker compose up -d --build
     # 혹은
     docker-compose up -d --build
     ~~~


5. **작동 확인**

  - 실행 중인 컨테이너 조회

     ~~~bash
     docker ps
     ~~~
    
  - mySQL 접속하여 DB [덤프 파일](/exec/sharkshark_dp_dump.zip) 실행

--------------------------

  
  

# 4. 🦈 주요 기능
------------------------------------------------------
  ![PT_8](./images/README/)
  ![PT_9](./images/README/)

  1. 

  ![회원가입_연동](./images/README/)
  ![로그인](./images/README/)
    

  2. 
    - 

    - 

    - 

      - 

  ![실력별_풀이유형별_추천](./images/README/)
  ![주요알고리즘_추천](./images/README/)

  3. 

    - 

      - 

    - 
  
  ![라이벌추천_등록_해지](./images/README/)
  ![라이벌추천_비교](./images/README/)


  4. 

    - 

    - 

    - 

    - 

  ![모의테스트_시작](./images/README/)
  ![모의테스트_제출확인](./images/README/)
  ![모의테스트_결과](./images/README/)
  

  5. 

    - 

    - 
  ![블로그계정설정](./images/README/)
  ![블로그_포스팅](./images/README/)
  ![블로그_포스팅2](./images/README/)

  6. 

    - 

    - 

    - 

  ![알고리즘실력분석](./images/README/)
  ![티어로드맵](./images/README/)
  ![유사사용자분석](./images/README/)


--------------------------



# 5. 🔍 사용 기술
------------------------------------------------------
  - 
    - 

  ![라이벌_추천_알고리즘](./images/README/)

  - 
    - 

  ![문제추천_알고리즘](./images/README/)
--------------------------



# 6. 🛡 배포
------------------------------------------------------
  - 
    - 
    - 
    - 
  - 
    - 
    - 
  
  
--------------------------
  
  

# 7. 📁 설계 문서
------------------------------------------------------
    
  ## 6-1. ERD

  ![ERD](./images/README/)


  ## 6-2. Design System

  ![DesignSystem](./images/README/)

  ![DESIGN_COMPONENT](./images/README/)


  ## 6-3. Design

  ![DesignConcept](./images/README/)

    - 브랜딩 컨셉
      - 
      - 

    
  

--------------------------



# 8. 🖊 Cooperation&Promises
------------------------------------------------------
  
  ## 7-1. Tools

    - Git

    - Jira

    - Notion

    - Mattermost

    - Webex
      
      
  ![PT_17](./images/README/)
    
--------------------------



# 8. 👨‍👩‍👧‍👦 ![logo_dark](./images/README/logo_dark.png) 팀원 소개
------------------------------------------------------
  
  ![PT_37](./images/README/)

![Footer](./images/README/)
