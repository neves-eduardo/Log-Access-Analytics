package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import org.influxdb.dto.QueryResult;

import java.util.List;

public interface LogDecoder {
    Log decodeLog(String log);
    List<Log> decodeLogs(QueryResult queryResult);
}
