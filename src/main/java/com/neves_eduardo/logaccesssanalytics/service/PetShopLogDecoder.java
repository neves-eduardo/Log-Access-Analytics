package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.exception.InvalidLogException;
import org.apache.commons.lang3.math.NumberUtils;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class PetShopLogDecoder implements LogDecoder {
    private static final int URL_INDEX = 0;
    private static final int MILISECONDS_INDEX = 1;
    private static final int UUID_INDEX = 2;
    private static final int REGION_INDEX = 3;

    public PetShopLogDecoder() {
    }

    @Override
    public Log decodeLog(String logText) {
        logText = logText.replace("\"","");
        String[] logAttributes = logText.split("\\s+");

        if (logAttributes.length != 4) {throw new InvalidLogException("Invalid Log Format Input.");}

        if (!NumberUtils.isParsable(logAttributes[MILISECONDS_INDEX])) {
            throw new InvalidLogException("Invalid Log Format input: Invalid Nanosecond value.");
        }

        if (!NumberUtils.isParsable(logAttributes[REGION_INDEX])) {
            throw new InvalidLogException("Invalid Log Format input: Invalid Region Integer value.");
        }

        if (!logAttributes[UUID_INDEX].matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
            throw new InvalidLogException("Invalid Log Format input: Invalid UUID value.");
        }

        return new Log(logAttributes[URL_INDEX],
                Long.parseLong(logAttributes[MILISECONDS_INDEX]),
                UUID.fromString(logAttributes[UUID_INDEX]),
                Integer.parseInt(logAttributes[REGION_INDEX]));
    }

    @Override
    public List<Log> decodeLogs(QueryResult queryResult) {
        List<QueryResult.Result> results = queryResult.getResults();
        if(queryResult.getError() != null) throw new InvalidLogException("Error in query");
        if(queryResult.getResults().isEmpty()) throw new InvalidLogException("Result set empty");
        for (QueryResult.Result result:queryResult.getResults()) {
            //TODO DECODING TO LOG DTO
        }


        return null;
    }
}
