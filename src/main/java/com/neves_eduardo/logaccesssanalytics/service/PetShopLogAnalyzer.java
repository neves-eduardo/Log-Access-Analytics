package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dao.DatabaseDAO;
import com.neves_eduardo.logaccesssanalytics.dto.Log;
import java.util.*;
import static java.util.stream.Collectors.toMap;


public class PetShopLogAnalyzer implements LogAnalyzer{
    private final DatabaseDAO databaseDAO;

    public PetShopLogAnalyzer(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    public void analyzeLog (Log log) {
        databaseDAO.publish(log);
    }

    public Map<String,Double> topURLs (int limit) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL();
        return sortAccessesDescendingOrder(limit,urlAccesses);
    }

    public Map<String,Double> topURLs (int limit, int region) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL(region);
        return sortAccessesDescendingOrder(limit,urlAccesses);
    }

    public Map<String,Double> topURLs (int limit, Long millisThreshold) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL(millisThreshold);
        return sortAccessesDescendingOrder(limit,urlAccesses);
    }

    public Map<String,Double> bottomURLs (int limit) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL();
        return sortAccessesAscendingOrder(limit,urlAccesses);
    }



    private Map<String,Double> sortAccessesDescendingOrder (int limit, Map<String,Double> urlAccesses) {
        return urlAccesses.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(limit)
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    private Map<String,Double> sortAccessesAscendingOrder (int limit, Map<String,Double> urlAccesses) {
        return urlAccesses.entrySet()
                .stream()
                .sorted((Map.Entry.comparingByValue()))
                .limit(limit)
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }
}
