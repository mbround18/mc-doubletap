FROM gradle:jdk11@sha256:db25faa6250d980931091d5b6101ee34076d2645bddd7e5f6daf547681a85aba

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]