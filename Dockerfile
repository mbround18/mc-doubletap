FROM gradle:jdk11@sha256:d409397bd50cd6678fcf4baca11cd518e5ec45786804628e67742fb150ef4e1a

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]