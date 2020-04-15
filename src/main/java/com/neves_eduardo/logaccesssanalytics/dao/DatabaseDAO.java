package com.neves_eduardo.logaccesssanalytics.dao;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;

public interface DatabaseDAO {
    void loadProperties();
    void publish(Log log);
    Map<String,Double> getAccessesByURL();
    Map<String,Double> getAccessesByURL(Integer region);
    Map<String,Double> getAccessesByURL(Long timestamp);
    Map<Double,Double> getAccessesByURLByTime(Long groupByValueInSeconds);
}
