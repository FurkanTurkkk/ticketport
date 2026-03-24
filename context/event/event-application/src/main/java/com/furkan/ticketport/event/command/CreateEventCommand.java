package com.furkan.ticketport.event.command;

import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.Title;

/**
 * Taslak etkinlik oluşturur. Slug yayın öncesi {@code assignSlug}; kapak URL’si upload sonrası string olarak gelir.
 *
 * @param description açıklama yoksa {@code null} veya boş
 * @param coverImageRef kapak yoksa {@code null} veya boş; varsa URL veya storage key
 */
public record CreateEventCommand(
        CategoryId categoryId, Title title, String description, String coverImageRef) {}
