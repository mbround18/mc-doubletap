FROM gradle:jdk11@sha256:771e52f19227443fdf6993db5e00a150a4d3cccab94f844651d83fd83086002c

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]