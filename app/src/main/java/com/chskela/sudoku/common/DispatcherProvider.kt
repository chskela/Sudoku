package com.chskela.sudoku.common

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    fun providerUIContext(): CoroutineContext
    fun providerIOContext(): CoroutineContext
}