package ru.alexush.alex;

import ru.alexush.alex.exception.EofException;
import ru.alexush.alex.exception.LangParseException;
import ru.alexush.alex.parser.Parser;
import ru.alexush.alex.lexer.Lexer;
import ru.alexush.alex.token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class AlexUI {

    public static void main(String[] args) throws FileNotFoundException, LangParseException {
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

        Parser parser = new Parser(tokens);
        try {
            parser.lang();
        } catch (EofException e){

        }
    }
}
