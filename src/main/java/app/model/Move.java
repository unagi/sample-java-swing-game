package app.model;

public enum Move {
    ZAN("斬"),
    TSUKI("突"),
    UCHI("打");

    private final String label;

    Move(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Outcome outcomeAgainst(Move other) {
        if (this == other) return Outcome.NONE;
        switch (this) {
            case ZAN:
                return other == UCHI ? Outcome.PLUS : Outcome.MINUS;
            case UCHI:
                return other == TSUKI ? Outcome.PLUS : Outcome.MINUS;
            case TSUKI:
                return other == ZAN ? Outcome.PLUS : Outcome.MINUS;
            default:
                return Outcome.NONE;
        }
    }
}

