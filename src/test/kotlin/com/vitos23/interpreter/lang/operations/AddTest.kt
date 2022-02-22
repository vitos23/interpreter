package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.lang.Const
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AddTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(10, Add(Const(-5), Const(15)).evaluate(env))
        assertEquals(
            Int.MIN_VALUE + Int.MAX_VALUE,
            Add(Const(Int.MIN_VALUE), Const(Int.MAX_VALUE)).evaluate(env)
        )
    }
}