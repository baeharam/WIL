import java.io.*;
import java.util.*;

public class SmallJS {
    public static void main(String[] args) {
        if(args.length==0) {
            System.out.println("사용법: java SmallJS \"파일경로\"");
        } else {
            Lexer lexer = new Lexer(args[0]);
            lexer.doTokenize();
            lexer.doPrintAllTokens();
        }
    }
}

class Lexer {
    private List<String> keywords;
    private BufferedReader reader;
    private Map<TokenType, Set<String>> tokens = new HashMap<>();

    Lexer(String path) {
        keywordsInitialization();
        fileReaderInitialization(path);
        tokenInitialization();
    }

    private void keywordsInitialization() {
        keywords = new ArrayList<>();
        keywords.add("<script_start>");
        keywords.add("var");
        keywords.add("window.prompt");
        keywords.add("parseFloat");
        keywords.add("while");
        keywords.add("if");
        keywords.add("else");
        keywords.add("document.write");
        keywords.add("document.writeln");
        keywords.add("for");
        keywords.add("switch");
        keywords.add("case");
        keywords.add("break");
        keywords.add("default");
        keywords.add("false");
        keywords.add("do");
        keywords.add("function");
        keywords.add("return");
        keywords.add("<script_end>");
    }

    private void fileReaderInitialization(String path) {
        try {
            reader = new BufferedReader(new FileReader(new File(path)));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void tokenInitialization() {
        tokens.put(TokenType.KEYWORD_IDENTIFIER,new HashSet<>());
        tokens.put(TokenType.USER_DEFINED_IDENTIFIER,new HashSet<>());
        tokens.put(TokenType.FUNCTION_IDENTIFIER,new HashSet<>());
        tokens.put(TokenType.LITERAL,new HashSet<>());
        tokens.put(TokenType.OPERATOR,new HashSet<>());
        tokens.put(TokenType.SPECIAL_CHARACTER,new HashSet<>());
        tokens.put(TokenType.DIGIT,new HashSet<>());
        tokens.put(TokenType.TAG,new HashSet<>());
        tokens.put(TokenType.FUNCTION_PARAMETER,new HashSet<>());
    }

    void doTokenize()
    {
        StringBuilder s = new StringBuilder();
        try {
            int ch = reader.read();
            while(true) {
                // Termination condition
                if(ch==-1) break;

                // Identifier
                else if(isFirstIdentifierChar(ch)) {
                    ch = processIdentifier(s.append((char)ch));
                }

                // Digit
                else if(Character.isDigit(ch)) {
                    ch = processDigit(s.append((char)ch));
                }

                // Other special characters
                else {
                    ch = processSpecialCharacter(s.append((char)ch));
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private int processIdentifier(StringBuilder s) throws IOException {
        int ch = reader.read();
        while(isSecondIdentifierChar(ch)) {
            s.append((char)ch);
            ch = reader.read();
        }
        if(ch=='>') {
            s.append('>');
            tokens.get(TokenType.KEYWORD_IDENTIFIER).add(s.toString());
            ch = reader.read();
        }
        else if(ch=='('
                && !s.toString().equals("if")
                && !s.toString().equals("switch")
                && !s.toString().equals("for")
                && !s.toString().equals("while")) {
            tokens.get(TokenType.FUNCTION_IDENTIFIER).add(s.toString());
            s.setLength(0);
            ch = processFunction(s.append((char)ch));
        }
        else if(keywords.contains(s.toString())) {
            tokens.get(TokenType.KEYWORD_IDENTIFIER).add(s.toString());
        } else {
            tokens.get(TokenType.USER_DEFINED_IDENTIFIER).add(s.toString());
        }
        s.setLength(0);
        return ch;
    }

    private int processFunction(StringBuilder s) throws  IOException {
        int ch = reader.read();
        boolean isLiteral = false;
        while(ch!=')') {
            if(ch=='\"') {
                isLiteral = true;
                ch = processLiteral(s.append((char)ch),true);
            }
            else {
                s.append((char)ch);
                ch = reader.read();
            }
        }
        if(!isLiteral) {
            s.append((char)ch);
            ch = reader.read();
            tokens.get(TokenType.FUNCTION_PARAMETER).add(s.toString());
            s.setLength(0);
        }
        return ch;
    }

    private int processLiteral(StringBuilder s, boolean isFunction) throws IOException {
        int ch = reader.read();
        while(ch!='\"') {
            if(ch=='<' ) {
                ch = processTag(new StringBuilder().append((char)ch));
            } else {
                s.append((char)ch);
                ch = reader.read();
            }
        }
        s.append((char)ch);
        ch = reader.read();
        if(isFunction) {
            tokens.get(TokenType.FUNCTION_PARAMETER).add(s.toString()+")");
        }
        tokens.get(TokenType.LITERAL).add(s.toString().replace("(",""));
        s.setLength(0);
        return ch;
    }

    private int processTag(StringBuilder s) throws  IOException {
        int ch = reader.read();
        while(ch!='>') {
            s.append((char)ch);
            ch = reader.read();
        }
        s.append((char)ch);
        tokens.get(TokenType.TAG).add(s.toString());
        ch = reader.read();
        return ch;
    }

    private int processDigit(StringBuilder s) throws IOException {
        int ch = reader.read();
        while(Character.isDigit(ch)) {
            s.append((char)ch);
            ch = reader.read();
        }
        tokens.get(TokenType.DIGIT).add(s.toString());
        s.setLength(0);
        return ch;
    }

    private int processComment() throws IOException {
        int ch = reader.read();
        while(ch!='\n'){
            ch = reader.read();
        }
        return ch;
    }

    private int processSpecialCharacter(StringBuilder s) throws IOException {
        int ch = s.toString().charAt(0);
        switch(ch) {
            case ' ':
            case '\n':
            case '\r':
            case '\t':
                ch = reader.read();
                break;
            case '/':
                ch = processComment();
                break;
            case '<':
                ch = reader.read();
                if(isFirstIdentifierChar(ch)) {
                    ch = processIdentifier(s.append((char)ch));
                } else {
                    if(ch=='=') {
                        tokens.get(TokenType.OPERATOR).add("<=");
                        ch = reader.read();
                    } else {
                        tokens.get(TokenType.OPERATOR).add("<");
                    }
                }
                break;
            case '\"':
                ch = processLiteral(s,false);
                break;
            case '=':
                ch = reader.read();
                if(ch=='=') {
                    tokens.get(TokenType.OPERATOR).add("==");
                    ch = reader.read();
                } else {
                    tokens.get(TokenType.OPERATOR).add("=");
                }
                break;
            case '+':
                ch = reader.read();
                if(ch=='+') {
                    tokens.get(TokenType.OPERATOR).add("++");
                    ch = reader.read();
                } else if(ch=='=') {
                    tokens.get(TokenType.OPERATOR).add("+=");
                    ch = reader.read();
                } else {
                    tokens.get(TokenType.OPERATOR).add("+");
                }
                break;
            case '*':
                tokens.get(TokenType.OPERATOR).add("*");
                ch = reader.read();
                break;
            case '>':
                ch = reader.read();
                if(ch=='=') {
                    tokens.get(TokenType.OPERATOR).add(">=");
                    ch = reader.read();
                } else {
                    tokens.get(TokenType.OPERATOR).add(">");
                }
                break;
            default:
                tokens.get(TokenType.SPECIAL_CHARACTER).add(String.valueOf((char)ch));
                ch = reader.read();

        }
        s.setLength(0);
        return ch;
    }

    private boolean isFirstIdentifierChar(int c) {
        return Character.isLetter(c);
    }

    private boolean isSecondIdentifierChar(int c) {
        return Character.isLetter(c) || Character.isDigit(c) ||
                c=='.' || c=='_';
    }

    void doPrintAllTokens() {
        System.out.println("Scanning Starts!");
        System.out.println("[KEYWORD_IDENTIFIER]: ");
        for(String s : tokens.get(TokenType.KEYWORD_IDENTIFIER)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[USER_DEFINED_IDENTIFIER]: ");
        for(String s : tokens.get(TokenType.USER_DEFINED_IDENTIFIER)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[FUNCTION_IDENTIFIER]: ");
        for(String s : tokens.get(TokenType.FUNCTION_IDENTIFIER)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[FUNCTION_PARAMETER]: ");
        for(String s : tokens.get(TokenType.FUNCTION_PARAMETER)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[LITERAL]: ");
        for(String s : tokens.get(TokenType.LITERAL)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[OPERATOR]: ");
        for(String s : tokens.get(TokenType.OPERATOR)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[DIGIT]: ");
        for(String s : tokens.get(TokenType.DIGIT)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[SPECIAL_CHARACTER]: ");
        for(String s : tokens.get(TokenType.SPECIAL_CHARACTER)) {
            System.out.print(s+", ");
        }
        System.out.println();
        System.out.println("[EXTRACTED TAG]: ");
        for(String s : tokens.get(TokenType.TAG)) {
            System.out.print(s+", ");
        }
    }

}

enum TokenType {
    KEYWORD_IDENTIFIER,
    USER_DEFINED_IDENTIFIER,
    FUNCTION_IDENTIFIER,
    FUNCTION_PARAMETER,
    SPECIAL_CHARACTER,
    LITERAL,
    DIGIT,
    TAG,
    OPERATOR
}