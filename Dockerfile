FROM gradle:jdk11@sha256:70b742b1c28192da253708801e24818481cf699b3c7a26bb758556b3cd59bdfc

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]