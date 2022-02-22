package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail

internal class VariableTest {

    @Test
    fun testEvaluate() {
        val env = TestEnvironment()
        env.variables["a"] = 10
        env.variables["b"] = 20

        assertEquals(10, Variable("a").evaluate(env))
        assertEquals(20, Variable("b").evaluate(env))

        try {
            Variable("c").evaluate(env)
            fail("Expected an exception")
        } catch (_: Exception) {
        }
    }

    private class TestEnvironment : Environment {

        val variables = HashMap<String, Int>()

        override fun getVariable(identifier: String): Int = variables[identifier]!!

        override fun loadFunction(function: FunctionStatement) {
        }

        override fun runFunction(identifier: String, arguments: List<Int>): Int = 0
    }
}