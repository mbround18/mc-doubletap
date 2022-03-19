FROM gradle:jdk11@sha256:1130ce2b2b857abbf4edacef423ab31deabb2d37f95e110d55958d5b1adb2ce2

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]