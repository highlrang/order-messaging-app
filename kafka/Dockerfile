FROM order-app

WORKDIR /kafka

# RUN chmod +x ./gradlew
# RUN ./gradlew build

ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} kafka.jar

ENTRYPOINT [ "java", "-jar", "kafka.jar"]
# -e