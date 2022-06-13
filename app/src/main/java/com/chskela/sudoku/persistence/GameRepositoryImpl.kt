package com.chskela.sudoku.persistence

import com.chskela.sudoku.domain.*

class GameRepositoryImpl(
    private val gameStore: IGameDataStorage,
    private val settingsStorage: ISettingsStorage
) : IGameRepository {

    override suspend fun saveGame(
        elapsedTime: Long,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getCurrentGameResult = gameStore.getCurrentGame()) {
            is GameStorageResult.OnSuccess -> {
                gameStore.updateGame(getCurrentGameResult.currentGame.copy(elapsedTime = elapsedTime))
                onSuccess(Unit)
            }
            is GameStorageResult.OnError -> {
                onError(getCurrentGameResult.exception)
            }
        }
    }

    override suspend fun updateGame(
        game: SudokuPuzzle,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val updateGameResult = gameStore.updateGame(game)) {
            is GameStorageResult.OnSuccess -> onSuccess(Unit)
            is GameStorageResult.OnError -> onError(updateGameResult.exception)
        }
    }

    override suspend fun updateNote(
        x: Int,
        y: Int,
        color: Int,
        elapsedTime: Long,
        onSuccess: (isComplete: Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val result = gameStore.updateNode(x, y, color, elapsedTime)) {
            is GameStorageResult.OnSuccess -> onSuccess(
                puzzleIsComplete(result.currentGame)
            )
            is GameStorageResult.OnError -> onError(result.exception)
        }
    }

    /**
     * This is mainly where this repository becomes important. I didn't want the front end decision
     * maker to make all of these decisions, but adding in an interactor/usecase/transaction script
     * is overkill for an app of this size.
     * 1. Request current game
     * 2a. Current game returns onSuccess; forward to caller onSuccess
     * 2b. Current game returns onError
     * 3b. Request current Settings from settingsStorage
     * 4c. settingsStorage returns onSuccess
     * 4d. settingsStorage returns onError
     * 5c. Write game update to gameStorage (to ensure consistent state between front and back end)
     * 5d. We're screwed at this point ¯\_(ツ)_/¯ ; forward to caller onError
     * 6e. gameStorage returns onSuccess; forward to caller onSuccess
     * 6f. gameStorage returns onError; forward to caller onError
     */
    override suspend fun getCurrentGame(
        onSuccess: (currentGame: SudokuPuzzle, isComplete: Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        when (val getCurrentGameResult = gameStore.getCurrentGame()) {
            is GameStorageResult.OnSuccess -> onSuccess(
                getCurrentGameResult.currentGame,
                puzzleIsComplete(getCurrentGameResult.currentGame)
            )
            is GameStorageResult.OnError -> {
                when(val getSettingsResult = settingsStorage.getSettings()) {
                    is SettingsStorageResult.OnSuccess -> {
                        when(val updateGameResult = createAndWriteNewGame(getSettingsResult.settings)) {

                        }
                    }
                    is SettingsStorageResult.OnError -> TODO()
                }
                onError(getCurrentGameResult.exception)
            }

        }
    }

    override suspend fun getSettings(onSuccess: (Settings) -> Unit, onError: (Exception) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSettings(
        settings: Settings,
        onSuccess: (Unit) -> Unit,
        onError: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}