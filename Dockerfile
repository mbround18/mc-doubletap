FROM gradle:jdk11@sha256:4a87c5ad17eb79107212f6e561252c0470aab6c4f0c6a0d5fd89e685ff4bde88

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]