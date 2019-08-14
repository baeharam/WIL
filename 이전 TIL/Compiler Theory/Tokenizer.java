import java.io.*;
import java.util.*;

class Token {
    private TokenType type;
    private String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    TokenType getType() {
        return this.type;
    }

    String getValue() {
        return this.value;
    }
}

class Tokenizer {
    private List<String> keywords;
    private BufferedReader reader;
    private List<Token> tokenList = new ArrayList<Token>();

    Tokenizer(String path) {
        keywordsInitialization();
        fileReaderInitialization(path);
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

    List<Token> getTokenList() {
        return this.tokenList;
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
            tokenList.add(new Token(TokenType.KEYWORD_IDENTIFIER,s.toString()));
            ch = reader.read();
        }
        else if(ch=='('
                && !s.toString().equals("if")
                && !s.toString().equals("switch")
                && !s.toString().equals("for")
                && !s.toString().equals("while")) {
            tokenList.add(new Token(TokenType.FUNCTION_IDENTIFIER,s.toString()));
            tokenList.add(new Token(TokenType.SPECIAL_CHARACTER,"("));
            s.setLength(0);
            ch = processFunction(s.append((char)ch));
        }
        else if(keywords.contains(s.toString())) {
            tokenList.add(new Token(TokenType.KEYWORD_IDENTIFIER,s.toString()));
        } else {
            tokenList.add(new Token(TokenType.USER_DEFINED_IDENTIFIER,s.toString()));
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
            s.setLength(0);
        }
        return ch;
    }

    private int processLiteral(StringBuilder s, boolean isFunction) throws IOException {
        int ch = reader.read();
        while(ch!='\"') {
            s.append((char)ch);
            ch = reader.read();
        }
        s.append((char)ch);
        ch = reader.read();
        tokenList.add(new Token(TokenType.LITERAL,s.toString().replace("(", "")));
        s.setLength(0);
        return ch;
    }

    private int processDigit(StringBuilder s) throws IOException {
        int ch = reader.read();
        while(Character.isDigit(ch)) {
            s.append((char)ch);
            ch = reader.read();
        }
        tokenList.add(new Token(TokenType.DIGIT, s.toString()));
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
                        tokenList.add(new Token(TokenType.OPERATOR,"<="));
                        ch = reader.read();
                    } else {
                        tokenList.add(new Token(TokenType.OPERATOR,"<"));
                    }
                }
                break;
            case '\"':
                ch = processLiteral(s,false);
                break;
            case '=':
                ch = reader.read();
                if(ch=='=') {
                    tokenList.add(new Token(TokenType.OPERATOR,"=="));
                    ch = reader.read();
                } else {
                    tokenList.add(new Token(TokenType.OPERATOR,"="));
                }
                break;
            case '+':
                ch = reader.read();
                if(ch=='+') {
                    tokenList.add(new Token(TokenType.OPERATOR,"++"));
                    ch = reader.read();
                } else if(ch=='=') {
                    tokenList.add(new Token(TokenType.OPERATOR,"+="));
                    ch = reader.read();
                } else {
                    tokenList.add(new Token(TokenType.OPERATOR,"+"));
                }
                break;
            case '*':
                tokenList.add(new Token(TokenType.OPERATOR,"*"));
                ch = reader.read();
                break;
            case '>':
                ch = reader.read();
                if(ch=='=') {
                    tokenList.add(new Token(TokenType.OPERATOR,">="));
                    ch = reader.read();
                } else {
                    tokenList.add(new Token(TokenType.OPERATOR,">"));
                }
                break;
            default:
                tokenList.add(new Token(TokenType.SPECIAL_CHARACTER,String.valueOf((char)ch)));
                ch = reader.read();

        }
        s.setLength(0);
        return ch;
    }

    private boolean isFirstIdentifierChar(int c) {
        return Character.isLetter(c);
    }

    private boolean isSecondIdentifierChar(int c) {
        return Character.isLetter(c) 
            || Character.isDigit(c) 
            || c=='.' 
            || c=='_';
    }

    void doPrintAllTokens() {
        System.out.println("Scanning Starts!");
        for(Token token : tokenList) {
            System.out.println("TokenType: "+token.getType().toString()+"| Value: "+token.getValue());
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
    OPERATOR
}