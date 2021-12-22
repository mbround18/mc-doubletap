FROM gradle:jdk11@sha256:3cb542b0b6cba3eb6a80c58f0d638a6042c5528b7152b47c6891bfa7d68ee68a

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]