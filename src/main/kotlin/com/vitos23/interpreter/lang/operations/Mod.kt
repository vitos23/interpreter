package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.exception.ArithmeticException
import com.vitos23.interpreter.lang.BinaryExpression
import com.vitos23.interpreter.lang.Expression

class Mod(
    left: Expression,
    right: Expression
) : BinaryExpression("%", left, right) {

    override fun calculate(a: Int, b: Int): Int {
        if (b == 0) {
            throw ArithmeticException("Division by zero: $a % 0")
        }
        return a % b
    }
}