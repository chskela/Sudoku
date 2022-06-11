package com.chskela.sudoku.domain

interface IStatisticsRepository {

    suspend fun getStatistics(
        onSuccess: (UserStatistics) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateStatistic(
        time: Long,
        difficulty: Difficulty,
        boundary: Int,
        onSuccess: (isRecord: Boolean) -> Unit,
        onError: (Exception) -> Unit
    )
}