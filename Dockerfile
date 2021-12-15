FROM gradle:jdk11@sha256:58385dc2aef421bcf2812592f6f39d2e94d85909577382fff447c8bc5ac2507e

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]