FROM gradle:jdk11@sha256:7c8946698072615e07973f249074096f5ad5fac15ca25575e5628f2175d4adc3

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]