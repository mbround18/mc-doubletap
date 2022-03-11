FROM gradle:jdk11@sha256:ec5b06507fbd97682e1e46104db82131c840b887be484723b5362b3d03ff6aba

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]