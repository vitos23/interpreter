package com.vitos23.interpreter.vm

import com.vitos23.interpreter.lang.FunctionStatement

interface Environment {

    fun getVariable(identifier: String): Int

    fun loadFunction(function: FunctionStatement)

    fun runFunction(identifier: String, arguments: List<Int>): Int
}