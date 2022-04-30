FROM gradle:jdk11@sha256:d2eb3a11c3be128f1fe3892c313dc6b3c78c32fb970d873b0c0e0a72f6344596

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]