FROM gradle:jdk11@sha256:d6bc41ea7fed17239f56c7b49ad45347e68a53d6dd6427d131a9beb1da39cedf

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]