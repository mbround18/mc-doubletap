FROM gradle:jdk11@sha256:a40adb54075099dd6e6c958938272a1fe5e565235b065de7a5a389f0c37b8b57

RUN mkdir -p /data

COPY src build.gradle gradle.properties settings.gradle /data/
WORKDIR /data

RUN chmod 777 -R /data

CMD ["/usr/bin/gradle", "build"]