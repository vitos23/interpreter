package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

class FunctionStatement(
    val identifier: String,
    val parameters: List<String>,
    val expression: Expression
) : Statement() {

    fun run(env: Environment): Int = expression.evaluate(env)

     override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other?.javaClass != this.javaClass) {
            return false
        }
        other as FunctionStatement
        return identifier == other.identifier
                && parameters == other.parameters
                && expression == other.expression
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + expression.hashCode()
        return result
    }
}