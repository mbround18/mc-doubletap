FROM gradle:jdk11@sha256:3652ffa5fddf743da207c2fcb879c9072e851b577e36fd1e1f840ea6929f259c

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]