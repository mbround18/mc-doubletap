FROM gradle:jdk11@sha256:1d472df71d43fd1243ba036d47a4b9aaefb8655d818d5b54785fc26526469f41

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]