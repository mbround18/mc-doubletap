FROM gradle:jdk11@sha256:5e684cb0780481f6363b71f659696f7157b6009b09cf759d05203bc379e2008e

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]