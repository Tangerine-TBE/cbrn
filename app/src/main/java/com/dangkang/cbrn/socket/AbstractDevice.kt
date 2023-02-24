package com.dangkang.cbrn.socket

interface AbstractDevice {
    fun close()
    fun read(): Boolean
    fun write(byteArray: ByteArray):Boolean
    var ip:String
}