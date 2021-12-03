FROM gradle:jdk11@sha256:48efbf65d927814670fa4a1f78d188af85bc98d128eb828f7022ced7ce1df24d

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]