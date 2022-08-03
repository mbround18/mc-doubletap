FROM gradle:jdk11@sha256:661ba79bd94a1cd72c830f7db2a4e4965ae0f66923b182d4f0cac7fa46489d27

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]