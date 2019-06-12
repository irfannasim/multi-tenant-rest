package com.rd.mtr.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
public @interface Column {
    String name();

    /**
     * used when the field type is ZonedDateTime.  the timestamp will be converted
     * to ZonedDateTime in this timezone.
     *
     * @return
     */
    String tz() default "UTC";

    /**
     * used when the field type is BigDecimal.
     *
     * @return
     */
    int scale() default 2;
}
