FROM gradle:jdk11@sha256:3f9086781db082659fcdf7a13ac46e782c1dfebe2401f4a86e067ed325685074

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]