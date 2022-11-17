package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void equalsTest() {
        assertEquals(entry, entry);
        assertNotEquals(entry, null);
        assertNotEquals(entry, "string");
        assertNotEquals(entry, new Entry("Foo", 90));
        assertNotEquals(entry, new Entry("Bar", 100));
        assertEquals(entry, new Entry("Foo", 100));
    }

    @Test
    void hashcodeTest() {
        assertNotEquals(entry.hashCode(), new Entry("Foo", 90).hashCode());
        assertNotEquals(entry.hashCode(), new Entry("Bar", 100).hashCode());
        assertEquals(entry.hashCode(), new Entry("Foo", 100).hashCode());
    }
}
