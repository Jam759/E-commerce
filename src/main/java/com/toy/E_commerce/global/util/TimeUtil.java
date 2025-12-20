package com.toy.E_commerce.global.util;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class TimeUtil {

    public static LocalDateTime getNowSeoulLocalDateTime() {
        Instant instant = Instant.now();
        ZonedDateTime zSeoul = instant.atZone(ZoneId.of("Asia/Seoul"));
        return zSeoul.toLocalDateTime();
    }

}
