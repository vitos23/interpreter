package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

abstract class BinaryExpression(
    val identifier: String,
    private val left: Expression,
    private val right: Expression
) : Expression() {

    override fun evaluate(env: Environment): Int = calculate(left.evaluate(env), right.evaluate(env))

    protected abstract fun calculate(a: Int, b: Int): Int

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as BinaryExpression

        return identifier == other.identifier && left == other.left && right == other.right
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + left.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }
}