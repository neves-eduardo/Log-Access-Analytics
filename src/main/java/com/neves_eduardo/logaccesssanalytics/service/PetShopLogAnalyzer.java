package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dao.DatabaseDAO;
import com.neves_eduardo.logaccesssanalytics.dto.Log;
import org.springframework.stereotype.Service;

import java.util.*;
import static java.util.stream.Collectors.toMap;

@Service
public class PetShopLogAnalyzer{
    private final DatabaseDAO databaseDAO;

    public PetShopLogAnalyzer(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    public void publishLog(Log log) {
        databaseDAO.publish(log);
    }

    public Map<String,Double> topURLs (int limit) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL();
        if(!urlAccesses.isEmpty()){return sortAccessesDescendingOrder(limit,urlAccesses);} else return Collections.EMPTY_MAP;
    }

    public Map<String,Double> topURLs (int limit, int region) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL(region);
        if(!urlAccesses.isEmpty()){return sortAccessesDescendingOrder(limit,urlAccesses);} else return Collections.EMPTY_MAP;
    }

    public Map<String,Double> topURLs (int limit, Long millisThreshold) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL(millisThreshold);
        if(!urlAccesses.isEmpty()){return sortAccessesDescendingOrder(limit,urlAccesses);} else return Collections.EMPTY_MAP;
    }

    public Map<String,Double> bottomURLs (int limit) {
        Map<String,Double> urlAccesses = databaseDAO.getAccessesByURL();
        if(!urlAccesses.isEmpty()){return sortAccessesAscendingOrder(limit,urlAccesses);} else return Collections.EMPTY_MAP;
    }

    public Map<Double,Double> mostAccessedMoment (int limit,Long groupByValueInSeconds) {
        Map<Double,Double> urlAccesses = databaseDAO.getAccessesByURLByTime(groupByValueInSeconds);
        if(!urlAccesses.isEmpty()){return sortAccessesByTimeDescendingOrder(limit,urlAccesses);} else return Collections.EMPTY_MAP;
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

    private Map<Double,Double> sortAccessesByTimeDescendingOrder (int limit, Map<Double,Double> urlAccesses) {
        return urlAccesses.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(limit)
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }



}
