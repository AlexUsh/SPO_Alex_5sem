package ru.alexush.alex;

import ru.alexush.alex.parser.Parser;
import ru.alexush.alex.lexer.Lexer;
import ru.alexush.alex.token.Token;
import ru.alexush.alex.poliz.Poliz;
import ru.alexush.alex.poliz.PolizCalculation;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class AlexUI {

    public static void main(String[] args) throws Exception {
        File file = new File("src/alex.txt");
        Scanner inp = new Scanner(file);
        Lexer lexer = new Lexer(inp.nextLine());
        LinkedList<Token> tokens = lexer.tokens();
        while (inp.hasNext()) {
            lexer = new Lexer(inp.nextLine());
            tokens.addAll(lexer.tokens());
        }

        System.out.println("Токен: ");
        for (Token token: tokens)
            System.out.println(token);

        Parser parser = new Parser(tokens);
        parser.lang();

        System.out.println("\nПолиз:");
        Poliz poliz = new Poliz(tokens);
        LinkedList<Token> testPoliz = poliz.makePoliz();
        for (Token token : testPoliz) {
            System.out.println(token.toString());
        }

        PolizCalculation.calculate(testPoliz);

    }
}
