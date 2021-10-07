FROM gradle:jdk11@sha256:8d1510af038f03377a9df0d6facc1e301af30f4782bad87ebfe9ec143793d856

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]