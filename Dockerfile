FROM gradle:jdk11@sha256:84f9373ce473589603dce5e9f840cdd21582a08cf2b3433316b9a38f82889b17

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]