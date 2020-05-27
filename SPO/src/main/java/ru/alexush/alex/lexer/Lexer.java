package ru.alexush.alex.lexer;

import ru.alexush.alex.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Lexer {

    private final String rawInput;

    public Lexer(String rawInput) {
        this.rawInput = rawInput;
    }

    public String getRawInput() {
        return rawInput;
    }

    public List<Token> tokens() {
        String source = rawInput;
        int currentIndex = 0;
        int currentIndexFrom = 0;

        boolean okWaiting = true;
        List<Token> tokens = new ArrayList<>();
        Lexem prevLexem = null;

        String acc = "";
        while (currentIndex < source.length()) {
            acc = source.substring(currentIndexFrom, currentIndex + 1);
            Lexem currentLexem = null;
            for (Lexem lexem : Lexem.values()) {
                Matcher matcher = lexem.getPattern().matcher(acc);
                if (matcher.find()) {
                    currentLexem = lexem;
                }
            }
            if (currentLexem != null) {
                prevLexem = currentLexem;
            }
            if (okWaiting && currentLexem != null) {
                okWaiting = false;
            }
            if (okWaiting && currentLexem == null) {
                throw new RuntimeException("Incorrect source");
            }
            if (!okWaiting && currentLexem == null) {
                //отбрасываем пробелы
                int lastSymb = acc.length()-1;
                Character ch = acc.charAt(lastSymb);
                while (acc.charAt(lastSymb-1) == ' ')
                    lastSymb--;
                Token token = new Token(prevLexem, acc.substring(0, lastSymb));
                tokens.add(token);
                okWaiting = true;
                currentIndexFrom = currentIndex;
            } else {
                currentIndex = currentIndex + 1;
            }
        }

        Token token = new Token(prevLexem, acc);
        tokens.add(token);
        return tokens;
    }
}
