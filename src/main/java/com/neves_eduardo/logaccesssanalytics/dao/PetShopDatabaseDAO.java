package com.neves_eduardo.logaccesssanalytics.dao;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.exception.CannotLoadConfigsException;
import com.neves_eduardo.logaccesssanalytics.exception.DatabaseConnectionException;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class PetShopDatabaseDAO implements DatabaseDAO {
    private String influxURL;
    private String influxUser;
    private String influxPassword;
    private String influxDataBase;
    private String influxLogMeasurement;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    public PetShopDatabaseDAO() {
        this.loadProperties();
    }

    @Override
    public void loadProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            this.influxURL = properties.getProperty("db.URL");
            this.influxUser = properties.getProperty("db.username");
            this.influxPassword = properties.getProperty("db.password");
            this.influxDataBase = properties.getProperty("db.name");
            this.influxLogMeasurement = properties.getProperty("db.measurements.petshoplogs");

        } catch (IOException e) {
            throw new CannotLoadConfigsException("Error loading properties.config file");
        }

    }

    @Override
    public Log publish(Log log) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxURL, influxUser, influxPassword);
        try {
            BatchPoints batchPoints = BatchPoints.database(influxDataBase).build();
            Point point = Point
                    .measurement(influxLogMeasurement)
                    .time(log.getTimestamp(), TimeUnit.MILLISECONDS)
                    .tag("URL", log.getUrl())
                    .tag("UUID",log.getId().toString())
                    .tag("Region",Integer.toString(log.getRegion()))
                    .addField("value", 1)
                    .build();

            batchPoints.point(point);
            influxDB.write(batchPoints);
        } catch (Exception e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
        influxDB.close();
        return null;
    }

    @Override
    public QueryResult query(Query query) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxURL, influxUser, influxPassword);
        influxDB.setDatabase(influxDataBase);
        QueryResult queryResult =influxDB.query(query);
        influxDB.close();
        return queryResult;
    }

}
