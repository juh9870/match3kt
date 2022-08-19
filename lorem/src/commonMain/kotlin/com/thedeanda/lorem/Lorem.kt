package com.thedeanda.lorem

import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import kotlin.time.Duration

/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Miguel De Anda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author mdeanda
 */
interface Lorem {
    fun getCity(): String
    fun getCountry(): String
    fun getEmail(): String
    fun getFirstName(): String
    fun getFirstNameMale(): String
    fun getFirstNameFemale(): String
    fun getLastName(): String
    fun getName(): String
    fun getNameMale(): String
    fun getNameFemale(): String

    fun getTitle(count: Int): String
    fun getTitle(min: Int, max: Int): String
    fun getHtmlParagraphs(min: Int, max: Int): String
    fun getParagraphs(min: Int, max: Int): String
    fun getUrl(): String

    fun getWords(count: Int): String
    fun getWords(min: Int, max: Int): String
    fun getPhone(): String
    fun getStateAbbr(): String
    fun getStateFull(): String
    fun getZipCode(): String

    fun getPriorDate(maxDurationBeforeNow: TimeSpan): DateTime
    fun getFutureDate(maxDurationFromNow: TimeSpan): DateTime
}