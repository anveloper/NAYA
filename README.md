![Footer__1_](./images/README/Footer.png)
  
[TOC]





------------------------------------------

# 1. NAYA 소개
  ### **NA(나)를 소개하는 가장 쉬운 방법 NAYA 소개 카드 관리 서비스**
![logo_dark](./images/README/logo.png)
  

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
  git clone https://lab.ssafy.com/s07-final/S07P31B104.git
  ```
    
2. **[도커 설치](https://docs.docker.com/get-docker/) 및 도커 [컴포즈 설치](https://docs.docker.com/compose/install/)**
  
3. **Dockerfile 및 docker-compose.yml작성**
  
   - frontend Dockerfile
     ~~~docker
      FROM node:16 as build-stage

      # 앱 디렉터리 생성
      WORKDIR /jenkins/workspace/NAYA/frontend
      ENV TZ Asia/Seoul

      # 앱 의존성 설치
      # 가능한 경우(npm@5+) package.json과 package-lock.json을 모두 복사하기 위해
      # 와일드카드를 사용
      COPY package*.json ./

      RUN npm install --save --legacy-peer-deps
      # 프로덕션을 위한 코드를 빌드하는 경우
      # RUN npm ci --only=production

      # 앱 소스 추가
      COPY . .

      RUN npm run build

      FROM nginx:stable-alpine as production-stage
      COPY --from=build-stage /jenkins/workspace/NAYA/frontend/build /usr/share/nginx/html
      COPY --from=build-stage /jenkins/workspace/NAYA/frontend/deploy_conf/nginx.conf /etc/nginx/conf.d/default.conf

      EXPOSE 3000
      CMD ["nginx", "-g", "daemon off;"]
     ~~~

   - backend dockerfile
     ~~~docker
      FROM openjdk:8
      WORKDIR /var/jenkins_home/workspace/NAYA/backend/naya
      ENV TZ Asia/Seoul
      #VOLUME /tmp
      #ARG JAR_FILE=build/libs/*.jar
      #COPY {JAR_FILE} app.jar
      #RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
      COPY build/libs/NAYA-0.0.1-SNAPSHOT.jar app.jar
      ENTRYPOINT ["java","-jar","app.jar"]
     ~~~

   - nginx.conf 파일
     ~~~bash
      upstream backend{
        ip_hash;
        server {내부 로컬 주소:포트번호}; ex)172.26.14.37:8080;
      }

      map $http_upgrade $connection_upgrade {
        default upgrade;
        ''      close;
      }

      server {
              listen  80;
              server_name     {도메인 주소}; ex) k7b104.p.ssafy.io;
              location / {
                      return 301 https://$host$request_uri;
              }
              #location /naya/api {
              #       proxy_name http://localhost:8080/naya/api;
              #}
      }

      server {
          listen       443 ssl;
      #    listen  [::]:443;
          server_name  {도메인 주소}; ex) k7b104.p.ssafy.io;

          access_log  /var/log/nginx/host.access.log  main;

        ssl_certificate /etc/letsencrypt/live/{도메인 주소}/fullchain.pem;
      # ex)   ssl_certificate /etc/letsencrypt/live/k7b104.p.ssafy.io/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/{도메인 주소}/privkey.pem;
      # ex)   ssl_certificate_key /etc/letsencrypt/live/k7b104.p.ssafy.io/privkey.pem;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3 SSLv3;
        ssl_ciphers ALL;

          location / {
              root   /usr/share/nginx/html;
              index  index.html index.htm;
              error_page 405 = $uri;
              proxy_redirect off;
              try_files $uri.html $uri $uri/ /index.html;

              charset utf-8;
              proxy_http_version 1.1;
              proxy_set_header Upgrade $http_upgrade;
              proxy_set_header Connection "upgrade";
              proxy_set_header Host $host;
              proxy_set_header X-Real-IP $remote_addr;
              proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
              proxy_set_header X-Forwarded-Proto $scheme;
              proxy_set_header X-Nginx-Proxy true;
          }

              location /api/ {
              proxy_pass http://backend/;
              proxy_redirect     off;
              charset utf-8;
              proxy_http_version 1.1;
              proxy_set_header Upgrade $http_upgrade;
              proxy_set_header Connection "upgrade";
              proxy_set_header Host $host;
              proxy_set_header X-Real-IP $remote_addr;
              proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
              proxy_set_header X-Forwarded-Proto $scheme;
              proxy_set_header X-Nginx-Proxy true;
          }
     ~~~

   - docker-compose.yml

     ~~~yml
      version: '3'
        services:
            jenkins:
                image: jenkins/jenkins:lts
                container_name: jenkins
                volumes:
                    - /var/run/docker.sock:/var/run/docker.sock
                    - /jenkins:/var/jenkins_home
                ports:
                    - "9090:8080"
                privileged: true
                user: root

     ~~~

4. **도커 컨테이너 실행**
  - mysql 이미지 실행하기

     ~~~bash
      # MySQL 컨테이너 생성
      docker run --name naya_DB -e MYSQL_ROOT_PASSWORD -d mysql

      # Dump 넣기
      docker cp {덤프파일 위치} naya_DB:/home

      # naya_DB 내부 접속
      docker exec -it naya_DB bash

      # Dump import
      mysql -hlocalhost -uroot -p{password} < /home/{덤프파일명}
     ~~~
  
    - 또는 3306포트로 mySQL 접속하여 naya 스키마 생성

  - docker-compose 실행

     ~~~bash
     sudo docker-compose up -d
     ~~~
  
  - 젠킨스에 접속해 빌드 설정

    - Build Steps - Excute shell 추가

     ~~~bash
      docker image prune -a --force
      mkdir -p /var/jenkins_home/images
      cd /var/jenkins_home/workspace/NAYA/frontend/
      docker build -t react .
      docker save react > /var/jenkins_home/images/react.tar

      cd /var/jenkins_home/workspace/NAYA/backend/NAYA/
      chmod +x ./gradlew
      ./gradlew clean build
      docker build -t springboot .
      docker save springboot > /var/jenkins_home/images/springboot.tar

      ls /var/jenkins_home/images
    ~~~
      
    - 빌드 후 조치 추가

    ~~~bash
      sudo docker load < /jenkins/images/react.tar
      sudo docker load < /jenkins/images/springboot.tar

      if (sudo docker ps -a | grep "react"); then sudo docker stop react; fi
      if (sudo docker ps -a | grep "springboot"); then sudo docker stop springboot; fi

      sudo docker run -it -d --rm -p 80:80 -p 443:443 -v /home/ubuntu/certbot/conf:/etc/letsencrypt/ -v /home/ubuntu/certbot/www:/var/www/certbot --name react react
      echo "Run frontend"
      sudo docker run -it -d --rm -p 8080:8080 --name springboot springboot
      echo "Run backend"
    ~~~
    

  
5. **작동 확인**

  - 실행 중인 컨테이너 조회

     ~~~bash
     docker ps
     ~~~

--------------------------

  
  

# 4. ⭐ 주요 기능
------------------------------------------------------

  1. 


    

  2. 
    - 

    - 

    - 

      - 



  3. 

    - 

      - 

    - 



  4. 

    - 

    - 

    - 

  

  5. 

    - 

    - 


  6. 

    - 

    - 

    - 



--------------------------



# 5. 🔍 사용 기술
------------------------------------------------------
  - 
    - 



  - 
    - 


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
