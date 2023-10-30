package se.sundsvall.incident.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Category {
    BELYSNING(1, "Belysning"),
    BRO_TUNNEL_KONSTRUKTION(2, "Bro/Tunnel/Konstruktion"),
    FELPARKERAD_BIL(3, "Felparkerat och övergivet fordon"),
    GANGCYKELVAG(4, "Gång- och cykelväg"),
    KLOTTER(5, "Klotter"),
    PARK_SKOG_LEKPLATS(6, "Park, skog, bad- och lekplats"),
    PARKERING(7, "Parkering"),
    RENHALLNING(8, "Renhållning"),
    SKYLT_VAGMARKE(9, "Skylt och vägmärke"),
    TRAFIKSIGNAL(10, "Trafiksignal"),
    VAG_GATA(11, "Väg/gata"),
    VAGMARKERING(12, "Vägmarkering"),
    OVRIGT(13, "Övrigt"),
    LIVBOJ(15, "Livboj"),
    LIVBAT(16, "Livbåt"),
    VATTENMATARE(17, "Felanmälan vattenmätare"),
    BRADD_OVERVAKNINGS_LARM(18, "Bräddövervakningslarm");

    private final int value;
    private final String label;

    Category(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static Category forValue(final int value) {
        return Arrays.stream(Category.values())
                .filter(status -> value == status.value)
                .findFirst()
                .orElse(null);
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
