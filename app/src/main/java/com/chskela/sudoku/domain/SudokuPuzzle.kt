package com.chskela.sudoku.domain

import java.io.Serializable
import java.util.*
import kotlin.collections.LinkedHashMap

data class SudokuPuzzle(
    val boundary: Int,
    val difficulty: Difficulty,
    val graph: LinkedHashMap<Int, LinkedList<SudokuNote>> = buildNewSudoku(
        boundary,
        difficulty
    ).graph,
    val elapsedTime: Long = 0L
) : Serializable {
    fun getValue(): LinkedHashMap<Int, LinkedList<SudokuNote>> = graph
}
