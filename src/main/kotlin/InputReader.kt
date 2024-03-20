package net.integr

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

class InputReader {
    companion object {
        private fun check(path: Path): Boolean {
            return path.exists()
        }

        fun readInput(path: Path): JsonObject {
            if (check(path)) {
                return GsonBuilder().setPrettyPrinting().create().fromJson(path.readText(), JsonObject::class.java)
            } else throw InvalidPathException("Path does not exist!", path.toAbsolutePath().toString())
        }
    }
}