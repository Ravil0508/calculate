package com.task.calculate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.calculate.config.HolidaysConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class HolidayServiceImpl implements HolidayService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ObjectMapper mapper;

    public HolidayServiceImpl(ObjectMapper yamlObjectMapper) {
        this.mapper = yamlObjectMapper;
    }

    private boolean isHolidayOrWeekend(LocalDate date) {
        String[] holidays = loadHolidays();
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
            return true;
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM"));
        for (String holiday : holidays) {
            if (holiday.equals(formattedDate))
                return true;
        }
        return false;
    }

    public int getWorkingDays(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        int workingDays = 0;
        for (long i = 0; i <= daysBetween; i++) {
            LocalDate nextDay = start.plusDays(i);
            if (!isHolidayOrWeekend(nextDay))
                workingDays++;
        }
        return workingDays;
    }

    private String[] loadHolidays() {
        String[] holidays;
        try {
            File holidaysFile = new ClassPathResource("holidays.yaml").getFile();
            HolidaysConfig config = mapper.readValue(holidaysFile, HolidaysConfig.class);
            holidays = config.getHolidays();
        } catch (IOException e) {
            e.printStackTrace();
            holidays = new String[0];
        }
        return holidays;
    }

}
