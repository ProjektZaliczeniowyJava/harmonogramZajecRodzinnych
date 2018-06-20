package sample;

import org.junit.Before;
import org.junit.Test;
import sample.utilities.Event;
import sample.views.EventField;

import static org.junit.Assert.*;

public class EventFieldTest {
    private EventField eventField;
    @Before
    public void setUp() {
        Event event = new Event(1,1,"PONIEDZIAŁEK",1,1,"SampleEvent");
        eventField = new EventField(event);
    }

    @Test
    public void idDayShouldBeTheSame() {
        assertEquals(0, eventField.getDayId());
    }

    @Test
    public void idEventShouldBeTheSame() {
        assertEquals(1, eventField.getEventId());
    }

    @Test
    public void hourShouldBeTheSame() {
        assertEquals(1, eventField.getHour());
    }

}