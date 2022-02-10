FROM gradle:jdk11@sha256:59fbc7e9faef8cdac0c5cce4e5d2965e41a66cff3c5032c28816e7d1b7ac4b68

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]