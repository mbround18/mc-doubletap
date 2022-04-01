FROM gradle:jdk11@sha256:d920da5cedc06e09c031b748673f2f99456fe8a5d3f437e211fd3cf0ca859fcc

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]