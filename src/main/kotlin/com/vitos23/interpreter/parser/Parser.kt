package com.vitos23.interpreter.parser

import com.vitos23.interpreter.lang.Program

interface Parser {

    fun parse(): Program
}