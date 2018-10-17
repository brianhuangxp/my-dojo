package com.ringcentral.domain;

public class Card {
    public static int OVER_FLOW_VALUE = 21;
    private CardSymbol symbol;
    private CardCour cour;

    public Card(CardSymbol symbol, CardCour cour) {
        this.symbol = symbol;
        this.cour = cour;
    }

    public CardSymbol getSymbol() {
        return symbol;
    }

    public CardCour getCour() {
        return cour;
    }

    public boolean isCardA() {
        return this.cour.isCardA();
    }

    @Override
    public String toString() {
        return String.format("%s%s", symbol.label, cour.label);
    }

    public enum CardCour {
        CARD_A(1, "A"),
        CARD_2(2, "2"),
        CARD_3(3, "3"),
        CARD_4(4, "4"),
        CARD_5(5, "5"),
        CARD_6(6, "6"),
        CARD_7(7, "7"),
        CARD_8(8, "8"),
        CARD_9(9, "9"),
        CARD_10(10, "10"),
        CARD_J(10, "J"),
        CARD_Q(10, "Q"),
        CARD_K(10, "K");


        private int value;
        private String label;

        CardCour(int value, String label) {
            this.value = value;
            this.label = label;
        }

        public int getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public boolean isCardA() {
            return this == CARD_A;
        }
    }

    public enum CardSymbol {
        Heart("♥"),     //红桃
        Club("♣"),      //梅花
        Diamond("♦"),   // 方块
        Spade("♠");      // 黑桃

        private String label;

        CardSymbol(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
