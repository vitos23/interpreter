package com.vitos23.interpreter.lexer

import com.vitos23.interpreter.exception.LexerException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.time.AbstractDoubleTimeSource

internal class LangLexerTest {

    @Test
    fun testNewLine() {
        val source = "  \n  \r\n \r\r    "
        val expected = MutableList(4) { Token(TokenType.EOL) }
        expected.add(Token(TokenType.EOF))
        assertEquals(expected, LangLexer(source).getTokens())
    }

    @Test
    fun test1() {
        val source = "- -2134"
        val expected = listOf(
            Token(TokenType.MINUS),
            Token(TokenType.MINUS),
            Token(TokenType.NUMBER, "2134"),
            Token(TokenType.EOF)
        )
        assertEquals(expected, LangLexer(source).getTokens())
    }

    @Test
    fun test2() {
        val source = """
            convert(a) = { [((a % 2) == 1)]?{a}:{0} }
            sumOdd(a, b) = { (convert(a) + convert(b)) }
            sumOdd((2 * 3), (22 / 2))
        """.trimIndent()

        val expected = listOf(
            Token(TokenType.ID, "convert"),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.ID, "a"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.EQUALITY),
            Token(TokenType.OPEN_CURLY_BRACKET),
            Token(TokenType.OPEN_SQUARE_BRACKET),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.ID, "a"),
            Token(TokenType.OP_MOD),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.OP_EQ),
            Token(TokenType.NUMBER, "1"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.CLOSING_SQUARE_BRACKET),
            Token(TokenType.QUESTION_MARK),
            Token(TokenType.OPEN_CURLY_BRACKET),
            Token(TokenType.ID, "a"),
            Token(TokenType.CLOSING_CURLY_BRACKET),
            Token(TokenType.COLON),
            Token(TokenType.OPEN_CURLY_BRACKET),
            Token(TokenType.NUMBER, "0"),
            Token(TokenType.CLOSING_CURLY_BRACKET),
            Token(TokenType.CLOSING_CURLY_BRACKET),
            Token(TokenType.EOL),

            Token(TokenType.ID, "sumOdd"),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.ID, "a"),
            Token(TokenType.COMMA),
            Token(TokenType.ID, "b"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.EQUALITY),
            Token(TokenType.OPEN_CURLY_BRACKET),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.ID, "convert"),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.ID, "a"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.OP_ADD),
            Token(TokenType.ID, "convert"),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.ID, "b"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.CLOSING_CURLY_BRACKET),
            Token(TokenType.EOL),

            Token(TokenType.ID, "sumOdd"),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.OP_MUL),
            Token(TokenType.NUMBER, "3"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.COMMA),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.NUMBER, "22"),
            Token(TokenType.OP_DIV),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.CLOSING_PARENTHESIS),
            Token(TokenType.EOF)
        )

        assertEquals(expected, LangLexer(source).getTokens())
    }

    @Test
    fun testLexerException() {
        expectException("1234fa")
        expectException("123 & 4321")
        expectException("2^5")
    }

    @Test
    fun testNoSuchElementException() {
        val source = "1"
        val lexer = LangLexer(source)
        lexer.getTokens()
        try {
            lexer.nextToken()
        } catch (_: NoSuchElementException) {
        }
    }

    @Test
    private fun expectException(source: String) {
        try {
            LangLexer(source).getTokens()
            fail("Expected LexerException")
        } catch (_: LexerException) {
        }
    }
}