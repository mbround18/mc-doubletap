FROM gradle:jdk11@sha256:b291c31af23e3f462a7737dde63f3a999bb44407bf3e3b91c56fb5a6fbdd1f24

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]