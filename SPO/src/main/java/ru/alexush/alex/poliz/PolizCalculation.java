package ru.alexush.alex.poliz;

import ru.alexush.alex.list.CustomLinkedList;
import ru.alexush.alex.lexer.Lexem;
import ru.alexush.alex.token.Token;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class PolizCalculation {
    public static Map<String, String> types = new HashMap<>();
    public static Map<String, Object> values  = new HashMap<>();

    public static void calculate(LinkedList<Token> poliz) throws Exception {
        Stack<Token> stack = new Stack<>();
        for (int i = 0; i < poliz.size(); i++) {
            Token token = poliz.get(i);

            if (token.getLexem() == Lexem.VAR || token.getLexem() == Lexem.DIGIT ||
                    token.getLexem() == Lexem.MARK_INDEX || token.getLexem() == Lexem.TYPE) {
                stack.add(token);
            }

            if (token.getLexem() == Lexem.TYPE) {
                i = i+1;
                Token var = poliz.get(i);
                if (!values.containsKey(var.getValue())) {
                    types.put(var.getValue(), token.getValue());
                    values.put(var.getValue(), null);
                }
            }

            if (token.getLexem() == Lexem.FUN_OPERATIONS) {
                Token t2 = stack.pop();
                Token t1 = stack.pop();
                if (values.get(t1.getValue()) == null) {
                    if (types.get(t1.getValue()).equals("list")) {
                        values.put(t1.getValue(), new CustomLinkedList());
                    } else if (types.get(t1.getValue()).equals("hashSet")) {
//                        values.put(t1.getValue(), new CustomHashSet());
                    }
                }
                if (types.get(t1.getValue()).equals("list")) {
                    if (t2.getLexem() == Lexem.DIGIT) {
                        if (token.getValue().equals("add")) {
                            ((CustomLinkedList) values.get(t1.getValue())).add(Integer.parseInt(t2.getValue()));
                        } else if (token.getValue().equals("get")) {
                            stack.add(new Token(Lexem.DIGIT, Integer.toString(((CustomLinkedList) values.get(t1.getValue())).get(Integer.parseInt(t2.getValue())))));
                        } else if (token.getValue().equals("remove")) {
                            ((CustomLinkedList) values.get(t1.getValue())).remove(Integer.parseInt(t2.getValue()));
                        } else if (token.getValue().equals("contains")) {
                            Boolean.toString(((CustomLinkedList) values.get(t1.getValue())).contains(Integer.parseInt(t2.getValue())));
                        }
                    } else if (t2.getLexem() == Lexem.VAR) {
                        if (token.getValue().equals("add")) {
                            ((CustomLinkedList) values.get(t1.getValue())).add((Integer) (values.get(t2.getValue())));
                        } else if (token.getValue().equals("get")) {
                            stack.add(new Token(Lexem.DIGIT, Integer.toString(((CustomLinkedList) values.get(t1.getValue())).get((Integer) values.get(t2.getValue())))));
                        } else if (token.getValue().equals("remove")) {
                            ((CustomLinkedList) values.get(t1.getValue())).remove((Integer) (values.get(t2.getValue())));
                        }
                    }
                }
            }

            if (token.getLexem() == Lexem.LOG_OPERATIONS) {
                int a1, a2;
                Token t2 = stack.pop();
                Token t1 = stack.pop();
                if (t1.getLexem() == Lexem.VAR) {
                    a1 = (Integer) values.get(t1.getValue());
                } else {
                    a1 = Integer.parseInt(t1.getValue());
                }
                if (t2.getLexem() == Lexem.VAR) {
                    a2 = (Integer) values.get(t2.getValue());
                } else {
                    a2 = Integer.parseInt(t2.getValue());
                }
                if (token.getValue().equals("==")) {
                    if (a1 == a2) {
                        stack.add(new Token(Lexem.BOOL, "true"));
                    } else {
                        stack.add(new Token(Lexem.BOOL, "false"));
                    }
                }
                if (token.getValue().equals("<=")) {
                    if (a1 <= a2) {
                        stack.add(new Token(Lexem.BOOL, "true"));
                    } else {
                        stack.add(new Token(Lexem.BOOL, "false"));
                    }
                }
                if (token.getValue().equals(">=")) {
                    if (a1 >= a2) {
                        stack.add(new Token(Lexem.BOOL, "true"));
                    } else {
                        stack.add(new Token(Lexem.BOOL, "false"));
                    }
                }
                if (token.getValue().equals("<")) {
                    if (a1 < a2) {
                        stack.add(new Token(Lexem.BOOL, "true"));
                    } else {
                        stack.add(new Token(Lexem.BOOL, "false"));
                    }
                }
                if (token.getValue().equals(">")) {
                    if (a1 > a2) {
                        stack.add(new Token(Lexem.BOOL, "true"));
                    } else {
                        stack.add(new Token(Lexem.BOOL, "false"));
                    }
                }
                if (token.getValue().equals("!=")) {
                    if (a1 != a2) {
                        stack.add(new Token(Lexem.BOOL, "true"));
                    } else {
                        stack.add(new Token(Lexem.BOOL, "false"));
                    }
                }
            }

            if (token.getLexem() == Lexem.ASSIGN_OPERATIONS) {
                int a2;
                Token t1, t2;
                if (!stack.empty())
                    t2 = stack.pop();
                else
                    continue;
                if (!stack.empty())
                    t1 = stack.pop();
                else
                    continue;
                if (t2.getLexem() == Lexem.VAR && stack.peek().getValue() != "list") {
                    a2 = (Integer) values.get(t2.getValue());
                } else {
                    a2 = Integer.parseInt(t2.getValue());
                }
                values.put(t1.getValue(), a2);
            }

            if (token.getLexem() == Lexem.OP) {
                int a1, a2;
                Token t1, t2;
                if (!stack.empty())
                    t2 = stack.pop();
                else
                    continue;
                if (!stack.empty())
                    t1 = stack.pop();
                else
                    continue;
                if (t1.getLexem() == Lexem.VAR) {
                    a1 = (Integer) values.get(t1.getValue());
                } else {
                    a1 = Integer.parseInt(t1.getValue());
                }
                if (t2.getLexem() == Lexem.VAR) {
                    a2 = (Integer) values.get(t2.getValue());
                } else {
                    a2 = Integer.parseInt(t2.getValue());
                }
                if (token.getValue().equals("*")) {
                    stack.add(new Token(Lexem.DIGIT, Integer.toString(a1 * a2)));
                }
                if (token.getValue().equals("/")) {
                    stack.add(new Token(Lexem.DIGIT, Integer.toString(a1 / a2)));
                }
                if (token.getValue().equals("+")) {
                    stack.add(new Token(Lexem.DIGIT, Integer.toString(a1 + a2)));
                }
                if (token.getValue().equals("-")) {
                    stack.add(new Token(Lexem.DIGIT, Integer.toString(a1 - a2)));
                }
            }

            if (token.getLexem() == Lexem.MARK) {
                Token index = stack.pop();

                if (token.getValue().equals("!F")) {
                    Token res = stack.pop();
                    if (res.getValue().equals("true")) {
                        continue;
                    } else {
                        i = Integer.parseInt(index.getValue()) - 1;
                    }
                } else if (token.getValue().equals("!if")) {
                    continue;
                } else {
                    i = Integer.parseInt(index.getValue()) - 1;
                }
            }
        }

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
