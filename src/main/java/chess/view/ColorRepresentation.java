package chess.view;

import chess.model.piece.Color;

public enum ColorRepresentation {
    WHITE("흰"),
    BLACK("검");

    private final String value;

    ColorRepresentation(String value) {
        this.value = value;
    }

    public static String mappingColor(Color color) {
        return ColorRepresentation.valueOf(color.name()).getValue();
    }

    private String getValue() {
        return value;
    }
}
