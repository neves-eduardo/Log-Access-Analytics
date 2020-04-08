package com.neves_eduardo.logaccesssanalytics.dao;

import com.neves_eduardo.logaccesssanalytics.dto.Log;

public interface Publisher {
    void loadProperties();
    Log publish(Log log);
}
