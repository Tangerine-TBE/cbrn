package com.dangkang.cbrn.service

interface AbstractDevice {
    fun write()
    fun read(): Boolean
    fun close()
    var power: Int
}