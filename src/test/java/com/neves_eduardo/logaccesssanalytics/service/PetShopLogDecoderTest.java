package com.neves_eduardo.logaccesssanalytics.service;

import com.neves_eduardo.logaccesssanalytics.dto.Log;
import com.neves_eduardo.logaccesssanalytics.exception.InvalidLogException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PetShopLogDecoderTest {
    private PetShopLogDecoder petShopLogDecoder;
    @Before
    public void init() {
        petShopLogDecoder = new PetShopLogDecoder();
    }

    @Test
    public void petShopLogDecoderShouldReturnAValidLogObjectTest() {
        Log log = petShopLogDecoder.decodeLog("/pets/exotic/cats/10 1037825323957 5b019db5-b3d0-46d2-9963-437860af707f 1");
        assertEquals(log.getId().toString(),"5b019db5-b3d0-46d2-9963-437860af707f");
        assertEquals(log.getUrl(),"/pets/exotic/cats/10");
        assertEquals(log.getRegion(),1);
        assertEquals(log.getTimestamp(),1037825323957L);
    }

    @Test(expected = InvalidLogException.class)
    public void petShopLogDecoderShouldReturnExceptionWhenLogIsInvalidTest() {
        Log log = petShopLogDecoder.decodeLog("/pets/exotic/cats/10 1");
    }

    @Test(expected = InvalidLogException.class)
    public void petShopLogDecoderShouldReturnExceptionWhenNanosecondsValueIsInvalid() {
        Log log = petShopLogDecoder.decodeLog("/pets/exotic/cats/10 10378253239gbh57 5b019db5-b3d0-46d2-9963-437860af707f 1");
    }

    @Test(expected = InvalidLogException.class)
    public void petShopLogDecoderShouldReturnExceptionWhenRegionValueIsInvalid() {
        Log log = petShopLogDecoder.decodeLog("/pets/exotic/cats/10 10378253239gbh57 5b019db5-b3d0-46d2-9963-437860af707f 1faefe");
    }

    @Test(expected = InvalidLogException.class)
    public void petShopLogDecoderShouldReturnExceptionWhenUUIDValueIsInvalid() {
        Log log = petShopLogDecoder.decodeLog("/pets/exotic/cats/10 10378253239gbh57 5b01asffa9db5-basfasf3d0-46aasfd2-9963-437860af707f 1");
    }

}