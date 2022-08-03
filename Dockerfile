FROM gradle:jdk11@sha256:d16065abffcc838bec656bd1396744064439cd0b0a4be33ac581b73f409d40df

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]