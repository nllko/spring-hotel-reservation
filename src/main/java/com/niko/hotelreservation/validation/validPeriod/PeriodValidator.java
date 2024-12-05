package com.niko.hotelreservation.validation.validPeriod;

import com.niko.hotelreservation.DTOs.PeriodDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodValidator implements ConstraintValidator<ValidPeriod, PeriodDTO> {
    @Override
    public boolean isValid(PeriodDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getCheckIn() == null || value.getCheckOut() == null) {
            return true;
        }
        return value.getCheckIn().isBefore(value.getCheckOut());
    }
}
