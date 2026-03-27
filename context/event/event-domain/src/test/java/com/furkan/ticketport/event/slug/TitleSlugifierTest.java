package com.furkan.ticketport.event.slug;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TitleSlugifierTest {

    @Test
    void nullAndBlankReturnEvent() {
        assertEquals("event", TitleSlugifier.toSlugCandidate(null));
        assertEquals("event", TitleSlugifier.toSlugCandidate("   "));
    }

    @Test
    void foldsTurkishAndAscii() {
        assertEquals("cok-guzel", TitleSlugifier.toSlugCandidate("Çok  Güzel"));
        assertEquals("istanbul", TitleSlugifier.toSlugCandidate("İstanbul"));
    }

    @Test
    void collapsesNonAlphanumericToHyphens() {
        assertEquals("a-b", TitleSlugifier.toSlugCandidate("a@@@b"));
    }

    @Test
    void shortAfterCleanupFallsBack() {
        assertEquals("event", TitleSlugifier.toSlugCandidate("##"));
    }

    @Test
    void truncatesToMaxLengthAndStripsTrailingHyphen() {
        String in = "x".repeat(150);
        String out = TitleSlugifier.toSlugCandidate(in);
        assertTrue(out.length() <= 120);
        assertTrue(!out.endsWith("-"));
    }
}
