FROM gradle:jdk11@sha256:3651f5933aebf1634a01480e677c03d6e10497799a889aeaf0f6f960a8c86f51

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]