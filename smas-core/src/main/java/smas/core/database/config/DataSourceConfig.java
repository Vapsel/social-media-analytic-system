package smas.core.database.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * Defines packages to scan with {@link org.springframework.data.repository.Repository} annotation
 */
@Configuration
@EnableJpaRepositories(basePackages = DataSourceConfig.PACKAGE_TO_SCAN_REPOSITORIES)
@PropertySource("classpath:database_application.properties")
public class DataSourceConfig {

    static final String PACKAGE_TO_SCAN_REPOSITORIES = "smas.core.database.repository";
    private static final String PACKAGE_TO_SCAN_ENTITIES = "smas.core.database.domain";

    private static final String PROPERTY_DATASOURCE_DRIVER_CLASS_NAME = "datasource.driver-class-name";
    private static final String PROPERTY_DATASOURCE_URL = "datasource.url";
    private static final String PROPERTY_DATASOURCE_USERNAME = "datasource.username.system.variable";
    private static final String PROPERTY_DATASOURCE_PASSWORD = "datasource.password.system.variable";

    private static final String PROPERTY_JPA_DATABASE_PLATFORM = "jpa.database-platform";
    private static final String PROPERTY_JPA_SHOW_SQL = "jpa.show-sql";
    private static final String PROPERTY_JPA_GENERATE_DDL = "jpa.generate-ddl";

    /**
     * Pooled data source using Apache Commons DBCP 2
     * @return Data source bean
     */
    @Bean
    public DataSource dataSource(Environment env){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getRequiredProperty(PROPERTY_DATASOURCE_DRIVER_CLASS_NAME));
        ds.setUrl(env.getRequiredProperty(PROPERTY_DATASOURCE_URL));
        ds.setUsername(env.getRequiredProperty(env.getRequiredProperty(PROPERTY_DATASOURCE_USERNAME)));
        ds.setPassword(env.getRequiredProperty(env.getRequiredProperty(PROPERTY_DATASOURCE_PASSWORD)));
        return ds;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    /**
     * Container managed JPA
     * This bean replaces <code>persistence.xml</code>
     * Defines packages to scan with {@link javax.persistence.Entity} annotation
     * @return Entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan(PACKAGE_TO_SCAN_ENTITIES);
        return emfb;
    }

    /**
     * JPA vendor adapter provides specifics about particular JPA implementation to use
     * @return JPA vendor adapter
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(Environment env){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(Boolean.parseBoolean(env.getProperty(PROPERTY_JPA_SHOW_SQL)));
        adapter.setDatabasePlatform(env.getProperty(PROPERTY_JPA_DATABASE_PLATFORM));
        adapter.setGenerateDdl(Boolean.parseBoolean(env.getProperty(PROPERTY_JPA_GENERATE_DDL)));
        return adapter;
    }

    /**
     * Bean that helps Spring to understand JPA annotations
     * @return Bean
     */
    @Bean
    public PersistenceAnnotationBeanPostProcessor paPostProcessor(){
        return new PersistenceAnnotationBeanPostProcessor();
    }

    /**
     * Bean that translates SQL exceptions into Spring unified data-access exceptions
     * @return Bean
     */
    @Bean
    public BeanPostProcessor persistenceTranslation(){
        return  new PersistenceExceptionTranslationPostProcessor();
    }
}
