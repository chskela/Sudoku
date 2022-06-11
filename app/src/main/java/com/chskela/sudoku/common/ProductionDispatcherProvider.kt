package com.chskela.sudoku.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object ProductionDispatcherProvider : DispatcherProvider {

    override fun providerUIContext(): CoroutineContext = Dispatchers.Main

    override fun providerIOContext(): CoroutineContext = Dispatchers.IO
}