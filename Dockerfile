FROM gradle:jdk11@sha256:98f70ba8147001bcce783000dc6f11ca89d3eb57d3f064acf35a503ec10d66ac

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]