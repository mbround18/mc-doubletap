FROM gradle:jdk11@sha256:6651a4e3177ddc3077a5bc70c9ba0870d8e4579593dc0acb5aa47996159cbca2

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]