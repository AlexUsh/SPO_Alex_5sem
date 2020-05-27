package ru.alexush.alex;

import ru.alexush.alex.lexer.Lexer;
import ru.alexush.alex.token.Token;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AlexUI {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/alex.txt");
        Scanner inp = new Scanner(file);
        Lexer lexer = new Lexer(inp.nextLine());
        List<Token> tokens = lexer.tokens();
        while (inp.hasNext()) {
            lexer = new Lexer(inp.nextLine());
            tokens.addAll(lexer.tokens());
        }

        for (Token token: tokens) {
            System.out.println(token);
        }

    }

}
