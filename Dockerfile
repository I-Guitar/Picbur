FROM java:8

VOLUME /tmp

COPY Picbur-*.jar /app.jar
COPY config.yml /
WORKDIR /

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8868
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]