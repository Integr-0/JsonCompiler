package net.integr

import kotlin.io.path.Path

fun main() {
    Interpreter.interpret(InputReader.readInput(Path("./inputs/input.json")), mutableMapOf(Pair("var_1", "true")))
}