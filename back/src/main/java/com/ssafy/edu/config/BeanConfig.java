package com.ssafy.edu.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class BeanConfig { // bean 객체 창고

//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().build();
//	}
//	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		sessionFactory.setTypeAliasesPackage("com.ssafy.edu.dto");
		return sessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate template(SqlSessionFactory factory) {
		return new SqlSessionTemplate(factory);
	}

//	@Bean(name = "tilesConfigure")
//	public TilesConfigurer tilesConfigurer() {
//		final TilesConfigurer configurer = new TilesConfigurer();
//		configurer.setDefinitions(new String[] { "/WEB-INF/views/layouts.xml" });
//		configurer.setCheckRefresh(true);
//		return configurer;
//	}
//
//	@Bean(name = "viewResolver")
//	public TilesViewResolver tilesViewResolver() {
//		TilesViewResolver resolver = new TilesViewResolver();
//		resolver.setViewClass(TilesView.class);
//		resolver.setOrder(1);
//		return resolver;
//	}
}
