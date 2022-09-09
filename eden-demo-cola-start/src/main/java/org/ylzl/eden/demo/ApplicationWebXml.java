package org.ylzl.eden.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.ylzl.eden.spring.framework.bootstrap.util.SpringProfileUtils;

/**
 * J2EE 加载器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		SpringProfileUtils.addDefaultProfile(builder.application());
		return builder.sources(Application.class);
	}
}
