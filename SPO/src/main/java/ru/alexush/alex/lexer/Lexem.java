package ru.alexush.alex.lexer;

import java.util.regex.Pattern;

public enum Lexem {

    VAR("^[a-zA-z]\\s*$", 0),
    DIGIT("^(0|([1-9][0-9]*))\\s*$", 0),
    ASSIGN_OPERATION("^=\\s*$", 0),
    LOGIC_OPERATIONS("^>|<|==|>=|<=\\s*$", 2),
    PL_MI_MU_DI("^(\\+|-|\\*|/)\\s*$", 3),
    OPEN_BRACKET("^\\(\\s*$", 1),
    CLOSE_BRACKET("^\\)\\s*$", 1),
    OPEN_SQUARE("^\\[\\s*$", 1),
    CLOSE_SQUARE("^\\]\\s*$", 1),
    OPEN_BRACE("^\\{\\s*$", -1),
    CLOSE_BRACE("^\\}\\s*$", 1),

    SEMICOLON("^;\\s*$", 10),

    IF_KW("^if\\s*$", 5),
    WHILE_KW("^while\\s*$", 5),
    FOW_KW("^for\\s*$", 5);

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
