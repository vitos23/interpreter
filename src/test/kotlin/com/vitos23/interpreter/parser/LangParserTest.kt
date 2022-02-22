package com.vitos23.interpreter.parser

import com.vitos23.interpreter.exception.ParseException
import com.vitos23.interpreter.lang.*
import com.vitos23.interpreter.lang.operations.*
import com.vitos23.interpreter.lexer.LangLexer
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail

internal class LangParserTest {

    @Test
    fun test1() {
        val source = """
            zero() = { 0 }
            convert(a) = { [((a % 2) == 1)]?{a}:{0} }
            
            sumOdd(a, b) = { (convert(a) + convert(b)) }
            
            
            (sumOdd((2 * 3), (22 / 2)) - zero())
        """.trimIndent()

        val program = Program(
            listOf(
                FunctionStatement("zero", listOf(), Const(0)),
                FunctionStatement("convert", listOf("a"), IfExpression(
                    Equals(Mod(Variable("a"), Const(2)), Const(1)),
                    Variable("a"),
                    Const(0)
                )),
                FunctionStatement("sumOdd", listOf("a", "b"), Add(
                    CallExpression("convert", listOf(Variable("a"))),
                    CallExpression("convert", listOf(Variable("b")))
                ))
            ),
            Subtract(
                CallExpression("sumOdd", listOf(
                    Multiply(Const(2), Const(3)),
                    Divide(Const(22), Const(2))
                )),
                CallExpression("zero", listOf())
            )
        )

        val parsed = LangParser(LangLexer(source)).parse()

        assertEquals(program, parsed)
    }

    @Test
    fun test2() {
        val source = "0"
        val program = Program(listOf(), Const(0))
        assertEquals(program, LangParser(LangLexer(source)).parse())
    }

    @Test
    fun test3() {
        val source = Int.MIN_VALUE.toString()
        val program = Program(listOf(), Const(Int.MIN_VALUE))
        assertEquals(program, LangParser(LangLexer(source)).parse())
    }

    @Test
    fun test4() {
        val source = "((1 > 0) < 2)"
        val program = Program(listOf(), Less(
            Greater(Const(1), Const(0)),
            Const(2)
        ))
        assertEquals(program, LangParser(LangLexer(source)).parse())
    }

    @Test
    fun test5() {
        val source = """
            foo() = { bar() }
            bar() = { foo() }
            
            foo()
        """.trimIndent()

        val program = Program(
            listOf(
                FunctionStatement("foo", listOf(), CallExpression("bar", listOf())),
                FunctionStatement("bar", listOf(), CallExpression("foo", listOf()))
            ),
            CallExpression("foo", listOf())
        )

        val parsed = LangParser(LangLexer(source)).parse()

        assertEquals(program, parsed)
    }

    @Test
    fun testInvalidPrograms() {
        expectException("1+2")
        expectException("(1+")
        expectException("(1+)")
        expectException("()")
        expectException("(0)")
        expectException("12345678901")
        expectException("-12345678901")
        expectException("")
        expectException("1+2+3")
        expectException("func() = { 1 }") // no expression after declarations
        expectException("[1]?{3}?{2}")
        expectException("f1() = { 1 }\nf1()") // function name can't contain digits
    }

    private fun expectException(source: String) {
        try {
            LangParser(LangLexer(source)).parse()
            fail("Expected ParseException")
        } catch (_: ParseException) {
        }
    }
}