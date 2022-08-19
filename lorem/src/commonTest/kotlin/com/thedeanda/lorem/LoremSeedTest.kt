package lorem

import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
class LoremSeedTest {
    private lateinit var lorem: Lorem

    @BeforeTest
    fun init() {
        lorem = LoremIpsum(99L)
    }

    @Test
    fun testSeededPseudoRandom() {
        assertEquals("Lincoln Heights", lorem.getCity())
        assertEquals("donec viverra", lorem.getWords(2))
        assertEquals("Benin", lorem.getCountry())
        assertEquals("(783) 177-2145", lorem.getPhone())
    }
}