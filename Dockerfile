FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
COPY bootstrap ./bootstrap
COPY common ./common
COPY context ./context

RUN chmod +x gradlew \
    && ./gradlew :bootstrap:bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S ticketport && adduser -S ticketport -G ticketport
USER ticketport:ticketport

COPY --from=build /workspace/bootstrap/build/libs/ticketport.jar ./ticketport.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "ticketport.jar"]
