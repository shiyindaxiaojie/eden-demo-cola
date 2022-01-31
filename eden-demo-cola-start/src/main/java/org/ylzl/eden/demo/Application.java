package org.ylzl.eden.demo;

import org.ylzl.eden.spring.framework.bootstrap.SpringBootApplicationTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 引导类
 *
 * @author gyl
 * @since 2.4.x
 */
@MapperScan(basePackages = "org.ylzl.eden.demo.infrastructure", annotationClass = Mapper.class)
@EnableDubbo(scanBasePackages = "org.ylzl.eden.demo.adapter")
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Slf4j
@SpringBootApplication
public class Application extends SpringBootApplicationTemplate {

	/**
	 * 启动入口
	 *
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		run(Application.class, args, WebApplicationType.SERVLET);
	}
}
