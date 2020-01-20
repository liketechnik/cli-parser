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
 * An exception for errors in the parsing process.
 *
 * @author Florian Warzecha
 * @version 1.0
 * @since 1.0-dev
 * @date 07 of June 2017
 * @see Parser
 */
class ParserException(reason: String) : Exception(reason) {
    /**
     * States that the parameters name was found in the arguments but did not match the prefix.
     * @param argument The part of the [Parser.arguments] that contains the [parameter].
     * @param parameter The name of the [Parameter] that was matched.
     * @see Parameter.name
     * @see Parser
     */
    constructor(argument: String, parameter: String) : this(
            "Argument '$argument' contains parameter '$parameter but does not have any matching prefix."
    )
}