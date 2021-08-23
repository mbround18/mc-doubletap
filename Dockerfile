FROM gradle:jdk11@sha256:7e5034ed40ae76bfa56ed39cafaa33678d7b15cf4910af32cf20f0be2901e362

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]