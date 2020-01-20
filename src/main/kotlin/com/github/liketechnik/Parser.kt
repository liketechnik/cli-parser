/*
 * Copyright 2020 Florian Warzecha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.liketechnik



/**
 * This class parses the passed arguments (from the cli) for the specified [parameters] and returns them on request.
 *
 * The values are internally stored and can be retrieved via their [id][Parameter.id] with the correct methods for the
 * value's data type (e. g. [getStringArgumentValue] for [Strings][String]).
 *
 * @author Florian Warzecha
 * @version 1.1
 * @since 1.0-dev
 * @date 07 of June 2017
 * @param arguments The arguments to parse (usually from cli).
 * @param argumentPrefix The prefix for long (default) argument forms (for example '--file=test.txt').
 * @param argumentSecondaryPrefix The prefix for short argument forms (for example '-f test.txt').
 * @param argumentValueSeparator The separator between argument and value for the long form.
 * @param parameters The [parameters][Parameter] to parse from the arguments.
 * @see Parameter
 */
class Parser @JvmOverloads constructor(val arguments: Array<String>, val argumentPrefix: String = "--",
                                       val argumentValueSeparator: String = "=", val argumentSecondaryPrefix: String = "-",
                                       val parameters: Array<Parameter>) {

    private val strValues: MutableMap<String, String> = mutableMapOf<String, String>()
    private val intValues: MutableMap<String, Int> = mutableMapOf<String, Int>()

    /**
     * Parses the passed arguments for the values for the specified parameters.
     */
    init{
        for (parameter in parameters) {
                if (parameter.type == ArgumentTypes.STRING) {
                    try {
                        strValues.putIfAbsent(parameter.id, parseStringArgument(parameter))
                    } catch (e: ParserException) {
                        strValues.putIfAbsent(parameter.id, parameter.defaultValue as String)
                    }
                } else if (parameter.type == ArgumentTypes.INT) {
                    try {
                        intValues.putIfAbsent(parameter.id, parseIntArgument(parameter))
                    } catch (e: ParserException) {
                        intValues.putIfAbsent(parameter.id, parameter.defaultValue as Int)
                    }
                }
        }
    }

    /**
     * Returns the parsed value for the specified parameter.
     * @param id The id of the parameter whose value is returned.
     * @return The value of the parameter.
     */
    fun getStringArgumentValue(id: String): String {
        val value: String? = strValues[id]
        if (value is String) {
            return value
        } else {
            return getParameterById(id).defaultValue as String
        }
    }

    /**
     * Returns the parsed value for the specified parameter.
     * @param id The id of the parameter whose value is returned.
     * @return The ([Int]) value of the parameter.
     */
    fun getIntArgumentValue(id: String): Int {
        val value: Int? = intValues[id]
        if (value is Int) {
            return value
        } else {
            return getParameterById(id).defaultValue as Int
        }
    }

    /**
     * Parses the [cli arguments][arguments] for the value of the specified [parameter].
     *
     * If it finds the long form of the parameter, the value of this is returned. If that is not
     * found, the short form is taken for search and the value is returned. If both long and short form are not found,
     * the [default value][Parameter.defaultValue] of the parameter is returned.
     * @see Parameter
     */
    @Throws(ParserException::class)
    private fun parseStringArgument(parameter: Parameter): String {
        var value: String = parameter.defaultValue.toString()

        for (i in this.arguments.indices) {
            val argument: String = this.arguments[i]
            if (argument.startsWith(argumentPrefix) &&
                    argument.substringAfter(argumentPrefix).substringBefore(argumentValueSeparator) == parameter.name) {
                value = argument.split(argumentValueSeparator, limit = 2)[1]
            } else if (argument.startsWith(argumentSecondaryPrefix) && argument.endsWith(parameter.shortName)) {
                val tmpVal: StringBuilder = StringBuilder()
                this.arguments.copyOfRange(i + 1, this.arguments.lastIndex + 1)
                        .takeWhile { !(it.startsWith(argumentPrefix) || it.startsWith(argumentSecondaryPrefix)) }
                        .forEach { tmpVal.append(it)
                                    tmpVal.append(" ")}
                value = tmpVal.removeRange(tmpVal.lastIndex, tmpVal.lastIndex + 1).toString()
            }
        }
        return value
    }

    /**
     * Tries to convert the string value for the parameter found by [parseStringArgument] to an [Int]. If this fails
     * it uses the default value from [parameter].
     * @param parameter The parameter whose int value from cli is parsed.
     * @see parseStringArgument
     */
    private fun parseIntArgument(parameter: Parameter): Int {
        try {
            return parseStringArgument(parameter).replace(" ", "").toInt()
        } catch (e: NumberFormatException) {
            return parameter.defaultValue as Int
        }
    }

    /**
     * Returns the parameter for the passed id.
     * @param id The id of the parameter that is returned.
     * @return The parameter with the specified [id].
     */
    @Throws(IllegalArgumentException::class)
    private fun getParameterById(id: String): Parameter {
        this.parameters
                .filter { it.id == id }
                .forEach { return it }
        throw IllegalArgumentException("No matching parameter found!")
    }
}
