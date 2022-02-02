FROM gradle:jdk11@sha256:2338785852c9584317e960f5f4f135828d0ab9268465c815ef0240fa95a75579

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]