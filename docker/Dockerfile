# 使用基础镜像
FROM openjdk:8-jdk-alpine as builder

# 设置工作目录并指定构建模块
WORKDIR /app
ARG MODULE=eden-demo-cola-start

# 复制应用程序 JAR 文件
COPY $MODULE/target/*.jar application.jar

# 使用 Spring Boot 的分层模式提取 JAR 文件的依赖项
RUN java -Djarmode=layertools -jar application.jar extract

# 创建容器镜像
FROM openjdk:8-jdk-alpine

# 定义镜像元数据和环境变量
LABEL maintainer="梦想歌 <shiyindaxiaojie@gmail.com>"
LABEL version="1.0.0"
ARG USER=tmpuser
ARG HOME=/app
ARG LOG=$HOME/logs
ENV START_DELAY_SECS 1
ENV JAVA_OPTS "-Xmx512m -Xms512m -Xmn256m"
ENV SPRING_PROFILES_ACTIVE "dev"
ENV SERVER_PORT "8080"
ENV MANAGEMENT_SERVER_PORT "9080"

# 创建普通用户并设置工作目录
RUN addgroup --gid 1000 "$USER" \
  && adduser -u 1000 -G "$USER" -h "$HOME" "$USER" --disabled-password \
  && chown $USER:$USER $HOME
WORKDIR $HOME

# 复制应用程序依赖项和模块
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader ./
COPY --from=builder /app/organization-dependencies ./
COPY --from=builder /app/modules-dependencies ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# 设置容器用户并创建日志目录
USER $USER
RUN mkdir $LOG \
  && touch $LOG/entrypoint.out \
  && ln -sf /dev/stdout $LOG/entrypoint.out \
  && ln -sf /dev/stderr $LOG/entrypoint.out

# 设置启动入口和端口
EXPOSE $SERVER_PORT $MANAGEMENT_SERVER_PORT
ENTRYPOINT exec java $JAVA_OPTS -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom "org.springframework.boot.loader.JarLauncher" "$@"
