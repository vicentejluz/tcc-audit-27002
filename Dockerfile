# Estágio de compilação
FROM maven:3.9.6-eclipse-temurin-21-alpine as build

ENV PROJECT_HOME /usr/src/tcc_audit

RUN mkdir -p $PROJECT_HOME

WORKDIR $PROJECT_HOME

ADD . $PROJECT_HOME

RUN mvn clean package -DskipTests

# Estágio de execução
FROM eclipse-temurin:21.0.3_9-jre-jammy

ENV PROJECT_HOME /usr/src/tcc_audit
ENV JAR_NAME tcc-audit-1.0.jar
ENV NEW_JAR_NAME tcc_audit.jar

RUN mkdir -p $PROJECT_HOME

WORKDIR $PROJECT_HOME

ADD docker-entrypoint.sh /usr/local/bin/

COPY --from=build $PROJECT_HOME/target/$JAR_NAME $NEW_JAR_NAME

ENTRYPOINT ["docker-entrypoint.sh"]