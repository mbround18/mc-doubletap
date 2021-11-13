FROM gradle:jdk11@sha256:f122ce52e2e81724b1faad85c0ea733c70494db8da0372fb80b7557e200a4d8f

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]