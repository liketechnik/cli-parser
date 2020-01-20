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

import org.junit.Test

import org.junit.Assert.*

/**
 * @author Florian Warzecha
 * *
 * @version 1.1
 * *
 * @date 07 of June 2017
 */
class ParserTest {
    @Test
    fun parseStringArguments() {
        val params: Array<Parameter> = arrayOf(StringTestParam())

        val longFormValue = "b=bac"
        val longFormArgs: Array<String> = arrayOf("a", "--test2=c", "--test=$longFormValue")
        val shortFormValue1: String = "a"
        val shortFormValue2: String = "b"
        val shortFormArgs: Array<String> = arrayOf<String>("--test3=hallo", "-t", shortFormValue1, shortFormValue2)
        assertEquals(Parser(longFormArgs, parameters = params).getStringArgumentValue(StringTestParam().id), longFormValue)
        assertEquals(Parser(shortFormArgs, parameters = params).getStringArgumentValue(StringTestParam().id),
                shortFormValue1 + " " + shortFormValue2)
        assertEquals(Parser(arrayOf(), parameters = params).getStringArgumentValue(StringTestParam().id),
                StringTestParam().defaultValue)
    }

    @Test
    fun parseIntArguments() {
        val params: Array<Parameter> = arrayOf(IntTestParam())

        val longFormValue: String = "20"
        val longFormArgs: Array<String> = arrayOf("10", "--test2=100", "--test=$longFormValue")
        val shortFormValue1: String = "30"
        val shortFormValue2: String = "000"
        val shortFormArgs: Array<String> = arrayOf<String>("--test4=40", "-t", shortFormValue1, shortFormValue2)

        assertEquals(longFormValue.toInt(),
                Parser(longFormArgs, parameters = params).getIntArgumentValue(IntTestParam().id))
        assertEquals((shortFormValue1 + shortFormValue2).toInt(),
                Parser(shortFormArgs, parameters = params).getIntArgumentValue(IntTestParam().id))
        assertEquals(IntTestParam().defaultValue,
                Parser(arrayOf(), parameters = params).getIntArgumentValue(IntTestParam().id))
        assertEquals(IntTestParam().defaultValue,
                Parser(arrayOf("--test=hallo"), parameters = params).getIntArgumentValue(IntTestParam().id))
    }

    open class StringTestParam() : Parameter() {
        override val name: String = "test"
        override val shortName: String = "t"
        override val defaultValue: String = "default"
        override val id: String = name + defaultValue
        override val type: ArgumentTypes = ArgumentTypes.STRING
    }

    class IntTestParam : Parameter() {
        override val name: String = "test"
        override val shortName: String = "t"
        override val defaultValue: Int = 10
        override val id: String = name + defaultValue
        override val type: ArgumentTypes = ArgumentTypes.INT
    }
}