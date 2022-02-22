package com.vitos23.interpreter.lang

import com.vitos23.interpreter.lang.operations.Add
import com.vitos23.interpreter.vm.StandardEnvironment
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FunctionStatementTest {

    @Test
    fun testRun() {
        val env = StandardEnvironment()

        assertEquals(10, FunctionStatement(
            "foo", listOf(), Add(Const(2), Const(8))
        ).run(env))
    }
}