package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.lang.Const
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GreaterTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(1, Greater(Const(1234), Const(43)).evaluate(env))
        assertEquals(0, Greater(Const(1), Const(1)).evaluate(env))
        assertEquals(0, Greater(Const(Int.MIN_VALUE), Const(Int.MAX_VALUE)).evaluate(env))
    }
}