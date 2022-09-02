FROM gradle:jdk11@sha256:5790efa9a0207d60003bedb87f64b9de14f75f1cb69ada5df5c59aabb9ad7c71

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]