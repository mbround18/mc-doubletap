FROM gradle:jdk11@sha256:dd33ac9072bf9e983662183eb0d1bff44f2ae417edc640acd1b5cf2acc661bf3

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]