package com.vitos23.interpreter.lang

import com.vitos23.interpreter.lang.operations.*
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ProgramTest {

    @Test
    fun test1() {
        val env = StandardEnvironment()

        /*
        convert(a) = { [((a % 2) == 1)]?{a}:{0} }
        sumOdd(a, b) = (convert(a) + convert(b))
        sumOdd((2 * 3), (22 / 2))
         */
        val program = Program(
            listOf(
                FunctionStatement(
                    "convert",
                    listOf("a"),
                    IfExpression(
                        Equals(Mod(Variable("a"), Const(2)), Const(1)),
                        Variable("a"),
                        Const(0)
                    )
                ),
                FunctionStatement(
                    "sumOdd",
                    listOf("a", "b"),
                    Add(
                        CallExpression("convert", listOf(Variable("a"))),
                        CallExpression("convert", listOf(Variable("b")))
                    )
                )
            ),
            CallExpression("sumOdd", listOf(
                Multiply(Const(2), Const(3)),
                Divide(Const(22), Const(2))
            ))
        )
        assertEquals(11, program.run(env))
    }
}