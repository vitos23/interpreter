package com.vitos23.interpreter.lexer

import com.vitos23.interpreter.exception.LexerException

class LangLexer(private val source: String) : Lexer {

    private var pos = 0
    private var cachedToken: Token? = null

    override fun hasNext(): Boolean {
        updateToken()
        return cachedToken != null
    }

    override fun nextToken(): Token {
        updateToken()
        if (cachedToken == null) {
            throw NoSuchElementException("There are no more tokens")
        }
        val result = cachedToken!!
        cachedToken = null
        return result
    }

    private fun updateToken() {
        if (cachedToken == null) {
            cachedToken = readNextToken()
        }
    }

    private fun readNextToken(): Token? {
        if (pos > source.length) {
            return null
        }
        skipWhitespaces()
        if (pos == source.length) {
            pos++
            return Token(TokenType.EOF)
        }
        if (takeEndOfLine()) {
            return Token(TokenType.EOL)
        }

        for (tokenType in TokenType.values()) {
            if (tokenType.lexeme.isNotEmpty() && take(tokenType.lexeme)) {
                return Token(tokenType)
            }
        }

        // id
        if (isIdChar(source[pos])) {
            return Token(TokenType.ID, parseId())
        }

        // number
        if (source[pos].isDigit()) {
            return Token(TokenType.NUMBER, parseNumber())
        }

        throwException()
    }

    private fun throwException(): Nothing {
        throw LexerException("Unexpected character '${source[pos]}' (position: $pos)")
    }

    private fun isIdChar(c: Char) = c in 'A'..'z' || c == '_'

    private fun parseId(): String {
        val start = pos
        while (pos < source.length && isIdChar(source[pos])) {
            pos++
        }
        return source.substring(start, pos)
    }

    private fun parseNumber(): String {
        val start = pos
        while (pos < source.length && source[pos].isDigit()) {
            pos++
        }
        expectSignificantChars(NUMBER_END_CHARS)
        return source.substring(start, pos)
    }

    /**
     * If the result is true, the part of the [source] equal to [s] will be skipped
     * @return true if the next characters of [source] compose [s]
     */
    private fun take(s: String): Boolean {
        if (pos + s.length <= source.length && source.substring(pos, pos + s.length) == s) {
            pos += s.length
            return true
        }
        return false
    }

    /**
     * If the result is true, end of line symbol will be skipped
     * @return true if the current symbol is end of line (\n, \r, \r\n)
     */
    private fun takeEndOfLine(): Boolean {
        val cachedPos = pos
        if (pos < source.length && source[pos] == '\r') {
            pos++
            if (pos < source.length && source[pos] == '\n') {
                pos++
            }
            return true
        }
        if (pos < source.length && source[pos] == '\n') {
            pos++
            return true
        }
        pos = cachedPos
        return false
    }

    private fun skipWhitespaces() {
        while (pos < source.length && source[pos].isWhitespace() && !(source[pos] == '\r' || source[pos] == '\n')) {
            pos++
        }
    }

    companion object {
        private const val NUMBER_END_CHARS = "{}()[]:?,+-*/%><="
    }

    /**
     * @throws LexerException if current character is not a whitespace
     * or eof and [chars] doesn't contain it
     */
    private fun expectSignificantChars(chars: String) {
        if (pos < source.length && !source[pos].isWhitespace() && source[pos] !in chars) {
            throwException()
        }
    }
}