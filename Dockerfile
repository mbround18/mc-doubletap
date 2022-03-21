FROM gradle:jdk11@sha256:e38f771a8b883f2c14740ea2c0d99176f5298cff7d2e6b26cfc91c0808e1461f

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]