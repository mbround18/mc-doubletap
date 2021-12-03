FROM gradle:jdk11@sha256:b39792d7b3ddd913e66adb33ecc035454697169ef8b5c2e04fcf5af0df080a13

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]