FROM gradle:jdk11@sha256:428497bcc4537d5bb6f16798aeb552fdb55c42755c729c9d047821da2ab4db41

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]