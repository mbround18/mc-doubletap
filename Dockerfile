FROM gradle:jdk11@sha256:0fedc5c67446d419ccc310d213a6525eca5d852ff6d1bd3c85a412767b3b4eac

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]