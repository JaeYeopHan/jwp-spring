package utils;

import java.util.Calendar;

/**
 * Created by Jbee on 2017. 5. 12..
 */
public class DateMessageProvider {

    private Calendar now;

    public DateMessageProvider(Calendar now) {
        this.now = now;
    }

    public String getDateMessage() {
        return new Hour(now.get(Calendar.HOUR_OF_DAY)).getMessage();
    }


    private class Hour {
        private int hour;
        public Hour(int hour) {
            this.hour = hour;
        }
        public String getMessage() {
            if (this.hour < 12) {
                return "오전";
            }

            return "오후";
        }
    }
}
