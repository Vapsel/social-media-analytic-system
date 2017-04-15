package smas.core.database.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
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
    @Profile("development")
    @Bean
    public DataSource dataSource(){
        // TODO move to properties file
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:tcp://localhost:5432/smas");
        ds.setUsername("postgres");
        ds.setPassword("pswmb$@FE1");
        // TODO What does it mean?
        ds.setInitialSize(5);
        return ds;
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
    public BeanPostProcessor persistanceTranslation(){
        return  new PersistenceExceptionTranslationPostProcessor();
    }
}
