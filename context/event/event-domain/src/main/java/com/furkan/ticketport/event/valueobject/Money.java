package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public final class Money {

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money create(BigDecimal amount, Currency currency) {
        Currency resolved = currency != null ? currency : Currency.getInstance("TRY");
        validateStrictlyPositive(amount);
        return new Money(copyNormalized(amount, resolved), resolved);
    }

    public static Money free(Currency currency) {
        Currency resolved = currency != null ? currency : Currency.getInstance("TRY");
        int scale = defaultFractionDigits(resolved);
        return new Money(BigDecimal.ZERO.setScale(scale, RoundingMode.UNNECESSARY), resolved);
    }

    public BigDecimal amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    private static void validateStrictlyPositive(BigDecimal amount) {
        if (amount == null) {
            throw new InvalidAmountException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount cannot be negative");
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidAmountException("Amount must be positive; use Money.free(currency) for zero");
        }
    }

    private static BigDecimal copyNormalized(BigDecimal amount, Currency currency) {
        return amount.setScale(defaultFractionDigits(currency), RoundingMode.HALF_UP);
    }

    private static int defaultFractionDigits(Currency currency) {
        int fd = currency.getDefaultFractionDigits();
        return fd < 0 ? 2 : fd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + currency.getCurrencyCode();
    }
}
