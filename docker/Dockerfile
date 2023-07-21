FROM openjdk:8-jdk-alpine as builder
WORKDIR /app
ARG PROJECT=eden-demo-cola-start
COPY $PROJECT/target/*.jar application.jar
COPY $PROJECT/src/main/docker/entrypoint.sh entrypoint.sh
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:8-jdk-alpine
MAINTAINER gyl "shiyindaxiaojie@gmail.com"
ARG USER=tmpuser
ARG HOME=/home/$USER
RUN addgroup --gid 1000 "$USER" \
  && adduser -u 1000 -G "$USER" -h "$HOME" "$USER" --disabled-password \
  && chown $USER:$USER $HOME

USER $USER
WORKDIR $HOME
COPY --from=builder /app/dependencies/ ./dependencies/
COPY --from=builder /app/spring-boot-loader ./spring-boot-loader/
COPY --from=builder /app/organization-dependencies ./organization-dependencies/
COPY --from=builder /app/modules-dependencies ./modules-dependencies/
COPY --from=builder /app/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=builder /app/application/ ./application/
COPY --from=builder /app/entrypoint.sh ./entrypoint.sh
ENV START_DELAY_SECS 1
ENV JAVA_OPTS '-Xmx512m -Xms512m -Xmn256m'
ENV JAVA_AGENT ''
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone \
    && mkdir logs \
    && cd logs \
    && touch entrypoint.out \
    && ln -sf /dev/stdout entrypoint.out \
    && ln -sf /dev/stderr entrypoint.out
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
