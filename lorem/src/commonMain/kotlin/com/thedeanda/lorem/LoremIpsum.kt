package com.thedeanda.lorem

import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import com.thedeanda.lorem.data.*
import kotlin.jvm.JvmOverloads
import kotlin.native.concurrent.ThreadLocal
import kotlin.random.Random

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
private val firstNames = maleNames + femaleNames
private val words = lorem

class LoremIpsum constructor(random: Random) : Lorem {
    private val random: Random
    private val URL_HOSTS = arrayOf(
        "https://www.google.com/#q=",
        "http://www.bing.com/search?q=",
        "https://search.yahoo.com/search?p=",
        "https://duckduckgo.com/?q="
    )

    @JvmOverloads
    constructor(seed: Long? = null) : this(if (seed == null) Random.Default else Random(seed))

    init {
        this.random = random
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getCity()
	 */
    override fun getCity(): String {
        return getRandom(cities)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getCountry()
	 */
    override fun getCountry(): String {
        return getRandom(countries)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getEmail()
	 */
    override fun getEmail(): String {
        val sb: StringBuilder = StringBuilder()
        sb.append(getFirstName().lowercase())
        sb.append(".")
        sb.append(getLastName().lowercase())
        sb.append("@example.com")
        return sb.toString().replace(' ', '.')
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getFirstName()
	 */
    override fun getFirstName(): String {
        return getRandom(firstNames)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getFirstNameMale()
	 */
    override fun getFirstNameMale(): String {
        return getRandom(maleNames)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getFirstNameFemale()
	 */
    override fun getFirstNameFemale(): String {
        return getRandom(femaleNames)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getLastName()
	 */
    override fun getLastName(): String {
        return getRandom(surnames)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getName()
	 */
    override fun getName(): String {
        return getFirstName() + " " + getLastName()
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getNameMale()
	 */
    override fun getNameMale(): String {
        return getFirstNameMale() + " " + getLastName()
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getNameFemale()
	 */
    override fun getNameFemale(): String {
        return getFirstNameFemale() + " " + getLastName()
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getTitle(int)
	 */
    override fun getTitle(count: Int): String {
        return getWords(count, count, true)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getTitle(int, int)
	 */
    override fun getTitle(min: Int, max: Int): String {
        return getWords(min, max, true)
    }

    private fun getCount(_min: Int, _max: Int): Int {
        var min = _min
        var max = _max
        if (min < 0) min = 0
        if (max < min) max = min
        return if (max != min) random.nextInt(max - min) + min else min
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getHtmlParagraphs(int, int)
	 */
    override fun getHtmlParagraphs(min: Int, max: Int): String {
        val count = getCount(min, max)
        val sb: StringBuilder = StringBuilder()
        for (i in 0 until count) {
            sb.append("<p>")
            sb.append(getParagraphs(1, 1))
            sb.append("</p>")
        }
        return sb.toString().trim { it <= ' ' }
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getParagraphs(int, int)
	 */
    override fun getParagraphs(min: Int, max: Int): String {
        val count = getCount(min, max)
        val sb: StringBuilder = StringBuilder()
        for (j in 0 until count) {
            val sentences: Int = random.nextInt(5) + 2 // 2 to 6
            for (i in 0 until sentences) {
                var first = getWords(1, 1, false)
                first = (first.substring(0, 1).uppercase()
                        + first.substring(1))
                sb.append(first)
                sb.append(getWords(2, 20, false))
                sb.append(".  ")
            }
            sb.append("\n")
        }
        return sb.toString().trim { it <= ' ' }
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getUrl()
	 */
    override fun getUrl(): String {
        val sb: StringBuilder = StringBuilder()
        val hostId: Int = random.nextInt(URL_HOSTS.size)
        val host: String = URL_HOSTS[hostId] + getWords(1)
        sb.append(host)
        return sb.toString()
    }

    private fun getWords(min: Int, max: Int, title: Boolean): String {
        val count = getCount(min, max)
        return getWords(count, title)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getWords(int)
	 */
    override fun getWords(count: Int): String {
        return getWords(count, count, false)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getWords(int, int)
	 */
    override fun getWords(min: Int, max: Int): String {
        return getWords(min, max, false)
    }

    private fun getWords(count: Int, title: Boolean): String {
        val sb: StringBuilder = StringBuilder()
        val size = words.size
        var wordCount = 0
        while (wordCount < count) {
            var word = words[random.nextInt(size)]
            if (title) {
                if (wordCount == 0 || word.length > 3) {
                    word = (word.substring(0, 1).uppercase()
                            + word.substring(1))
                }
            }
            sb.append(word)
            sb.append(" ")
            wordCount++
        }
        return sb.toString().trim { it <= ' ' }
    }

    private fun getRandom(data: Array<String>): String {
        val size = data.size
        return data[random.nextInt(size)]
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getPhone()
	 */
    override fun getPhone(): String {
        val sb = StringBuilder()
        sb.append("(")
        sb.append(random.nextInt(9) + 1)
        for (i in 0..1) {
            sb.append(random.nextInt(10))
        }
        sb.append(") ")
        sb.append(random.nextInt(9) + 1)
        for (i in 0..1) {
            sb.append(random.nextInt(10))
        }
        sb.append("-")
        for (i in 0..3) {
            sb.append(random.nextInt(10))
        }
        return sb.toString()
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getStateAbbr()
	 */
    override fun getStateAbbr(): String {
        return getRandom(stateAbbr)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getStateFull()
	 */
    override fun getStateFull(): String {
        return getRandom(stateFull)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see com.thedeanda.lorem.Lorem#getZipCode()
	 */
    override fun getZipCode(): String {
        val sb: StringBuilder = StringBuilder()
        for (i in 0..4) {
            sb.append(random.nextInt(10))
        }
        return sb.toString()
    }

    override fun getPriorDate(maxDurationBeforeNow: TimeSpan): DateTime {
        val now: DateTime = DateTime.now()
        val offset = maxDurationBeforeNow.milliseconds * random.nextDouble()
        return now.add(0, -offset)
    }

    override fun getFutureDate(maxDurationFromNow: TimeSpan): DateTime {
        val now: DateTime = DateTime.now()
        val offset = maxDurationFromNow.milliseconds * random.nextDouble()
        return now.add(0, offset)
    }

    companion object {
        /*
         * this command was useful:
         *
         * cat lorem.txt | sed -e 's/[,;.]//g' | sed -e 's/ /\n/g' | sed -e \
         * 'y/ABCDEFGHIJKLMNOPQRSTUVWXYZ/abcdefghijklmnopqrstuvwxyz/' | sort | \
         * uniq > lorem.txt.2
         */
        val instance: LoremIpsum by lazy { LoremIpsum() }
    }
}