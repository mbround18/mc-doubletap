FROM gradle:jdk11@sha256:d7c6aafd580ad027f529fe611e4f0ac9d29fd949af889d5f2625f8a7091497b3

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]