package model.pieces;

import model.Event;
import model.EventLog;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class EventTest {
    EventLog theLog = EventLog.getInstance();

    // EFFECT: checks if the Event at the given index has the given description
    protected void checkEvent(int index, String description) {
        List<Event> events = new ArrayList<>();
        theLog.iterator().forEachRemaining(events::add);
        assertEquals(description, events.get(index).getDescription());
    }
}
