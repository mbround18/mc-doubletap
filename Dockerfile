FROM gradle:jdk11@sha256:f6073e7905482fa00f9c3881bc1fa16c8e6864cb4cba4b8ab23bc92d4e10a4ba

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]