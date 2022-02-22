package com.vitos23.interpreter.vm

import com.vitos23.interpreter.exception.EnvironmentException
import com.vitos23.interpreter.lang.CallExpression
import com.vitos23.interpreter.lang.Const
import com.vitos23.interpreter.lang.FunctionStatement
import com.vitos23.interpreter.lang.Variable
import com.vitos23.interpreter.lang.operations.Subtract
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail

internal class StandardEnvironmentTest {

    @Test
    fun testGetVariableError() {
        val env = StandardEnvironment()

        expectError { env.getVariable("a") }
    }

    @Test
    fun testFunction() {
        val env = StandardEnvironment()
        val func = FunctionStatement(
            "foo", listOf("a", "b"),
            Subtract(Variable("a"), Variable("b"))
        )
        env.loadFunction(func)

        assertEquals(-1, env.runFunction("foo", listOf(2, 3)))

        expectError { env.runFunction("foo", listOf(2)) }
        expectError { env.runFunction("foo", listOf(2, 3, 4)) }
        expectError { env.runFunction("bar", listOf(2)) }
    }

    @Test
    fun testCallStackLimit() {
        val env = StandardEnvironment(1)
        val func1 = FunctionStatement(
            "foo", listOf("a", "b"),
            Subtract(Variable("a"), Variable("b"))
        )
        val func2 = FunctionStatement(
            "runFoo",
            listOf(),
            CallExpression("foo", listOf(Const(1), Const(1)))
        )

        env.loadFunction(func1)
        env.loadFunction(func2)

        expectError { env.runFunction("runFoo", listOf()) }
    }

    private fun expectError(f: () -> Unit) {
        try {
            f()
            fail("Expected the EnvironmentException")
        } catch (_: EnvironmentException) {
        }
    }
}