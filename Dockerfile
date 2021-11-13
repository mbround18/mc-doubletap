FROM gradle:jdk11@sha256:8a9f92176e1b9ee3b8b6ac76c1aac2aa38164c5ebafdbe1ebc06a3ef60c7ff67

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]