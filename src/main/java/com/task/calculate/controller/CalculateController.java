package com.task.calculate.controller;

import com.task.calculate.model.CalculationRequest;
import com.task.calculate.model.CalculationResponse;
import com.task.calculate.service.HolidayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.task.calculate.util.NumberUtils.roundDouble;

@RestController
public class CalculateController {

    HolidayService holidayService;

    public CalculateController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/calculate")
    public CalculationResponse calculate(@RequestBody CalculationRequest request) {
        CalculationResponse response = new CalculationResponse();
        double result;

        if (request.getStartDate() != null && request.getEndDate() != null) {
            int workingDays = holidayService.getWorkingDays(request.getStartDate(), request.getEndDate());
            result = (request.getSalary() / 30) * workingDays;
        } else {
            result = (request.getSalary() / 30) * request.getVacationDays();
        }

        response.setResult(roundDouble(result));

        return response;
    }
}
