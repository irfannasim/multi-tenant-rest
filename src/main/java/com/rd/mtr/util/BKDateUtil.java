package com.rd.mtr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 12-Feb-2019
 * @version   : 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : multi-tenant-rest
 * @Package   : com.rd.mtr.util
 * @FileName  : BKDateUtil
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class BKDateUtil {

    public static List<String> calcualteMonthsSinceFiscal(String fiscalMonth) {
        List<String> months = new ArrayList<>();


        return null;
    }

    public static Date getDateFromString(String formatedDate, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(formatedDate);
    }
}
