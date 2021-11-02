FROM gradle:jdk11@sha256:ab3cb0afa4f8d5e8596fd52733620fda1a1030ee7b5fb417bb1d7c21f8505628

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]