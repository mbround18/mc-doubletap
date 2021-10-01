FROM gradle:jdk11@sha256:efecad1d65b7065162e670102ed65ee83b004489b786e2902af7158a0f9c4be1

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]