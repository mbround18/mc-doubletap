FROM gradle:jdk11@sha256:361e750d488b2c96c3fb80fe97fe03b542abef324b85d62c829433f9bf5c6603

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]