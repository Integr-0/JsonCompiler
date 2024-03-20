package net.integr

import com.google.gson.JsonObject

class Interpreter {
    companion object {
        fun interpret(json: JsonObject, vars: MutableMap<String, String>) {
            val map = json.asMap()
            val variables: MutableMap<String, String> = vars
            for (key in map.keys) {
                if (key.startsWith("printLn")) {
                    println(map[key]!!.asString.trim('"').replaceVariables(variables))
                }

                if (key.startsWith("exit")) {
                    break
                }

                if (key.startsWith("if")) {
                    if (conditionToBoolean(map[key]!!.asJsonObject["condition"].asString.replaceVariables(variables))) {
                        interpret(map[key]!!.asJsonObject["execute"].asJsonObject, variables)
                    }
                }

                if (key.startsWith("var")) {
                    variables[key] = map[key]!!.asString
                }

                if (key.startsWith("while")) {
                    while (conditionToBoolean(map[key]!!.asJsonObject["condition"].asString.replaceVariables(variables))) {
                        interpret(map[key]!!.asJsonObject["execute"].asJsonObject, variables)
                    }
                }

                if (key.startsWith("repeat")) {
                    val amount = map[key]!!.asJsonObject["amount"].asString.replaceVariables(variables).toInt()
                    for (i in (0..<amount)) {
                        variables["var_i"] = i.toString()
                        interpret(map[key]!!.asJsonObject["execute"].asJsonObject, variables)
                    }
                }

                if (key.startsWith("readLn")) {
                    val varName = map[key]!!.asString
                    variables[varName] = readln()
                }
            }
        }

        private fun conditionToBoolean(condition: String): Boolean {
            if (condition.contains("=")) {
                val n = condition.replace(" ", "")
                val s = n.split("=")
                return s[0] == s[1]
            } else if (condition.contains("<")) {
                val n = condition.replace(" ", "")
                val s = n.split("<")
                return s[0].toInt() < s[1].toInt()
            } else if (condition.contains(">")) {
                val n = condition.replace(" ", "")
                val s = n.split(">")
                return s[0].toInt() > s[1].toInt()
            } else if (condition.contains("<=")) {
                val n = condition.replace(" ", "")
                val s = n.split("<=")
                return s[0].toInt() <= s[1].toInt()
            } else if (condition.contains(">=")) {
                val n = condition.replace(" ", "")
                val s = n.split(">=")
                return s[0].toInt() >= s[1].toInt()
            }

            return false
        }

        private fun String.replaceVariables(variables: MutableMap<String, String>): String {
            var new = this
            for (key in variables.keys) {
                new = new.replace(key, variables[key]!!)
            }

            return new
        }
    }
}