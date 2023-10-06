package com.example.demo.config;

import com.example.demo.annotation.SqlServerDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


@Configuration
@MapperScan(annotationClass = SqlServerDataSource.class, sqlSessionFactoryRef =  SqlServerDataSourceConfig.SESSION_FACTORY,
basePackages = {"com.example.demo.repository"})
public class SqlServerDataSourceConfig {
    private static final Logger log = LoggerFactory.getLogger(SqlServerDataSourceConfig.class);
    public static final String SESSION_FACTORY = "sqlServerSessionFactory";
    public static final String DATASOURCE = "SQLSERVER_DATASOURCE";

    @Bean(name = DATASOURCE, destroyMethod = "")
    @ConfigurationProperties(prefix = "mssql.datasource")
    public DataSource dataSourceOne() {
        HikariDataSource ds = new HikariDataSource();
        return ds;
    }

    @Bean(name = SESSION_FACTORY, destroyMethod = "")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATASOURCE) final DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResource("classpath:/mappers/mssql/QdQExecHistoryRepository.xml")
        );

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

        if (sqlSessionFactory != null) {
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);

            if (dataSource instanceof HikariDataSource) {
                log.info("HirakiDataSource Microsoft SQL Server url {}", ((HikariDataSource) dataSource).getJdbcUrl());
            }
        }
        return sqlSessionFactory;
    }

}
