FROM gradle:jdk11@sha256:5e407aad93b19b4fb9e6ec4ca620dd9a1f64758592ee56035fc9d511ffea115d

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]