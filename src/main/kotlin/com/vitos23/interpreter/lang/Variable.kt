package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

class Variable(val identifier: String) : Expression() {

    override fun evaluate(env: Environment): Int = env.getVariable(identifier)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Variable

        return identifier == other.identifier
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }
}