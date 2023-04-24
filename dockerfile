FROM openjdk:11

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY core core
COPY order-api order-api
COPY store-api store-api
COPY external-client external-cilent
COPY kafka kafka

COPY core/src/main/resources/application*.yml order-api/src/main/resources
COPY core/src/main/resources/application*.yml store-api/src/main/resources
COPY core/src/main/resources/application*.yml kafka/src/main/resources

RUN chmod +x ./gradlew
RUN ./gradlew build

