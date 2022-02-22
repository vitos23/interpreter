package com.vitos23.interpreter.parser

import com.vitos23.interpreter.exception.ParseException
import com.vitos23.interpreter.lang.*
import com.vitos23.interpreter.lang.operations.*
import com.vitos23.interpreter.lexer.Lexer
import com.vitos23.interpreter.lexer.Token
import com.vitos23.interpreter.lexer.TokenType

class LangParser(private val tokens: List<Token>) : Parser {

    private var pos = 0

    constructor(lexer: Lexer) : this(lexer.getTokens())

    override fun parse(): Program {
        val functions = ArrayList<FunctionStatement>()
        while (true) {
            skipEol()
            val func = tryParseFunction()
            functions.add(func ?: break)
        }
        skipEol()
        val expression = parseExpression()
        skipEol()
        getTypedToken(TokenType.EOF)
        return Program(functions, expression)
    }

    /**
     * @return FunctionStatement or null if the following tokens don't form a function
     */
    private fun tryParseFunction(): FunctionStatement? {
        val cachedPos = pos
        val identifier: String
        val parameters: List<String>
        try {
            identifier = getTypedToken(TokenType.ID).lexeme
            getTypedToken(TokenType.OPEN_PARENTHESIS)
            parameters = parseParameterList()
            getTypedToken(TokenType.CLOSING_PARENTHESIS)
            getTypedToken(TokenType.EQUALITY)
        } catch (e: ParseException) {
            pos = cachedPos
            return null
        }

        getTypedToken(TokenType.OPEN_CURLY_BRACKET)
        val expression = parseExpression()
        getTypedToken(TokenType.CLOSING_CURLY_BRACKET)
        getTypedToken(TokenType.EOL)

        return FunctionStatement(identifier, parameters, expression)
    }

    private fun parseExpression(): Expression {
        val token = getToken()
        when (token.type) {
            TokenType.ID -> {
                return if (takeToken(TokenType.OPEN_PARENTHESIS)) {
                    // arg-list
                    val call = CallExpression(token.lexeme, parseArgList())
                    getTypedToken(TokenType.CLOSING_PARENTHESIS)
                    call
                } else {
                    Variable(token.lexeme)
                }
            }
            TokenType.NUMBER -> return Const(strToInt(token.lexeme))
            TokenType.MINUS -> return Const(
                strToInt("-" + getTypedToken(TokenType.NUMBER).lexeme)
            )
            TokenType.OPEN_PARENTHESIS -> {
                val expression = parseBinaryExpression()
                getTypedToken(TokenType.CLOSING_PARENTHESIS)
                return expression
            }
            TokenType.OPEN_SQUARE_BRACKET -> return parseIfExpression()
            else -> throw ParseException("Unexpected token: $token")
        }
    }

    // without first {
    private fun parseIfExpression(): IfExpression {
        val condition = parseExpression()
        getTypedToken(TokenType.CLOSING_SQUARE_BRACKET)
        getTypedToken(TokenType.QUESTION_MARK)
        getTypedToken(TokenType.OPEN_CURLY_BRACKET)
        val trueExpression = parseExpression()
        getTypedToken(TokenType.CLOSING_CURLY_BRACKET)
        getTypedToken(TokenType.COLON)
        getTypedToken(TokenType.OPEN_CURLY_BRACKET)
        val falseExpression = parseExpression()
        getTypedToken(TokenType.CLOSING_CURLY_BRACKET)
        return IfExpression(condition, trueExpression, falseExpression)
    }

    private fun parseArgList(): List<Expression> {
        if (testToken(TokenType.CLOSING_PARENTHESIS)) {
            return listOf()
        }
        val args = ArrayList<Expression>()
        do {
            args.add(parseExpression())
        } while (takeToken(TokenType.COMMA))
        return args
    }

    private fun parseBinaryExpression(): BinaryExpression {
        val left = parseExpression()
        val op = getToken()
        if (op.type !in BINARY_OPS) {
            throw ParseException("Unknown binary operation: $op")
        }
        val right = parseExpression()

        return when (op.type) {
            TokenType.OP_ADD -> Add(left, right)
            TokenType.MINUS -> Subtract(left, right)
            TokenType.OP_MUL -> Multiply(left, right)
            TokenType.OP_DIV -> Divide(left, right)
            TokenType.OP_MOD -> Mod(left, right)
            TokenType.OP_GT -> Greater(left, right)
            TokenType.OP_LESS -> Less(left, right)
            TokenType.OP_EQ -> Equals(left, right)
            else -> throw AssertionError(
                "Unknown operation listed in BINARY_OPS: ${op.type}"
            )
        }
    }

    private fun strToInt(s: String): Int {
        try {
            return s.toInt()
        } catch (e: NumberFormatException) {
            throw ParseException("Invalid number: $s (${e.message})")
        }
    }

    private fun parseParameterList(): List<String> {
        if (testToken(TokenType.CLOSING_PARENTHESIS)) {
            return listOf()
        }
        val params = ArrayList<String>()
        do {
            params.add(getTypedToken(TokenType.ID).lexeme)
        } while (takeToken(TokenType.COMMA))
        return params
    }

    /**
     * @return next token
     * @throws ParseException if there are no more tokens
     */
    private fun getToken(): Token {
        if (pos == tokens.size) {
            throw ParseException("There are no more tokens")
        }
        return tokens[pos++]
    }

    /**
     * @return next token
     * @throws ParseException if the next token has a different type
     */
    private fun getTypedToken(type: TokenType): Token {
        val token = getToken()
        if (token.type != type) {
            throw ParseException("Expected token of type $type but found ${token.type}")
        }
        return token
    }

    /**
     * If the next token has the given type this function returns true
     * and the next token is changed to the next one.
     * @return true if the next token has the given type
     */
    private fun takeToken(type: TokenType): Boolean {
        if (pos < tokens.size && tokens[pos].type == type) {
            pos++
            return true
        }
        return false
    }

    /**
     * @return true if the next token has the given type
     */
    private fun testToken(type: TokenType): Boolean = pos < tokens.size && tokens[pos].type == type

    private fun skipEol() {
        while(takeToken(TokenType.EOL)) {
        }
    }

    companion object {
        private val BINARY_OPS = setOf(
            TokenType.OP_ADD,
            TokenType.MINUS,
            TokenType.OP_MUL,
            TokenType.OP_DIV,
            TokenType.OP_MOD,
            TokenType.OP_GT,
            TokenType.OP_LESS,
            TokenType.OP_EQ
        )
    }
}