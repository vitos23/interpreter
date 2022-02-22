package com.vitos23.interpreter.lang

import com.vitos23.interpreter.lang.operations.Greater
import com.vitos23.interpreter.lang.operations.Less
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class IfExpressionTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(1, IfExpression(
            Greater(Const(2), Const(1)), Const(1), Const(2)
        ).evaluate(env))

        assertEquals(2, IfExpression(
            Less(Const(2), Const(1)), Const(1), Const(2)
        ).evaluate(env))

        assertEquals(1, IfExpression(
            Const(-1234), Const(1), Const(2)
        ).evaluate(env))

        assertEquals(2, IfExpression(
            Const(0), Const(1), Const(2)
        ).evaluate(env))
    }
}