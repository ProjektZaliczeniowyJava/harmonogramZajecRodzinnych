package sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {
    private Event event;

    @Before
    public void setUp() {
        this.event = new Event(2,5, "NIEDZIELA",1, 1, "SampleEventMessage");
    }

    @Test
    public void eventIdShouldBeTheSame() {
        assertEquals(2, this.event.getId());
    }

    @Test
    public void userIdShouldBeTheSame() {
        assertEquals(5, this.event.getId_user());
    }

    @Test
    public void dayShouldBeTheSame() {
        assertEquals("NIEDZIELA", this.event.getDay());
    }

    @Test
    public void hourShouldBeTheSame() {
        assertEquals(1, this.event.getHour());
    }

    @Test
    public void minShouldBeTheSame() {
        assertEquals(1, this.event.getMin());
    }

    @Test
    public void messageShouldBeTheSame() {
        assertEquals("SampleEventMessage", this.event.getMessage());
    }


}