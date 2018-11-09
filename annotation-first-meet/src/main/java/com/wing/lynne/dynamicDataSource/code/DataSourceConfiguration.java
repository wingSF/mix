package com.wing.lynne.dynamicDataSource.code;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;

@ConfigurationProperties(prefix = "db")
@Data
public class DataSourceConfiguration {

    private DataSource masterDataSource;

    private DataSource slaveDataSource;
}
