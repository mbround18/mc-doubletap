FROM gradle:jdk11@sha256:296066535f0b7fca3d5276881a652bebb045e6d471de1d2d3d56f6b7285d4765

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]