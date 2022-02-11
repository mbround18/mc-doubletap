FROM gradle:jdk11@sha256:c4550288e6c3e9723b0d086e25e1efd04c8c8728b09eaf668803442a99f7992b

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]