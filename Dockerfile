FROM gradle:jdk11@sha256:5e1c44518d1883bf706b657f495d2543d43a4390e7f40eee33fcc43c670d5863

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]