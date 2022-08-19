package lorem

import com.soywiz.klock.*
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import kotlin.test.*

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
class LoremTest {
    private lateinit var lorem: Lorem

    @BeforeTest
    fun init() {
        lorem = LoremIpsum.instance
    }

    private fun notBlank(s: String) {
        assertNotNull(s)
        assertNotEquals("", s.trim { it <= ' ' })
    }

    @Test
    fun testCity() {
        val s = lorem.getCity()
        notBlank(s)
    }

    @Test
    fun testCountry() {
        val s = lorem.getCountry()
        notBlank(s)
    }

    @Test
    fun testEmail() {
        for (i in 0 until LOOP_TIMES) {
            val s = lorem.getEmail()
            notBlank(s)
            assertFalse(s.contains(" "))
        }
    }

    @Test
    fun testNameMale() {
        val s = lorem.getNameMale()
        notBlank(s)
    }

    @Test
    fun testNameFemale() {
        val s = lorem.getNameFemale()
        notBlank(s)
    }

    @Test
    fun testParagraphs() {
        val p1 = lorem.getParagraphs(3, 5)
        notBlank(p1)
        val p2 = lorem.getParagraphs(6, 8)
        notBlank(p2)
        assertNotEquals(p1, p2)
    }

    @Test
    fun testState() {
        var s = lorem.getStateAbbr()
        notBlank(s)
        s = lorem.getStateFull()
        notBlank(s)
    }

    @Test
    fun testWords() {
        val words0 = lorem.getWords(2)
        assertNotNull(words0)
        assertNotEquals("", words0.trim { it <= ' ' })
        val words1 = lorem.getWords(4, 5)
        assertNotNull(words1)
        assertNotEquals("", words1.trim { it <= ' ' })
        val wordsplit = words1.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        assertNotNull(wordsplit)
        assertTrue(wordsplit.size >= 4)
        assertTrue(wordsplit.size <= 4)
        assertNotEquals(words0, words1)
    }

    @Test
    fun testZip() {
        val s = lorem.getZipCode()
        notBlank(s)
    }

    @Test
    fun testPriorDate() {
        val now: DateTime = DateTime.now().add(MonthSpan(0), 1.seconds)
        val earlier: DateTime = DateTime.now().add(MonthSpan(0), (-65).minutes)
        for (i in 0..99) {
            val pd: DateTime = lorem.getPriorDate(1.hours)
            assertTrue(now > pd)
            assertTrue(earlier < pd)
        }
    }

    @Test
    fun testFutureDate() {
        val now: DateTime = DateTime.now().add(MonthSpan(0), 1.seconds)
        val later: DateTime = DateTime.now().add(MonthSpan(0), 65.minutes)
        for (i in 0..99) {
            val fd: DateTime = lorem.getFutureDate(1.hours)
            assertTrue(now < fd)
            assertTrue(later > fd)
        }
    }

    companion object {
        private const val LOOP_TIMES = 5000
    }
}