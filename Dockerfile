FROM gradle:jdk11@sha256:16294b549832db4f9bb9c985ef0739a7250395d18112456cd36154899fb911fa

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]