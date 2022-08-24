FROM gradle:jdk11@sha256:135471ae502b0f00fc22be16991d29e55a8a617df5d62921bd0a5f4cdb0f6f1a

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]