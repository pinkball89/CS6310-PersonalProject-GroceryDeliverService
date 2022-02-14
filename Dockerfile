# Compile our java files in this container
FROM openjdk:17-slim AS builder
COPY src /usr/src/cs6310/src
WORKDIR /usr/src/cs6310
RUN find . -name "*.java" | xargs javac -d ./target
RUN jar cfe drone_delivery.jar Main -C ./target/ .

# Copy the jar and test scenarios into our final image
FROM openjdk:17-slim
WORKDIR /usr/src/cs6310
COPY test_scenarios ./
COPY test_results ./
COPY --from=builder /usr/src/cs6310/drone_delivery.jar ./drone_delivery.jar
CMD ["java", "-jar", "drone_delivery.jar", "commands_00.txt"]
