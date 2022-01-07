FROM gradle:jdk11@sha256:7cf5d5975f1be4bc429e499c8f786b6f677b9f290360c1dc490b935a9028785a

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]