FROM gradle:jdk11@sha256:e53a42ae81fce3125b2453c8a859996930065f174f173b3822ad8ad5fac630ef

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]