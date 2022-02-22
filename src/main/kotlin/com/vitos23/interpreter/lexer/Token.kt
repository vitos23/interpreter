package com.vitos23.interpreter.lexer

data class Token(val type: TokenType, val lexeme: String = type.lexeme)
