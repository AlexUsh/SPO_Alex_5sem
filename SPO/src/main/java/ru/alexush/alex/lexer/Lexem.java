package ru.alexush.alex.lexer;

import java.util.regex.Pattern;

public enum Lexem {
    VAR("^[a-zA-Z][a-zA-Z0-9]*+\\s*$"),
    DIGIT("^(0|([1-9][0-9]*))\\s*$"),
    ASSIGN_OPERATIONS("^=\\s*$"),
    OP("^(\\+|-|\\*|/)\\s*$"),
    LOG_OPERATIONS("^(>|>=|==|<=|<)\\s*$"),
    BOOL("true|false"),
    TYPE("^(list|hashSet)\\s*$"),
    FUN_OPERATIONS("^(add|remove|get|)\\s*$"),
    OPEN_BRACKET("^(\\()\\s*$"),
    CLOSE_BRACKET("^(\\))\\s*$"),
    OPEN_BRACE("^(\\{)\\s*$"),
    CLOSE_BRACE("^(\\})\\s*$"),
    END_LINE("^\n$"),

    WHILE_KW("^while +\\s*$"),
    IF_KW("^if\\s*$"),

    MARK("^$"),
    MARK_INDEX("^$");

    private final Pattern pattern;

    Lexem(String regexp) {
        this.pattern = Pattern.compile(regexp);
    }

    public Pattern getPattern() {
        return pattern;
    }
}


