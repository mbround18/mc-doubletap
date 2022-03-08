FROM gradle:jdk11@sha256:329d1aabe6dce7fd6e757d5b8e722654e24770c0a9922a9d0fe1f9e716313753

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]