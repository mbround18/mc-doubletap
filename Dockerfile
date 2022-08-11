FROM gradle:jdk11@sha256:da3dcef7acf4ee493374ebbfc936c59168a9734947800bbb257e9d1808e7d106

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]