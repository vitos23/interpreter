package com.vitos23.interpreter.lexer

enum class TokenType(val lexeme: String = "") {
    NUMBER,
    ID, // identifier
    OPEN_PARENTHESIS("("),
    CLOSING_PARENTHESIS(")"),
    OPEN_SQUARE_BRACKET("["),
    CLOSING_SQUARE_BRACKET("]"),
    OPEN_CURLY_BRACKET("{"),
    CLOSING_CURLY_BRACKET("}"),
    COLON(":"),
    QUESTION_MARK("?"),
    COMMA(","),
    OP_ADD("+"),
    MINUS("-"),
    OP_MUL("*"),
    OP_DIV("/"),
    OP_MOD("%"),
    OP_GT(">"),
    OP_LESS("<"),
    OP_EQ("=="),
    EQUALITY("="),
    EOL, // end of line
    EOF, // enf of file
}