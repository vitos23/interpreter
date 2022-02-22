package com.vitos23.interpreter.tool

import com.vitos23.interpreter.exception.IncorrectSyntaxException
import com.vitos23.interpreter.exception.LangException
import com.vitos23.interpreter.lang.Program
import com.vitos23.interpreter.lexer.LangLexer
import com.vitos23.interpreter.parser.LangParser
import com.vitos23.interpreter.vm.StandardEnvironment
import java.io.File
import java.io.IOException

class CmdLineInterface : SdkInterface {

    override fun start(args: Array<String>) {
        if (args.size != 1) {
            println("Expected only one argument - the name of the program source code file")
            return
        }
        val source = readFile(args[0]) ?: return
        execute(source)
    }

    private fun execute(source: String) {
        val program: Program

        try {
            program = LangParser(LangLexer(source)).parse()
        } catch (e: IncorrectSyntaxException) {
            println("Syntax error: ${e.message}")
            return
        }

        try {
            println(program.run(StandardEnvironment()))
        } catch (e: LangException) {
            println("An error occurred while executing program: ${e.message}")
        }
    }

    /**
     * @return file content or null if the file can't be read
     */
    private fun readFile(path: String): String? {
        return try {
            File(path).readText()
        } catch (e: IOException) {
            println("An error occurred while reading file $path: ${e.message}")
            null
        }
    }
}