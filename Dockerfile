FROM gradle:jdk11@sha256:c352a51196f2a6e7cf9015d611140ed0cd834116b5995348651bc721de66dab2

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]