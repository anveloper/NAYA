
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
