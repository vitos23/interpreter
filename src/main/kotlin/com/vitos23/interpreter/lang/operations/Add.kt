package com.vitos23.interpreter.lang.operations

import com.vitos23.interpreter.lang.BinaryExpression
import com.vitos23.interpreter.lang.Expression

class Add(
    left: Expression,
    right: Expression
) : BinaryExpression("+", left, right) {

    override fun calculate(a: Int, b: Int): Int = a + b
}