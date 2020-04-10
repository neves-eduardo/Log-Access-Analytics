package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dao.DatabaseDAO;
import com.neves_eduardo.logaccesssanalytics.dto.Log;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.msgpack.core.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.select;

public class PetShopLogAnalyzer implements LogAnalyzer{
    private DatabaseDAO databaseDAO;

    public PetShopLogAnalyzer(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    public void analyzeLog (Log log) {
        databaseDAO.publish(log);
    }

    public Map<String,Double> top3URLs (@Nullable Integer region) {
        Query query = select().count("value").from("laa","petshopLogs").groupBy("URL");
        Query queryFiltered = select().count("value").from("laa","petshopLogs").where("Region =~/" + region+"/").groupBy("URL");

        QueryResult result;
        if(region == null) {result = databaseDAO.query(query);} else {result = databaseDAO.query(queryFiltered);}

        List<QueryResult.Series> series = result.getResults().get(0).getSeries();
        Map<String,Double> urlAccesses = new HashMap<>();

        for (QueryResult.Series serie: series) {
            String serieTag = serie.getTags().get("URL");
            Double count = (Double) serie.getValues().get(0).get(1);
            urlAccesses.put(serieTag,count);
        }

        Map<String,Double> sortedAccesses = urlAccesses.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        System.out.println(sortedAccesses);

        return sortedAccesses;
    }

}
