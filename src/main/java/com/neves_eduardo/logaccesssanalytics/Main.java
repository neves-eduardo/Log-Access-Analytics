package com.neves_eduardo.logaccesssanalytics;

import com.neves_eduardo.logaccesssanalytics.dao.PetShopDatabaseDAO;
import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.service.LogAnalyzer;
import com.neves_eduardo.logaccesssanalytics.service.PetShopLogAnalyzer;
import com.neves_eduardo.logaccesssanalytics.service.PetShopLogDecoder;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Query;
import org.influxdb.querybuilder.SelectQueryImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.select;

public class Main {
    public static void main(String[] args) {
//        LogAnalyzer logAnalyzer = new PetShopLogAnalyzer(new PetShopDatabaseDAO());
//        for (int i = 0; i < 1000; i++) {
//            logAnalyzer.analyzeLog(generateLog());
//        }
//        PetShopDatabaseDAO petShopDatabaseDAO = new PetShopDatabaseDAO();
//        Query query = select().count("value").from("laa","petshopLogs").groupBy("URL");
//        System.out.println(petShopDatabaseDAO
//                .query(query)
//                .getResults()
//                .get(0).getSeries().get(0).getValues().get(0).get(1));
        PetShopLogAnalyzer petShopLogAnalyzer = new PetShopLogAnalyzer(new PetShopDatabaseDAO());
        petShopLogAnalyzer.top3URLs(null);
    }

    public static Log generateLog() {
        Random random = new Random();
        List<String> urls = new ArrayList<>();
        urls.add("/pets/exotic/cats/10");
        urls.add("/pets/guaipeca/dogs/1");
        urls.add("/tigers/bid/now");
        urls.add("/monkeys/bid/now");


        return new Log(urls.get(random.nextInt((4))), Timestamp.from(Instant.now()).getTime(), UUID.randomUUID(), random.nextInt(3)+1);
    }
}
