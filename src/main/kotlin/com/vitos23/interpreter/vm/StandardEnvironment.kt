package com.vitos23.interpreter.vm

import com.vitos23.interpreter.exception.EnvironmentException
import com.vitos23.interpreter.lang.FunctionStatement

open class StandardEnvironment(val callStackLimit: Int = 1024) : Environment {

    private var vars: HashMap<String, Int> = hashMapOf()
    private val functions = HashMap<String, FunctionStatement>()
    private var callStackSize = 0

    override fun getVariable(identifier: String): Int =
        vars[identifier] ?: throwError("Variable $identifier wasn't declared")

    override fun loadFunction(function: FunctionStatement) {
        functions[function.identifier] = function
    }

    override fun runFunction(identifier: String, arguments: List<Int>): Int {
        if (callStackSize == callStackLimit) {
            throwError("Call stack limit ($callStackLimit) exceeded")
        }

        val func = functions[identifier] ?: throwError("Function $identifier wasn't declared")
        if (func.parameters.size != arguments.size) {
            throwError("Function $identifier takes ${func.parameters.size} " +
                    "parameters while ${arguments.size} was given")
        }

        val cachedVars = vars

        vars = HashMap(arguments.size)
        for (i in func.parameters.indices) {
            vars[func.parameters[i]] = arguments[i]
        }

        callStackSize++
        val result = func.run(this)
        callStackSize--

        vars = cachedVars

        return result
    }

    private fun throwError(message: String = ""): Nothing {
        throw EnvironmentException(message)
    }
}