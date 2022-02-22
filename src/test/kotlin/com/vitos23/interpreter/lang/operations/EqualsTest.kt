package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.lang.Const
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EqualsTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(0, Equals(Const(1234), Const(43)).evaluate(env))
        assertEquals(1, Equals(Const(Int.MIN_VALUE), Const(Int.MIN_VALUE)).evaluate(env))
    }
}