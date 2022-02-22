package com.vitos23.interpreter.lexer

interface Lexer {

    /**
     * @return true if the next token exists
     */
    fun hasNext(): Boolean

    /**
     * @return next token
     * @throws NoSuchElementException if the next token doesn't exist
     */
    fun nextToken(): Token

    /**
     * @return list of remaining tokens
     */
    fun getTokens(): List<Token> {
        val tokens = ArrayList<Token>()
        while (hasNext()) {
            tokens.add(nextToken())
        }
        return tokens
    }
}