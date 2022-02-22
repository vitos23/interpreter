package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ConstTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(3, Const(3).evaluate(env))
        assertEquals(Int.MAX_VALUE, Const(Int.MAX_VALUE).evaluate(env))
        assertEquals(Int.MIN_VALUE, Const(Int.MIN_VALUE).evaluate(env))
    }
}