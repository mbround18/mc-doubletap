FROM gradle:jdk11@sha256:40a49896e118fb3829cf3e6c7a662c1b0f3afff25a36a3455f2198c4ba89947b

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]