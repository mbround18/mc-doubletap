FROM gradle:jdk11@sha256:ba38042e0ee4806751e70cb34e30897b0ad6b3b8a6217ab33bcf876e03f49a21

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]