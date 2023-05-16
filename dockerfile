FROM openjdk:11

WORKDIR /order-app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY core core
COPY auth auth
COPY order-api order-api
COPY store-api store-api
COPY external-client external-client
COPY kafka kafka

COPY core/src/main/resources/application*.yml data.sql order-api/src/main/resources/
COPY core/src/main/resources/application*.yml data.sql store-api/src/main/resources/
COPY core/src/main/resources/application*.yml data.sql auth/src/main/resources/
COPY core/src/main/resources/application*.yml data.sql kafka/src/main/resources/

RUN chmod +x ./gradlew
RUN ./gradlew build

