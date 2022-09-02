FROM gradle:jdk11@sha256:b17228e1879682599d35c390156732f3a33f917ca00352955e76a3f8822b46ce

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]