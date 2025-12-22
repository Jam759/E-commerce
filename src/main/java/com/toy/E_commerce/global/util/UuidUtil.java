package com.toy.E_commerce.global.util;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

public class UuidUtil {

    public static UUID getUUIDv7(){
        return UuidCreator.getTimeOrderedEpoch();
    }

}
