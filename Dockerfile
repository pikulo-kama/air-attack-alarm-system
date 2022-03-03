FROM maven:3.6.3-jdk-11 AS build

ARG POSTGRES_URL
ARG POSTGRES_USER
ARG POSTGRES_PASS
ARG REDIS_HOST
ARG REDIS_PORT
ARG REDIS_PASS
ARG FRONTEND_URL
ARG TELEGRAM_BOT_TOKEN
ARG TELEGRAM_BOT_USERNAME
ARG TELEGRAM_BOT_WEBHOOK_PATH
ARG JKS_KEYSTORE_NAME
ARG JKS_KEY_ALIAS
ARG JKS_KEY_PASSWORD
ARG JKS_KEYSTORE_PASSWORD
ARG JKS_TRUSTSTORE_NAME
ARG JKS_TRUSTSTORE_PASSWORD

RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package -Dspring.datasource.url=${POSTGRES_URL} \
                                 -Dspring.datasource.username=${POSTGRES_USER} \
                                 -Dspring.datasource.password=${POSTGRES_PASS} \
                                 -Dspring.datasource.redis.host=${REDIS_HOST} \
                                 -Dspring.datasource.redis.port=${REDIS_PORT} \
                                 -Dspring.datasource.redis.password=${REDIS_PASS} \
                                 -Dtelegram.token=${TELEGRAM_BOT_TOKEN} \
                                 -Dtelegram.bot-username=${TELEGRAM_BOT_USERNAME} \
                                 -Dtelegram.webhook-path=${TELEGRAM_BOT_WEBHOOK_PATH} \
                                 -Dfrontend.url=${FRONTEND_URL} \
                                 -Dserver.ssl.key-alias=${JKS_KEY_ALIAS} \
                                 -Dserver.ssl.key-password=${JKS_KEY_PASSWORD} \
                                 -Dserver.ssl.key-store-password=${JKS_KEYSTORE_PASSWORD} \
                                 -Dserver.ssl.key-store=${JKS_KEYSTORE_NAME} \
                                 -Dserver.ssl.trust-store=${JKS_TRUSTSTORE_NAME} \
                                 -Dserver.ssl.trust-store-password=${JKS_TRUSTSTORE_PASSWORD}

FROM openjdk:11-jre-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","app.jar"]