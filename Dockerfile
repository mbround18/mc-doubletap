FROM gradle:jdk11@sha256:66e99844e0687a482d9481f522361a382a22d420cf9a0964ec8c2d81d82fb126

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]