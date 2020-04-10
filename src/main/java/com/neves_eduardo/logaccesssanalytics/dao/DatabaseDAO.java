package com.neves_eduardo.logaccesssanalytics.dao;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

public interface DatabaseDAO {
    void loadProperties();
    Log publish(Log log);
    QueryResult query(Query query);
}
