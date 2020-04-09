package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dao.Publisher;
import com.neves_eduardo.logaccesssanalytics.dto.Log;

public class PetShopLogAnalyzer {
    private Publisher publisher;

    public PetShopLogAnalyzer(Publisher publisher) {
        this.publisher = publisher;
    }

    public void analyzeLog (Log log) {
        publisher.publish(log);
    }
}
