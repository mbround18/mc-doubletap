FROM gradle:jdk11@sha256:a25677acba8040127805aed6d689fea75a337c0b291a917192fb77e984badfe0

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]