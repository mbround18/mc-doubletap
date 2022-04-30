FROM gradle:jdk11@sha256:c81c15b2af05ab6256ad14f65022986d6f406af211db007255268a8c06c8f247

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]