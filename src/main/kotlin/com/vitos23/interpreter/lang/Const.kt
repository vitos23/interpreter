package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

class Const(val value: Int) : Expression() {

    override fun evaluate(env: Environment): Int = value

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Const

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }
}