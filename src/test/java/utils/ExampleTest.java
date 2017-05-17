package utils;


import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExampleTest {

    @Before
    public void setUp() {

    }

    @Test
    public void 오전() throws Exception {
        DateMessageProvider provider = new DateMessageProvider(currentCalender(11));
        assertThat(provider.getDateMessage(), is("오전"));
    }

    @Test
    public void 오후() throws Exception {
        DateMessageProvider provider = new DateMessageProvider(currentCalender(13));
        assertThat(provider.getDateMessage(), is("오후"));
    }

    public Calendar currentCalender(int currentTime) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, currentTime);
        return now;
    }
}