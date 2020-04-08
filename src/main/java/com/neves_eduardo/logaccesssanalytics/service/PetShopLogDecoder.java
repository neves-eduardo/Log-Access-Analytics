package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.exception.InvalidLogException;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.UUID;

public class PetShopLogDecoder implements LogDecoder {
    private static final int URL_INDEX = 0;
    private static final int NANOSECS_INDEX = 1;
    private static final int UUID_INDEX = 2;
    private static final int REGION_INDEX = 3;

    @Override
    public Log decodeLog(String logText) {
        String[] logAttributes = logText.split("\\s+");

        if (logAttributes.length != 4) {throw new InvalidLogException("Invalid Log Format Input.");}

        if (!NumberUtils.isParsable(logAttributes[NANOSECS_INDEX])) {
            throw new InvalidLogException("Invalid Log Format input: Invalid Nanosecond value.");
        }

        if (!NumberUtils.isParsable(logAttributes[REGION_INDEX])) {
            throw new InvalidLogException("Invalid Log Format input: Invalid Region Integer value.");
        }

        if (!logAttributes[UUID_INDEX].matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
            throw new InvalidLogException("Invalid Log Format input: Invalid UUID value.");
        }

        return new Log(logAttributes[URL_INDEX],
                Long.parseLong(logAttributes[NANOSECS_INDEX]),
                UUID.fromString(logAttributes[UUID_INDEX]),
                Integer.parseInt(logAttributes[REGION_INDEX]));
    }
}
