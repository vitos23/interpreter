package com.vitos23.interpreter.lang

import com.vitos23.interpreter.lang.operations.Add
import com.vitos23.interpreter.lang.operations.Subtract
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CallExpressionTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()
        val func = FunctionStatement(
            "foo", listOf("a", "b"),
            Subtract(Variable("a"), Variable("b"))
        )
        env.loadFunction(func)

        assertEquals(-10, CallExpression(
            "foo",
            listOf(Const(10), Add(Const(5), Const(15)))
        ).evaluate(env))
    }
}