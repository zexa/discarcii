FROM openjdk:11-jdk as builder
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN ./mvnw package

FROM openjdk:11-jdk
COPY --from=builder /usr/src/myapp /usr/src/myapp
WORKDIR /usr/src/myapp
RUN ls ./target
CMD ["java", "-jar", "./target/discarcii-1.0-SNAPSHOT.jar"]