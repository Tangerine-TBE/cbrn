package com.dangkang.cbrn.service

import com.dangkang.core.utils.L
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import java.net.SocketException

class SocketDevice(socket: Socket) : AbstractDevice {
    private var mInputStream: InputStream
    private var mPrintWriter: PrintWriter
    private var mSocket: Socket? =null
    private var type:Int = 0
    private var mStop  = true
    private var mCurrentSystemTime :Long =0
    private val mSocketTimerTask:Runnable = Runnable {
        while (mStop){
            val time = System.currentTimeMillis() - mCurrentSystemTime
            if (time> 1000*10){
                type= 1
                mSocket!!.close()
                break
            }
        }
    }
    init {
        this.mSocket = socket
        this.mInputStream = socket.getInputStream()
        this.mPrintWriter = PrintWriter(
            BufferedWriter(
                OutputStreamWriter(
                    mSocket!!.getOutputStream(), Charsets.UTF_8
                )
            )
        )
    }

    override fun write() {
        mPrintWriter.println("11111")
    }

    override fun read(): Boolean {
        val buffer = ByteArray(1024)
        var len: Int
        var receiveStr = ""
        mCurrentSystemTime = System.currentTimeMillis()
        Thread{
             mSocketTimerTask.run()
         }.start()
        try {
            while (mInputStream.read(buffer).also { len = it } != -1 && !mSocket!!.isClosed) {
                mCurrentSystemTime = System.currentTimeMillis()
                receiveStr += String(buffer, 0, len, Charsets.UTF_8)
                if (len < 1024) {
                    L.e(receiveStr)
                    receiveStr = ""
                }
            }
            return true
        } catch (e: java.lang.Exception) {
            if (e is SocketException) {
                if(type == 0){
                    L.e("下位机主动断开连接")
                }else{
                    L.e("连接超时，上位机主动断开")
                }
                mInputStream.close()
                mSocket!!.close()
                mStop = false
            } else if (e is IOException) {
                L.e("读取通道关闭")
            }
            return false
        }


    }
}