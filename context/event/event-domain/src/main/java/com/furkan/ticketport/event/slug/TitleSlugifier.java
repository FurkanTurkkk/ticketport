package com.furkan.ticketport.event.slug;

import java.text.Normalizer;
import java.util.Locale;


public final class TitleSlugifier {

    private static final int MAX_SLUG_LEN = 120;

    private TitleSlugifier() {}

    public static String toSlugCandidate(String title) {
        if (title == null || title.isBlank()) {
            return "event";
        }
        String s = title.trim().toLowerCase(Locale.forLanguageTag("tr"));
        s = foldTurkish(s);
        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        s = s.toLowerCase(Locale.ROOT);
        s = s.replaceAll("[^a-z0-9]+", "-");
        s = s.replaceAll("^-+", "").replaceAll("-+$", "");
        s = s.replaceAll("-{2,}", "-");
        if (s.length() < 2) {
            s = "event";
        }
        if (s.length() > MAX_SLUG_LEN) {
            s = s.substring(0, MAX_SLUG_LEN).replaceAll("-+$", "");
        }
        return s;
    }

    private static String foldTurkish(String s) {
        return s.replace('ı', 'i')
                .replace('ğ', 'g')
                .replace('ü', 'u')
                .replace('ş', 's')
                .replace('ö', 'o')
                .replace('ç', 'c');
    }
}
