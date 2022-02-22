package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.lang.Const
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LessTest {

    @Test
    fun testEvaluate() {
        val env = StandardEnvironment()

        assertEquals(0, Less(Const(1234), Const(43)).evaluate(env))
        assertEquals(0, Less(Const(1), Const(1)).evaluate(env))
        assertEquals(1, Less(Const(Int.MIN_VALUE), Const(Int.MAX_VALUE)).evaluate(env))
    }
}