FROM gradle:jdk11@sha256:982750da29923d7c5cab1cd29461bec3e8e7a04640d2c3e4e5eb76bcb720a4ed

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]