package com.chskela.sudoku.domain

import java.io.Serializable

data class SudokuNote(
    val x: Int,
    val y: Int,
    var color: Int = 0,
    var readOnly: Boolean = true
) : Serializable {
    override fun hashCode(): Int {
        return getHash(x, y)
    }
}

internal fun getHash(x: Int, y: Int): Int = "${x * 100}$y".toInt()