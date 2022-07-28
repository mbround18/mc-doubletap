FROM gradle:jdk11@sha256:8dc00f59fde12bbfb28df94af62203c30b7568d10ecfdcc59fce2cf297376363

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]