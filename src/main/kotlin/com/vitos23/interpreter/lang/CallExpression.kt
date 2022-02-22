package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

class CallExpression(val identifier: String, private val arguments: List<Expression>) : Expression() {

    override fun evaluate(env: Environment): Int {
        val args = List(arguments.size) { arguments[it].evaluate(env) }
        return env.runFunction(identifier, args)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as CallExpression

        return identifier == other.identifier && arguments == other.arguments
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + arguments.hashCode()
        return result
    }
}