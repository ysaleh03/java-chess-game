package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntryTest {
    private Entry entry;

    @BeforeEach
    void beforeEach() {
        entry = new Entry("Foo", 100);
    }

    @Test
    void constructorTest() {
        assertEquals("Foo", entry.getName());
        assertEquals(100, entry.getTurns());
    }
}
