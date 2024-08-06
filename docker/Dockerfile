# 使用基础镜像
FROM openjdk:11-jdk-slim as builder

# 设置工作目录并指定构建模块
WORKDIR /app
ARG MODULE=eden-demo-cola-start

# 复制必要文件
COPY $MODULE/target/$MODULE.jar application.jar
COPY docker/entrypoint.sh entrypoint.sh

# 使用 Spring Boot 的分层模式提取 JAR 文件的依赖项
RUN java -Djarmode=layertools -jar application.jar extract

# 创建容器镜像
FROM openjdk:11-jdk-slim

# 定义镜像元数据和环境变量
LABEL maintainer="梦想歌 <shiyindaxiaojie@gmail.com>"
LABEL version="1.0.0"
ARG USER=tmpuser
ARG GROUP=tmpgroup
ARG HOME=/app
ARG LOG=$HOME/logs
ENV WORKDIR $HOME
ENV START_DELAY_SECS 1
ENV JVM_XMS "1g"
ENV JVM_XMX "1g"
ENV JVM_XSS "256k"
ENV JVM_MS "128m"
ENV USE_HEAP_DUMP "y"
ENV USE_GC_LOG "y"
ENV USE_LARGE_PAGES "n"
ENV DEBUG "n"
ENV SPRING_PROFILES_ACTIVE "dev"
ENV SERVER_PORT "8080"
ENV MANAGEMENT_SERVER_PORT "9080"

# 创建普通用户并设置工作目录
RUN groupadd -g 1000 $GROUP \
  && useradd -u 1000 -g $GROUP -d $HOME -s /bin/bash $USER \
  && mkdir $HOME \
  && chown -R $USER:$GROUP $HOME \
  && chmod -R a+rwX $HOME \
  && mkdir /dev/shm/logs \
  && chmod -R a+rw /dev/shm/logs
WORKDIR $HOME

# 复制应用程序依赖项和模块
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader ./
COPY --from=builder /app/organization-dependencies ./
COPY --from=builder /app/modules-dependencies ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
COPY --from=builder /app/entrypoint.sh ./
RUN chmod +x entrypoint.sh

# 设置容器用户并创建日志目录
USER $USER
RUN mkdir $LOG \
  && touch $LOG/entrypoint.out \
  && ln -sf /dev/stdout $LOG/entrypoint.out \
  && ln -sf /dev/stderr $LOG/entrypoint.out

# 设置启动入口和端口
EXPOSE $SERVER_PORT $MANAGEMENT_SERVER_PORT
ENTRYPOINT ["./entrypoint.sh"]
