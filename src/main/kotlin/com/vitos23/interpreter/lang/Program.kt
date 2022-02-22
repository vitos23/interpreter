package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

class Program(private val functions: List<FunctionStatement>, private val expression: Expression) {

    fun run(env: Environment): Int {
        functions.forEach { env.loadFunction(it) }
        return expression.evaluate(env)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Program

        return functions == other.functions && expression == other.expression
    }

    override fun hashCode(): Int {
        var result = functions.hashCode()
        result = 31 * result + expression.hashCode()
        return result
    }
}