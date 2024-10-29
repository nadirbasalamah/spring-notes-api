#!/bin/sh

echo "build the jar executable"
./mvnw clean package -DskipTests

echo "move to dependency directory"
mkdir -p target/dependency && (cd target/dependency; unzip ../*.jar)

echo "build the docker image"
docker build -t spring-notes-app:latest .

echo "done"

