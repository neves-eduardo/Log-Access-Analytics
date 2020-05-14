package com.neves_eduardo.logaccesssanalytics.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neves_eduardo.logaccesssanalytics.dao.DatabaseDAO;
import com.neves_eduardo.logaccesssanalytics.dto.MetricsDTO;
import com.neves_eduardo.logaccesssanalytics.exception.DatabaseConnectionException;
import com.neves_eduardo.logaccesssanalytics.exception.InvalidLogException;
import com.neves_eduardo.logaccesssanalytics.service.LogDecoder;
import com.neves_eduardo.logaccesssanalytics.service.PetShopLogAnalyzer;
import com.neves_eduardo.logaccesssanalytics.service.PetShopLogDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

@RequestMapping(value = "/laa")
@RestController
public class PetShopLogController {
    private DatabaseDAO databaseDAO;
    private LogDecoder logDecoder;
    private PetShopLogAnalyzer petShopLogAnalyzer;
    private ObjectMapper objectMapper;

    @Autowired
    public PetShopLogController(DatabaseDAO databaseDAO, LogDecoder logDecoder, PetShopLogAnalyzer petShopLogAnalyzer, ObjectMapper objectMapper) {
        this.databaseDAO = databaseDAO;
        this.logDecoder = logDecoder;
        this.petShopLogAnalyzer = petShopLogAnalyzer;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/ingest")
    public @ResponseBody ResponseEntity addLog(@RequestBody String log) {
        try{
            databaseDAO.publish(logDecoder.decodeLog(log));
        } catch (InvalidLogException invalidLogException) {
            return ResponseEntity.badRequest().body(invalidLogException.getMessage());
        } catch (DatabaseConnectionException databaseConnectionException) {
            return ResponseEntity.status(500).body(databaseConnectionException.getMessage());
        }
        return ResponseEntity.ok(log);
    }

    @GetMapping("/metrics/{top}/{region}/{timeUnit}/{duration}")
    public @ResponseBody ResponseEntity getMetrics(@PathVariable Integer top, @PathVariable Integer region, @PathVariable String timeUnit, @PathVariable Long duration) throws JsonProcessingException {
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(new MetricsDTO(petShopLogAnalyzer.topURLs(top),
                petShopLogAnalyzer.topURLs(top,region),
                petShopLogAnalyzer.bottomURLs(top),
                petShopLogAnalyzer.topURLs(top,TimeUnit.valueOf(timeUnit).toMillis(duration)),
                petShopLogAnalyzer.mostAccessedMoment(top,TimeUnit.valueOf(timeUnit).toMillis(duration)))));
    }
    @GetMapping("/health")
    public @ResponseBody ResponseEntity healthChecker() {
        return ResponseEntity.ok().body(null);
    }


}
