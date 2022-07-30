FROM gradle:jdk11@sha256:4d1b47201246c9f273f5a3965078e1a2ba4682a41a1e0e34b46b1edc56893aa3

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]