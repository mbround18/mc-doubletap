FROM gradle:jdk11@sha256:765c38058880c760911834c983b221edb83b65743437933245313be7d11fe14c

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]