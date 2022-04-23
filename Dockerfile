FROM gradle:jdk11@sha256:c10da1b48fcd574b96f5f6e2f97c86d912692f8133f5f70944ec1857cf09e3e1

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]