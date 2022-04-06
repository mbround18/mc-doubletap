FROM gradle:jdk11@sha256:850511ca2e6f31efbd30be3bc205c5b3edb7f5ed3e4a9aef9c63aaa6534f7ced

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]