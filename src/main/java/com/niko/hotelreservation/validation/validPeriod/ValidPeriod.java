package com.niko.hotelreservation.validation.validPeriod;

import com.niko.hotelreservation.constants.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeriodValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriod {
    String message() default Messages.VALIDATION_PERIOD_INVALID;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
