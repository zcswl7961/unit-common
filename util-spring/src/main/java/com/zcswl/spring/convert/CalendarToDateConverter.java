package com.zcswl.spring.convert;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhoucg
 * @date 2020-07-16 17:17
 */
public class CalendarToDateConverter implements Convert<Calendar, Date>{
    @Override
    public Date convert(Calendar source) {
        return source.getTime();
    }
}
