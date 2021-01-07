package org.macula.cloud.stock.config;

// @Configuration
// @MapperScan(basePackages = "org.macula.cloud.gbss.mapper", sqlSessionTemplateRef = "gbssSqlSessionTemplate")
// @EnableJpaRepositories(entityManagerFactoryRef = "gbssEntityManagerFactory", transactionManagerRef = "gbssJpaTransactionManager", basePackages = {
//		"org.macula.cloud.stock.gbss.repository" })
public class GbssDatabaseConfig {

	//	@ConfigurationProperties(prefix = "spring.datasource.gbss")
	//	@Bean(name = "gbssDataSource")
	//	public DataSource gbssDataSource(Environment environment) {
	//		return DruidDataSourceBuilder.create().build();
	//	}
	//
	//	@Bean(name = "gbssDataSourceTransactionManager")
	//	public DataSourceTransactionManager gbssDataSourceTransactionManager(@Qualifier("gbssDataSource") DataSource dataSource) {
	//		return new DataSourceTransactionManager(dataSource);
	//	}
	//
	//	@Bean(name = "gbssEntityManagerFactory")
	//	public LocalContainerEntityManagerFactoryBean gbssEntityManagerFactory(@Qualifier("gbssDataSource") DataSource dataSource,
	//			EntityManagerFactoryBuilder builder, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
	//		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
	//		return builder.dataSource(dataSource).properties(properties).packages("org.macula.cloud.gbss.domain").build();
	//	}
	//
	//	@Bean(name = "gbssEntityManager")
	//	public EntityManager gbssEntityManager(@Qualifier("gbssEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
	//		return entityManagerFactory.createEntityManager();
	//	}
	//
	//	@Bean(name = "gbssJpaTransactionManager")
	//	public PlatformTransactionManager gbssJpaTransactionManager(@Qualifier("gbssEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
	//		return new JpaTransactionManager(entityManagerFactory);
	//	}
	//
	//	@Bean(name = "gbssSqlSessionFactory")
	//	public SqlSessionFactory gbssSqlSessionFactory(@Qualifier("gbssDataSource") DataSource dataSource) throws Exception {
	//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	//		bean.setDataSource(dataSource);
	//		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/gbss/*.xml"));
	//		return bean.getObject();
	//	}
	//
	//	@Bean(name = "gbssSqlSessionTemplate")
	//	public SqlSessionTemplate gbssSqlSessionTemplate(@Qualifier("gbssSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
	//		return new SqlSessionTemplate(sqlSessionFactory);
	//	}

}
