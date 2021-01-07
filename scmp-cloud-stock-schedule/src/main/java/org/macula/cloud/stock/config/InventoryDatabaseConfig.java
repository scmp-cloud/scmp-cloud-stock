package org.macula.cloud.stock.config;

// @Configuration
// @MapperScan(basePackages = "org.macula.cloud.inventory.mapper", sqlSessionTemplateRef = "inventorySqlSessionTemplate")
// @EnableJpaRepositories(entityManagerFactoryRef = "inventoryEntityManagerFactory", transactionManagerRef = "inventoryJpaTransactionManager", basePackages = {
//		"org.macula.cloud.stock.repository" })
public class InventoryDatabaseConfig {

	//	@ConfigurationProperties(prefix = "spring.datasource.inventory")
	//	@Bean(name = "inventoryDataSource")
	//	@Primary
	//	public DataSource inventoryDataSource(Environment environment) {
	//		return DruidDataSourceBuilder.create().build();
	//	}
	//
	//	@Bean(name = "inventoryDataSourceTransactionManager")
	//	@Primary
	//	public DataSourceTransactionManager inventoryDataSourceTransactionManager(@Qualifier("inventoryDataSource") DataSource dataSource) {
	//		return new DataSourceTransactionManager(dataSource);
	//	}

	//	@Bean(name = "inventorySqlSessionFactory")
	//	@Primary
	//	public SqlSessionFactory inventorySqlSessionFactory(@Qualifier("inventoryDataSource") DataSource dataSource) throws Exception {
	//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	//		bean.setDataSource(dataSource);
	//		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/inventory/*.xml"));
	//		return bean.getObject();
	//	}
	//
	//	@Bean(name = "inventorySqlSessionTemplate")
	//	@Primary
	//	public SqlSessionTemplate inventorySqlSessionTemplate(@Qualifier("inventorySqlSessionFactory") SqlSessionFactory sqlSessionFactory)
	//			throws Exception {
	//		return new SqlSessionTemplate(sqlSessionFactory);
	//	}
	//
	//	@Bean(name = "inventoryEntityManagerFactory")
	//	@Primary
	//	public LocalContainerEntityManagerFactoryBean inventoryEntityManagerFactory(@Qualifier("inventoryDataSource") DataSource dataSource,
	//			EntityManagerFactoryBuilder builder, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
	//		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
	//		return builder.dataSource(dataSource).properties(properties).packages("org.macula.cloud.inventory.domain").build();
	//	}
	//
	//	@Bean(name = "inventoryEntityManager")
	//	@Primary
	//	public EntityManager inventoryEntityManager(@Qualifier("inventoryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
	//		return entityManagerFactory.createEntityManager();
	//	}
	//
	//	@Bean(name = "inventoryJpaTransactionManager")
	//	@Primary
	//	public PlatformTransactionManager inventoryJpaTransactionManager(
	//			@Qualifier("inventoryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
	//		return new JpaTransactionManager(entityManagerFactory);
	//	}
}
