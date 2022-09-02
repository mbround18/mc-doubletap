FROM gradle:jdk11@sha256:5fecd42e65675a91b30245673975d9e5914bc5b622d2333e48026396bd1132a4

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]