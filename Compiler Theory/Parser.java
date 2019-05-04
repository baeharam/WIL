import java.util.Iterator;
import java.util.List;

class Parser {
    public static void main(String[] args) throws ParsingException{
        if(args.length==0) {
            System.out.println("Usage: java RDParser \"file path\"");
        } else {
            Tokenizer tokenizer = new Tokenizer(args[0]);
            tokenizer.doTokenize();
            tokenizer.doPrintAllTokens();
            
            RDParser rdParser = new RDParser(tokenizer.getTokenList());
            rdParser.parse();
        }
    }
}

class RDParser {
    private List<Token> tokenList;
    private Iterator<Token> it;
    private Token currentToken;

    RDParser(List<Token> tokenList) {
        this.tokenList = tokenList;
        this.it = tokenList.iterator();
    }

    void parse() throws ParsingException{
        currentToken = it.next();
        if(currentToken.getValue().equals("<script_start>")){
            match("<script_start>");
            parseStmtList();
            match("<script_end>");
            System.out.println("Parsing OK");
        } else {
            throw new ParsingException("<script_start>");
        }
    }

    private void parseStmtList() throws ParsingException{
        while(!currentToken.getValue().equals("<script_end>")) {
            if(currentToken.getValue().equals("if")){
                match("if");
                parseIfElseStmt();
            } else if(currentToken.getValue().equals("while")){
                match("while");
                parseWhileStmt();
            } else if(currentToken.getValue().equals("for")){
                match("for");
                parseForStmt();
            } else if(currentToken.getValue().equals("switch")){
                match("switch");
                parseSwitchStmt();
            } else if(currentToken.getType()==TokenType.KEYWORD_IDENTIFIER ||
                      currentToken.getType()==TokenType.USER_DEFINED_IDENTIFIER ||
                      currentToken.getType()==TokenType.FUNCTION_IDENTIFIER){
                matchIdentifier();
                if(currentToken.getValue().equals("++") || currentToken.getValue().equals("--")) {
                    switch(currentToken.getValue()){
                        case "++": match("++"); break;
                        case "--": match("--");
                    }
                } else if(currentToken.getValue().equals("(")) {
                    parseFunctionStmt();
                } else {
                    if(currentToken.getValue().equals("var")) {
                        match("var");
                    }
                    parseAssignmentStmt();
                }
            } else {
                break;
            }
            if(currentToken.getValue().equals(",")) {
                match(",");
            } else if(currentToken.getValue().equals(";")) {
                match(";");
            }
        }
    }

    private void parseFunctionStmt() throws ParsingException {
        match("(");
        matchLiteral();
        match(")");
    }

    private void parseAssignmentStmt() throws ParsingException {
        if(currentToken.getType() == TokenType.USER_DEFINED_IDENTIFIER){
            matchIdentifier();
            if(currentToken.getValue().equals("=")) {
                match("=");
                parseExp();
            }
        } else {
            match("=");
            parseExp();
        }
    }

    private void parseIfElseStmt() throws ParsingException {
        if(currentToken.getValue().equals("(")) {
            match("(");
            parseExp();
            match(")");
            match("{");
            parseStmtList();
            match("}");
            match("else");
            match("{");
            parseStmtList();
            match("}");
        } else {
            throw new ParsingException("(");
        }
    }

    private void parseWhileStmt() throws ParsingException {
        if(currentToken.getValue().equals("(")){
            match("(");
            parseExp();
            match(")");
            match("{");
            parseStmtList();
            match("}");
        } else {
            throw new ParsingException("(");
        }
    }

    private void parseForStmt() throws ParsingException {
        if(currentToken.getValue().equals("(")){
            match("(");
            matchIdentifier();
            parseAssignmentStmt();
            match(";");
            parseExp();
            match(";");
            parseExp();
            match(")");
            match("{");
            parseStmtList();
            match("}");
        } else {
            throw new ParsingException("(");
        }
    }

    private void parseSwitchStmt() throws ParsingException {

    }

    private void parseExp() throws ParsingException {
        parseAddExp();
        while(currentToken.getValue().equals("<") 
            || currentToken.getValue().equals(">")
            || currentToken.getValue().equals("<=")
            || currentToken.getValue().equals(">=")
            || currentToken.getValue().equals("==")){

            switch(currentToken.getValue()){
                case "<": match("<"); break;
                case ">": match(">"); break;
                case "<=": match("<="); break;
                case ">=": match(">="); break;
                case "==": match("==");
            }
            parseAddExp();
        }
    }

    private void parseAddExp() throws ParsingException {
        parseMulExp();
        while(currentToken.getValue().equals("+") || currentToken.getValue().equals("-")){
            switch(currentToken.getValue()) {
                case "+": match("+"); break;
                case "-": match("/");
            }
            parseMulExp();
        }
    }

    private void parseMulExp() throws ParsingException {
        parseInDeExp();
        while(currentToken.getValue().equals("*") || currentToken.getValue().equals("/")){
            switch(currentToken.getValue()) {
                case "*": match("*"); break;
                case "/": match("/");
            }
            parseInDeExp();
        }
    }

    private void parseInDeExp() throws ParsingException {
        parseFactor();
        while(currentToken.getValue().equals("++") || currentToken.getValue().equals("--")){
            switch(currentToken.getValue()) {
                case "++": match("++"); break;
                case "--": match("--");
            }
        }
    }

    private void parseFactor() throws ParsingException {
        if(currentToken.getType()==TokenType.KEYWORD_IDENTIFIER ||
            currentToken.getType()==TokenType.FUNCTION_IDENTIFIER ||
            currentToken.getType()==TokenType.USER_DEFINED_IDENTIFIER) {
            matchIdentifier();
        }
        else if(currentToken.getType()==TokenType.DIGIT) {
            matchDigit();
        } else {
            throw new ParsingException(currentToken.getValue());
        }
    }

    private void matchLiteral() throws ParsingException {
        if(currentToken.getType()==TokenType.LITERAL){
            System.out.println("Match with "+currentToken.getValue());
            currentToken = it.next();
        } else {
            throw new ParsingException(currentToken.getValue());
        }
    }

    private void matchDigit() throws ParsingException {
        if(currentToken.getType()==TokenType.DIGIT){
            System.out.println("Match with "+currentToken.getValue());
            currentToken = it.next();
        } else {
            throw new ParsingException(currentToken.getValue());
        }
    }

    private void matchIdentifier() throws ParsingException {
        if(currentToken.getType()==TokenType.KEYWORD_IDENTIFIER
        || currentToken.getType()==TokenType.USER_DEFINED_IDENTIFIER
        || currentToken.getType()==TokenType.FUNCTION_IDENTIFIER) {
            System.out.println("Match with "+currentToken.getValue());
            currentToken = it.next();
        } else {
            throw new ParsingException(currentToken.getValue());
        }
    }

    private void match(String expectedTokenValue) throws ParsingException {
        if(currentToken.getValue().equals(expectedTokenValue)) {
            System.out.println("Match with "+currentToken.getValue());
            if(!currentToken.getValue().equals("<script_end>")){
                currentToken = it.next();
            }
        } else {
            throw new ParsingException(expectedTokenValue);
        }
    }
}

class ParsingException extends Exception {
    private static final long serialVersionUID = 1L;

    ParsingException(String errorTokenValue) {
        super("Parsing error!: " + errorTokenValue + " is invalid");
    }
}