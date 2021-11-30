FROM gradle:jdk11@sha256:5b55bcd7b88850f88fdeb1b7794fb79a71b164a011b2ee150724afe9bd2b40d0

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]