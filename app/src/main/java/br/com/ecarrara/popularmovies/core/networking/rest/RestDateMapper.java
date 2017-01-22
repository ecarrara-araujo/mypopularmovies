package br.com.ecarrara.popularmovies.core.networking.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ecarrara.popularmovies.core.utils.datetime.DateUtils;

public class RestDateMapper {

    private static final Date INVALID_DATE = DateUtils.INVALID_DATE;

    /**
     * Parses a date from the JSON format to a Date Object
     * @param dateString JSON date to be converted
     * @return a Date object or {@link DateUtils#INVALID_DATE} in case of error
     */

    public static Date transformFrom(String dateString) {
        DateFormat format = new SimpleDateFormat(RestConfigs.API_DATE_FORMAT);
        Date date = INVALID_DATE;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
