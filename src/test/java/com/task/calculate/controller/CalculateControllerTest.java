package com.task.calculate.controller;

import com.task.calculate.model.CalculationRequest;
import com.task.calculate.model.CalculationResponse;
import com.task.calculate.service.HolidayService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculateControllerTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private CalculateController calculateController;

    @Test
    public void testCalculateWithWorkingDays() {

        CalculationRequest request = new CalculationRequest();
        request.setStartDate("01.09.2023");
        request.setEndDate("15.09.2023");
        request.setSalary(3000.0);

        Mockito.when(holidayService.getWorkingDays(request.getStartDate(), request.getEndDate())).thenReturn(11);
        CalculationResponse response = calculateController.calculate(request);

        assertEquals(1100.0, response.getResult());
    }

    @Test
    public void testCalculateWithVacationDays() {

        CalculationRequest request = new CalculationRequest();
        request.setVacationDays(10);
        request.setSalary(3000.0);
        CalculationResponse response = calculateController.calculate(request);

        assertEquals(1000, response.getResult());
    }

}
