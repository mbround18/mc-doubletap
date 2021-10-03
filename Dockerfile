FROM gradle:jdk11@sha256:b963c2fa3603de2a27a0dfd51db0258fa7eb89b08b4a81fd5e8d5e4e88a28945

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]