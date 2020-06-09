package ru.alexush.alex.parser;

import ru.alexush.alex.lexer.Lexem;
import ru.alexush.alex.token.Token;

import java.util.LinkedList;

public class Parser {
    private Token token;
    private Token tempVar;
    private LinkedList<Token> tokens = new LinkedList<>();
    private LinkedList<Token> expression = new LinkedList<>();

    public Parser(LinkedList<Token> tokens) {
        this.tokens.addAll(tokens);
    }

    public void lang() throws Exception {
        while (!tokens.isEmpty()) {
            expr();
        }
    }
    private void expr() throws Exception {
        try {
            expression.clear();
            while_log();
        }
        catch (Exception ex) {
            try {
                while (expression.size() > 0) {
                    if (tokens.getFirst() != expression.getLast())
                        tokens.addFirst(expression.pop());

                }
                declarationExpr();
            }
            catch (Exception ex1) {
                try {
                    while (expression.size() > 0) {
                        if (tokens.getFirst() != expression.getLast())
                            tokens.addFirst(expression.pop());
                    }
                    funExpr();
                }
                catch (Exception ex2) {
                    try {
                        while (expression.size() > 0) {
                            if (tokens.getFirst() != expression.getLast())
                                tokens.addFirst(expression.pop());
                        }
                        assignExpr();
                    } catch (Exception ex3) {
                        try {
                            while (expression.size() > 0) {
                                if (tokens.getFirst() != expression.getLast())
                                    tokens.addFirst(expression.pop());
                            }
                            condition();
                        } catch (Exception ex4) {
                            try {
                                while (expression.size() > 0) {
                                    if (tokens.getFirst() != expression.getLast())
                                        tokens.addFirst(expression.pop());
                                }
                                getAssign();
                            } catch (Exception ex5) {
                                endLine();
                            }
                        }
                    }
                }
            }
        }
    }

    private void condition() throws Exception {
        conditionOp();
        term();
        body();
    }
    private void funExpr() throws Exception {
        var();
        funOp();
        arExpr();
        endLine();
    }

    private void while_log() throws Exception {
        cycle();
        term();
        body();
    }

    private void getAssign() throws Exception {
        var();
        assignOp();
        var();
        funOp();
        try {
            var();
        } catch (Exception e) {
            digit();
        }
    }

    private void term() throws Exception {
        boolExpr();
    }

    private void boolExpr() throws Exception {
        try {
            arExpr();
        }
        catch (Exception ex) {
            operand();
        }
        boolOp();
        try {
            arExpr();
        }
        catch (Exception ex) {
            operand();
        }
    }

    private void operand() throws Exception {
        try {
            var();
        }
        catch (Exception ex) {
            try {
                digit();
            }
            catch (Exception ex2) {
                bracketExpr();
            }
        }
    }

    private void bracketExpr() throws Exception {
        openParenthesis();
        inBrackets();
        closeParenthesis();
    }

    private void inBrackets() throws Exception {
        try {
            bracketExpr();
        }
        catch (Exception ex) {
            arExpr();
        }
    }

    private void arExpr() throws Exception {
        operand();
        while (true) {
            try {
                ariphmeticOp();
                operand();
            } catch (Exception ex) {
                return;
            }
        }
    }

    private void body() throws Exception {
        openBrace();
        try {
            endLine();
        } catch (Exception e) {

        }
        bodyExpr();
        try {
            endLine();
        } catch (Exception e) {

        }
        closeBrace();
    }

    private void bodyExpr() throws Exception {
        expr();
        while (true) {
            try {
                expr();
            } catch (Exception ex) {
                return;
            }
        }
    }

    private void assignExpr() throws Exception {
        var();
        assignOp();
        try {
            arExpr();
        }
        catch (Exception ex) {
            operand();
        }
        endLine();
    }

    private void declarationExpr() throws Exception { //объявление списка
        type();
        var();
        endLine();
    }
    private void match() {
        token = tokens.peek();
    }

    private void cycle() throws Exception {
        match();
        if (token.getLexem() != Lexem.WHILE_KW) {
            throw new Exception("Ошибка в cycle, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void funOp() throws Exception {
        match();
        if (token.getLexem() != Lexem.FUN_OPERATIONS) {
            throw new Exception("Ошибка funOp на токене " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void type() throws Exception {
        match();
        if (token.getLexem() != Lexem.TYPE) {
            throw new Exception("Ошибка type, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void openParenthesis() throws Exception {
        match();
        if (token.getLexem() != Lexem.OPEN_BRACKET) {
            throw new Exception("Ошибка openParenthesis, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void closeParenthesis() throws Exception {
        match();
        if (token.getLexem() != Lexem.CLOSE_BRACKET) {
            throw new Exception("Ошибка в closeParenthesis, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void boolOp() throws Exception {
        match();
        if (token.getLexem() != Lexem.LOG_OPERATIONS) {
            throw new Exception("Ошибка boolOp, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void var() throws Exception {
        match();
        if (token.getLexem() != Lexem.VAR) {
            throw new Exception("Ошибка var, токен: " + token.toString());
        }
        expression.add(token);
        tempVar = tokens.poll();
    }

    private void digit() throws Exception {
        match();
        if (token.getLexem() != Lexem.DIGIT) {
            throw new Exception("Ошибка в digit, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }
    private void ariphmeticOp() throws Exception {
        match();
        if (token.getLexem() != Lexem.OP) {
            throw new Exception("Ошибка ariphmeticOp, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void openBrace() throws Exception {
        match();
        if (token.getLexem() != Lexem.OPEN_BRACE) {
            throw new Exception("Ошибка openBrace, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void closeBrace() throws Exception {
        match();
        if (token.getLexem() != Lexem.CLOSE_BRACE) {
            throw new Exception("Ошибка closeBrace, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void assignOp() throws Exception {
        match();
        if (token.getLexem() != Lexem.ASSIGN_OPERATIONS) {
            throw new Exception("Ошибка assignOp, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void endLine() throws Exception {
        match();
        if (token.getLexem() != Lexem.END_LINE) {
            throw new Exception("Ошибка endLine, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }

    private void conditionOp() throws Exception {
        match();
        if (token.getLexem() != Lexem.IF_KW) {
            throw new Exception("Ошибка conditionOp, токен: " + token.toString());
        }
        expression.add(token);
        tokens.remove();
    }
}
