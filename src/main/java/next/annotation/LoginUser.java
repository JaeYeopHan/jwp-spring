package next.annotation;

import java.lang.annotation.*;

/**
 * Created by Jbee on 2017. 5. 11..
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
}
