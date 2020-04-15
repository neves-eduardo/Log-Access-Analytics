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
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.*;
import static org.influxdb.querybuilder.time.DurationLiteral.*;

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
    public void publish(Log log) {
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
    }

    public Map<String,Double> getAccessesByURL() {
        Query query = select().count("value").from(influxDataBase,influxLogMeasurement).groupBy("URL");
        return mapCountedEntries(query);
    }

    public Map<String,Double> getAccessesByURL(Integer region) {
        Query query = select().count("value").from(influxDataBase,influxLogMeasurement).where(eq("Region",Integer.toString(region))).groupBy("URL");
        return mapCountedEntries(query);
    }

    public Map<String,Double> getAccessesByURL(Long timestamp) {
        Query query = select().count("value").from(influxDataBase,influxLogMeasurement)
                .where()
                .and(gte("time",ti(System.currentTimeMillis() - timestamp,MILLISECONDS)))
                .and(lte("time",ti(System.currentTimeMillis(),MILLISECONDS))).groupBy("URL");
        return mapCountedEntries(query);
    }

    public Map<Double,Double> getAccessesByURLByTime(Long groupByValueInSeconds) {
        Query query = select().count("value").from(influxDataBase,influxLogMeasurement)
                .groupBy(time(groupByValueInSeconds,SECOND));
        return mapCountedEntriesByTime(query);
    }

    private Map<String,Double> mapCountedEntries(Query query) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxURL, influxUser, influxPassword);
        QueryResult result = influxDB.query(query,TimeUnit.MILLISECONDS);
        influxDB.close();
        List<QueryResult.Series> series = result.getResults().get(0).getSeries();
        Map<String,Double> urlAccesses = new HashMap<>();
        if(series == null){return Collections.emptyMap();}
        for (QueryResult.Series serie: series) {
            String serieTag = serie.getTags().get("URL");
            Double count = (Double) serie.getValues().get(0).get(1);
            urlAccesses.put(serieTag,count);
        }
        return urlAccesses;

    }

    private Map<Double,Double> mapCountedEntriesByTime(Query query) {
        InfluxDB influxDB = InfluxDBFactory.connect(influxURL, influxUser, influxPassword);
        QueryResult queryResult = influxDB.query(query,TimeUnit.MILLISECONDS);
        influxDB.close();
        List<List<Object>> results =queryResult.getResults().get(0).getSeries().get(0).getValues();
        Map<Double,Double> urlAccesses = new HashMap<>();
        if(results == null){return Collections.emptyMap();}
        for (List<Object>result: results) {
            Double timestamp = (Double) result.get(0);
            Double count = (Double) result.get(1);
           if(count != 0) {urlAccesses.put(timestamp, count);}
        }
        return urlAccesses;

    }
}
