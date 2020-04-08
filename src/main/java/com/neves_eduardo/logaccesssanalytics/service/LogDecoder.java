package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dto.Log;

public interface LogDecoder {
    Log decodeLog(String log);
}
