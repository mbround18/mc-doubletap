FROM gradle:jdk11@sha256:d7346379145c983fde8ae5af8945ce7dc9b7d39192f997d84a557fd28c8c069b

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]