package com.juh9870.match3kt.core.painter

import com.juh9870.match3kt.core.SRandom
import com.juh9870.serialization.DeepCopyable
import com.juh9870.serialization.deepCopy
import com.juh9870.utils.RandomExtensions.Companion.chances
import com.juh9870.utils.SoftGenericWorkaround

interface RandomCellDataProvider<T> : DeepCopyable {
    fun getCellData(): T
}

class ArrayCellDataProvider<T>(private var random: SRandom, private val data: Array<T>) : RandomCellDataProvider<T> {
    override fun getCellData(): T {
        return data[random.nextInt(data.size)]
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return ArrayCellDataProvider(random.deepCopy(), data.copyOf())
    }
}

class WeightedArrayCellDataProvider<T>(private var random: SRandom, private val data: Array<T>, private val weights: FloatArray) : RandomCellDataProvider<T> {
    private val sum = weights.sum()

    override fun getCellData(): T {
        return data[random.chances(weights, sum)]
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return WeightedArrayCellDataProvider(random.deepCopy(), data.copyOf(), weights)
    }
}