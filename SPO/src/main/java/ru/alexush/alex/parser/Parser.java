package ru.alexush.alex.parser;

import ru.alexush.alex.exception.EofException;
import ru.alexush.alex.exception.LangParseException;
import ru.alexush.alex.lexer.Lexem;
import ru.alexush.alex.token.Token;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class Parser {

        private ListIterator<Token> iterator;

        private final List<Token> tokens;

        private Stack<Integer> depths;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.depths = new Stack<>();
        this.iterator = tokens.listIterator();
    }
//=============================================================================================================
    public void lang() throws LangParseException, EofException {
        while(true) {
            expr();
        }
    }
//-----------------------------------------------------------------(ветви)
    private void expr() throws LangParseException, EofException {
        depths.push(0);
        try {
            assignExpr();
        } catch (LangParseException e){
            try{
                back(depths.pop());
                depths.push(0);
                log_choice();
            } catch (LangParseException ex){
                try {
                    back(depths.pop());
                    depths.push(0);
                    log_circle();
                } catch (LangParseException exc) {
                    //back(depths.pop());
                    throw new LangParseException(
                            e.getMessage() + " / "
                                    + ex.getMessage() + " / "
                                        + exc.getMessage()
                    );
                }
            }
        }
        depths.pop();
    }
//-----------------------------------------------------------------
    private void assignExpr() throws LangParseException, EofException {
        var();
        assignOp();
        value();
        semicolon();
    }
//-----------------------------------------------------------------
//    private void complexExpr() throws LangParseException, EofException {
//        var();
//        assignOp();
//        value();
//        op();
//        value();
//        semicolon();
//    }
//-----------------------------------------------------------------(значения)
    private void value() throws LangParseException, EofException {
        depths.push(0);
        try{
            var();
        } catch (LangParseException e) {
            try {
                back(depths.pop());
                depths.push(0);
                digit();
            } catch (LangParseException ex){
                throw new LangParseException(
                        e.getMessage() + " / "
                                + ex.getMessage()
                );
            }
        }
        depths.pop();
    }
//-----------------------------------------------------------------(буквы)
    private void var() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.VAR);
    }
//-----------------------------------------------------------------(+-*/)
//    private void op() throws LangParseException, EofException {
//        match(getCurrentToken(), Lexem.PL_MI_MU_DI);
//    }
//-----------------------------------------------------------------(равно)
    private void assignOp() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.ASSIGN_OPERATION);
    }
//-----------------------------------------------------------------(числа)
    private void digit() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.DIGIT);
    }
//----------------------------------------------------------------(if)
    private void log_choice() throws LangParseException, EofException {
        if_log();
        log_body();
    }
//----------------------------------------------------------------(цикл while)
    private void log_circle() throws LangParseException, EofException {
        while_log();
        log_body();
    }
//-----------------------------------------------------------------
    private void if_log() throws LangParseException, EofException {
        if_kw();
        log_bracket();
    }
//-----------------------------------------------------------------
    private void while_log() throws  LangParseException, EofException {
        while_kw();
        log_bracket();
    }
//-----------------------------------------------------------------
    private void if_kw() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.IF_KW);
    }
//-----------------------------------------------------------------
    private void while_kw() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.WHILE_KW);
    }
//-----------------------------------------------------------------
    private void log_bracket() throws LangParseException, EofException {
        open_bracket();
        log_expression();
        close_bracket();
    }
//-----------------------------------------------------------------
    private void open_bracket() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.OPEN_BRACKET);
    }
//-----------------------------------------------------------------
    private void log_expression() throws LangParseException, EofException {
        value();
        log_op();
        value();
    }
//-----------------------------------------------------------------
    private void log_op() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.LOGIC_OPERATIONS);
    }
//-----------------------------------------------------------------
    private void close_bracket() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.CLOSE_BRACKET);
    }
//-----------------------------------------------------------------
    private void log_body() throws LangParseException, EofException {
        open_brace();
        expr();
        close_brace();
    }
//-----------------------------------------------------------------
    private void open_brace() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.OPEN_BRACE);
    }
//-----------------------------------------------------------------
    private void close_brace() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.CLOSE_BRACE);
    }
//-----------------------------------------------------------------
    private void semicolon() throws LangParseException, EofException {
        match(getCurrentToken(), Lexem.SEMICOLON);
    }
//=============================================================================================================
    private void match(Token token, Lexem lexem) throws LangParseException {
        if (!token.getLexem().equals(lexem)) {
            throw new LangParseException( lexem.name() + " expected " +
                    "but " + token.getLexem().name() + " found");
        }
    }

    private void back(int step) {
        for(int i = 0; i < step; i++){
            if(iterator.hasPrevious()){
                iterator.previous();
            }
        }
    }

    private Token getCurrentToken() throws EofException {
        if (iterator.hasNext()) {
            Token token = iterator.next();
            depths.push(depths.pop()+1);
            return token;
        }
        throw new EofException("EOF");
    }
}
