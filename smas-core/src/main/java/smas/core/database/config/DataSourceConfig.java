package smas.core.database.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /**
     * Pooled data source using Apache Commons DBCP 2
     * @return Data source bean
     */
    @Bean
    public DataSource dataSource(){
        // TODO move to properties file
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/smas?useUnicode=yes&amp;characterEncoding=UTF-8");
        ds.setUsername("postgres");
        ds.setPassword("pswmb$@FE1");
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
        emfb.setPackagesToScan("smas.core.database.domain");
        return emfb;
    }

    /**
     * JPA vendor adapter provides specifics about particular JPA implementation to use
     * @return JPA vendor adapter
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        // TODO What does it mean?
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL95Dialect");
        adapter.setGenerateDdl(true);
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
