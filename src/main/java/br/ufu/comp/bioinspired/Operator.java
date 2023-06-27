package br.ufu.comp.bioinspired;

public enum Operator {
    EQUAL("="),
    NOT_EQUAL("!="),
    GREATER_EQUAL_THAN(">="),
    LESS_THAN("<");

    private final String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
