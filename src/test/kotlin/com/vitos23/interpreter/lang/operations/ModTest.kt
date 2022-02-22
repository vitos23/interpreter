package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.exception.ArithmeticException
import com.vitos23.interpreter.lang.Const
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ModTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(1234 % 43, Mod(Const(1234), Const(43)).evaluate(env))
        assertEquals(1234 % -43, Mod(Const(1234), Const(-43)).evaluate(env))
        assertEquals(
            Int.MIN_VALUE % Int.MAX_VALUE,
            Mod(Const(Int.MIN_VALUE), Const(Int.MAX_VALUE)).evaluate(env)
        )

        try {
            Mod(Const(1), Const(0)).evaluate(env)
            org.junit.jupiter.api.fail("Expected the division by zero exception")
        } catch (_: ArithmeticException) {}
    }
}