FROM gradle:jdk11@sha256:398d33230d057a5601749744e5e1c4240e995ae7e9b62110b0c727d648b1edbb

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]