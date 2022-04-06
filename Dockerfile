FROM gradle:jdk11@sha256:9e75afbb9ed286fa818ce396659da6874fed2c103638a4ce16f072533677055c

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]