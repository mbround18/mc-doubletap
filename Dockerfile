FROM gradle:jdk11@sha256:7fc1e50234710709c3eb3b32c60836766705c5991a134cfa9c3be5eea851be83

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]