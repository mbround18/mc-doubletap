FROM gradle:jdk11@sha256:4938f732c57e26647afffdd5662e21a92172c9a814622e1d6bc81da08b3cfb6c

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]