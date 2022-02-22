package com.vitos23.interpreter.lang

import com.vitos23.interpreter.vm.Environment

abstract class Expression : Statement() {

    abstract fun evaluate(env: Environment): Int
}