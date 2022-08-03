FROM gradle:jdk11@sha256:ed6c647da29cd5bc60419a396f3b6767a2f00953066c663ef34d8b430c4366c8

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]