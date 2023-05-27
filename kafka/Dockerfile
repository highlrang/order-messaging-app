FROM order-app

WORKDIR /order-app/kafka

# RUN chmod +x ./gradlew
# RUN ./gradlew build

ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} /order-app/kafka/build/libs/kafka.jar

ENTRYPOINT ["java", "-jar", "/order-app/kafka/build/libs/kafka.jar"]
# -e