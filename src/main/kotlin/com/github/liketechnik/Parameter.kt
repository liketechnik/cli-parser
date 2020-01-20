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
 * This class represents a command line parameter.
 *
 * [name] and [shortName] are used to identify the passed arguments and match their values to the correct parameters.
 * [defaultValue] must have the same type as [type] is stating; it is used when the parameter is not specified on the cli.
 * [type] defines which (java/kotlin) data type the value of the parameter should have.
 * [id] identifies the parameter amongst others in a parser and must be unique
 *
 * @author Florian Warzecha
 * @version 1.0
 * @since 1.0-dev
 * @date 07 of June 2017
 * @see Parser
 * @see ArgumentTypes
 */
abstract class Parameter {
    /**
     * The long (default) version of the parameter on the cli (e. g. ```version```).
     */
    abstract val name: String
    /**
     * The short (abbreviated) version of the parameter on the cli (e. g. ```v```).
     */
    abstract val shortName: String
    /**
     * When overriding please choose an appropriate type for the default value, in most cases it must be the same as
     * specified by [type]. The value of this property is used when the parameter was not found on the cli by the parser
     * or it was unable to convert it to the [type] requested.
     */
    abstract val defaultValue: kotlin.Any
    /**
     * The data type of the [parameter's][Parameter] value.
     * @see ArgumentTypes
     */
    abstract val type: ArgumentTypes
    /**
     * A unique id for identifying the parameter amongst others.
     */
    abstract val id: String
}


/**
 * The possible data types a [parameter's][Parameter] value can have.
 * @author Florian Warzecha
 * @version 1.0
 * @since 1.0-dev
 * @date 07 of June 2017
 * @see Parameter
 * @see Parameter.type
 */
enum class ArgumentTypes() {
    STRING, INT
}