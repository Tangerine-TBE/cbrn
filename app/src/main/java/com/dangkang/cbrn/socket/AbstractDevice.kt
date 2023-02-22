package com.dangkang.cbrn.socket

interface AbstractDevice {
    fun write()
    fun read(): Boolean
    fun close()
}