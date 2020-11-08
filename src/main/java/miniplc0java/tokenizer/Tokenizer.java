package miniplc0java.tokenizer;

import miniplc0java.error.TokenizeError;
import miniplc0java.error.ErrorCode;
import miniplc0java.util.Pos;

public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     * 
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        if (Character.isDigit(peek)) {
            return lexUInt();
        } else if (Character.isAlphabetic(peek)) {
            return lexIdentOrKeyword();
        } else {
            return lexOperatorOrUnknown();
        }
    }

    private Token lexUInt() throws TokenizeError {
        // 请填空：
        StringBuffer number = new StringBuffer();
        Pos p = it.currentPos();           //save start pos
        char current;                      //save current char
        char peek = it.peekChar();         //point the char

        if(Character.isDigit(peek)){
            while(Character.isDigit(peek)){
                current = it.nextChar();
                number.append(current);
            }
            // 直到查看下一个字符不是数字为止:
            // -- 前进一个字符，并存储这个字符
            //
            int tokennum = Integer.parseInt(number.toString());
            Token t = new Token(TokenType.Uint,tokennum,p, it.currentPos());
            return t;
            // 解析存储的字符串为无符号整数
            // 解析成功则返回无符号整数类型的token，否则返回编译错误
            //
            // Token 的 Value 应填写数字的值
        }
        else                                //it is the right way to throw error?
        throw new Error("Not implemented");
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        // 请填空：
        StringBuffer str = new StringBuffer();
        Pos p = it.currentPos();           //save start pos
        //TokenType tt;
        char current;                      //save current char
        char peek = it.peekChar();         //point the char

        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        //
        if (Character.isAlphabetic(peek)){
            while(Character.isAlphabetic(peek) || Character.isDigit(peek)){
                current = it.nextChar();
                str.append(current);
            }

            // 尝试将存储的字符串解释为关键字
            // -- 如果是关键字，则返回关键字类型的 token
            // -- 否则，返回标识符

            if(str.toString().equalsIgnoreCase("begin")){ //str.equals("begin")
                Token t = new Token(TokenType.Begin,str,p, it.currentPos());
                return t;
            }
            else if (str.toString().equalsIgnoreCase("end")){
                Token t = new Token(TokenType.End,str,p, it.currentPos());
                return t;
            }
            else if (str.toString().equalsIgnoreCase("const")){
                Token t = new Token(TokenType.Const,str,p, it.currentPos());
                return t;
            }
            else if (str.toString().equalsIgnoreCase("var")){
                Token t = new Token(TokenType.Var,str,p, it.currentPos());
                return t;
            }
            else if (str.toString().equalsIgnoreCase("print")){
                Token t = new Token(TokenType.Print,str,p, it.currentPos());
                return t;
            }
            else{
                Token t = new Token(TokenType.Ident,str,p, it.currentPos());
                return t;
            }
        }

        // Token 的 Value 应填写标识符或关键字的字符串
        else                                //it is the right way to throw error?
        throw new Error("Not implemented");
    }

    private Token lexOperatorOrUnknown() throws TokenizeError {
        char a = it.nextChar();
        switch (a) {
            case '+':
                if(a == '+')
                return new Token(TokenType.Plus, '+', it.previousPos(), it.currentPos());
                //need break?
                else throw new Error("Not implemented");

            case '-':
                // 填入返回语句
                if(a == '-')
                return new Token(TokenType.Minus, '-', it.previousPos(), it.currentPos());

                else throw new Error("Not implemented");

            case '*':
                // 填入返回语句
                if(a == '*')
                    return new Token(TokenType.Mult, '*', it.previousPos(), it.currentPos());

                else throw new Error("Not implemented");

            case '/':
                // 填入返回语句
                if(a == '/')
                    return new Token(TokenType.Div, '/', it.previousPos(), it.currentPos());


                else throw new Error("Not implemented");

            // 填入更多状态和返回语句
            case '=':
                // 填入返回语句
                if(a == '=')
                    return new Token(TokenType.Equal, '=', it.previousPos(), it.currentPos());


                else throw new Error("Not implemented");

            case '(':
                // 填入返回语句
                if(a == '(')
                    return new Token(TokenType.LParen, '(', it.previousPos(), it.currentPos());


                else throw new Error("Not implemented");
            case ')':
                // 填入返回语句
                if(a == ')')
                    return new Token(TokenType.RParen, ')', it.previousPos(), it.currentPos());


                else throw new Error("Not implemented");
            case ';':
                // 填入返回语句
                if(a == ';')
                    return new Token(TokenType.Semicolon, ';', it.previousPos(), it.currentPos());


                else throw new Error("Not implemented");
            case '\0':
                // 填入返回语句
                if(it.isEOF())
                    return new Token(TokenType.EOF, "EOF", it.previousPos(), it.currentPos());


                else throw new Error("Not implemented");

                //////need break?

            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }
}
