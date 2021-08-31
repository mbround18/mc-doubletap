FROM gradle:jdk11@sha256:4b50f66dfe05ceb68180bd6a8aa11ba27b03496ad04c39fbd39ecc73fe2c065c

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]