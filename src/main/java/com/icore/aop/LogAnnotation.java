package com.icore.aop;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAnnotation {

        /**
         * default extension name
         */
        String value() default "";

}
