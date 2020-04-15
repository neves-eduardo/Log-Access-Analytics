package com.neves_eduardo.logaccesssanalytics;

import com.neves_eduardo.logaccesssanalytics.dao.PetShopDatabaseDAO;
import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.service.PetShopLogAnalyzer;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
       PetShopLogAnalyzer logAnalyzer = new PetShopLogAnalyzer(new PetShopDatabaseDAO());
//        for (int i = 0; i < 1000; i++) {
//            logAnalyzer.analyzeLog(generateLog());
//        }
        PetShopDatabaseDAO petShopDatabaseDAO = new PetShopDatabaseDAO();
        System.out.println(logAnalyzer.topURLs(3));
        System.out.println(logAnalyzer.topURLs(3,2));
        System.out.println(logAnalyzer.topURLs(3,86400000L));
        System.out.println(logAnalyzer.bottomURLs(1));
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
