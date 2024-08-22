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
ENV HOME "/app"
ENV TZ "Asia/Shanghai"
ENV LANG "C.UTF-8"
ENV XMS "1g"
ENV XMX "1g"
ENV XSS "256k"
ENV GC_MODE "G1"
ENV USE_GC_LOG "Y"
ENV USE_HEAP_DUMP "Y"
ENV USE_LARGE_PAGES "N"
ENV SPRING_PROFILES_ACTIVE "dev"
ENV SERVER_PORT "8080"
ENV MANAGEMENT_SERVER_PORT "9080"

# 创建普通用户并切换工作目录
RUN groupadd -g 1000 $GROUP \
  && useradd -u 1000 -g $GROUP -d $HOME -s /bin/bash $USER \
  && mkdir $HOME \
  && chown -R $USER:$GROUP $HOME \
  && chmod -R a+rwX $HOME
WORKDIR $HOME

# 从基础镜像复制应用程序依赖项和模块
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader ./
COPY --from=builder /app/organization-dependencies ./
COPY --from=builder /app/modules-dependencies ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
COPY --from=builder /app/entrypoint.sh ./
RUN chmod +x entrypoint.sh

# 切换到容器用户并创建日志目录
USER $USER
RUN mkdir $HOME/logs \
  && touch $HOME/logs/entrypoint.out \
  && ln -sf /dev/stdout $HOME/logs/entrypoint.out \
  && ln -sf /dev/stderr $HOME/logs/entrypoint.out

# 设置启动入口和端口
EXPOSE $SERVER_PORT $MANAGEMENT_SERVER_PORT
ENTRYPOINT ["./entrypoint.sh"]
