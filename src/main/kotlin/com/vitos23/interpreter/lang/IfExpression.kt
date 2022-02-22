package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

class IfExpression(
    private val condition: Expression,
    private val trueExpression: Expression,
    private val falseExpression: Expression
) : Expression() {

    override fun evaluate(env: Environment): Int {
        return if (condition.evaluate(env) != 0) {
            trueExpression.evaluate(env)
        } else {
            falseExpression.evaluate(env)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as IfExpression

        return condition == other.condition
                && trueExpression == other.trueExpression
                && falseExpression == other.falseExpression
    }

    override fun hashCode(): Int {
        var result = condition.hashCode()
        result = 31 * result + trueExpression.hashCode()
        result = 31 * result + falseExpression.hashCode()
        return result
    }
}