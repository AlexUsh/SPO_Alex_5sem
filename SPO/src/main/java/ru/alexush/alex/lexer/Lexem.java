package ru.alexush.alex.lexer;

import java.util.regex.Pattern;

public enum Lexem {

    VAR("[a-zA-z]+", 0),
    DIGIT("0|([1-9][0-9]*)", 0),
    ASSIGN_OP("=", 0),
    PLUS_MINUS("\\+|\\-", 3),
    MULT_DIV("\\*|\\/", 4),
    LOGIC_OP(">|<|==|>=|<=", 2),
    CONST_STRING("\".*\"", 0),
    OPEN_PARANTH("\\(", 1),
    CLOSE_PARANTH("\\)", 1),
    OPEN_BRACKET("\\{", -1),
    CLOSE_BRACKET("\\}", 1),

    IF_KW("if", 5),
    WHILE_KW("while", 5),
    FOW_KW("for", 5);

    private final Pattern pattern;
    private int priority;

    Lexem(String regexp, int priority) {
        this.pattern = Pattern.compile(regexp);
        this.priority = priority;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getPriority() {
        return priority;
    }
}
