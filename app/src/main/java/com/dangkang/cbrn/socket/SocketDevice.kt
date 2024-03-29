package com.dangkang.cbrn.socket

import com.dangkang.Constant
import com.dangkang.core.utils.L
import com.dangkang.core.utils.StringUtil
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException

class SocketDevice(
    socket: Socket, socketCallBack: SocketCallBack, readTimeOut: Boolean
) : AbstractDevice {
    private var mInputStream: InputStream? = null
    private var mOutputStream: OutputStream? = null
    private var mSocket: Socket? = null
    private var type: Int = Constant.SOCKET_DISCONNECT_APP
    private var mStop = true
    private var mCurrentSystemTime: Long = 0
    override var ip: String = ""
    override var alive: Boolean = false
    private var mReadTimeOut: Boolean
    private var mSocketCallBack: SocketCallBack
    private val mSocketTimerTask: Runnable = Runnable {
        while (mStop) {
            val time = System.currentTimeMillis() - mCurrentSystemTime
            if (time > Constant.SOCKET_TIME_OUT) {
                type = Constant.SOCKET_DISCONNECT_DEVICE
                mSocket!!.close()
                break
            }
        }
    }


    init {
        this.mSocket = socket
        this.mInputStream = socket.getInputStream()
        this.mSocketCallBack = socketCallBack
        this.mOutputStream = socket.getOutputStream()
        this.mReadTimeOut = readTimeOut
    }

    override fun read(): Boolean {
        val buffer = ByteArray(1024)
        var len: Int
        var receiveStr = ""
        mCurrentSystemTime = System.currentTimeMillis()
        if (mReadTimeOut) {
            Thread {
                mSocketTimerTask.run()
            }.start()
        }
        ip = mSocket!!.inetAddress.hostAddress as String
        try {
            while (mInputStream!!.read(buffer).also { len = it } != -1 && !mSocket!!.isClosed) {
                mCurrentSystemTime = System.currentTimeMillis()
                receiveStr += String(buffer, 0, len, Charsets.UTF_8)
                if (len < 1024) {
                    alive = true
                    mSocketCallBack.receiverByte(receiveStr.toByteArray(Charsets.UTF_8), ip)
                    mSocketCallBack.receiverString(receiveStr,ip)
                    receiveStr = ""
                }
            }
            return true
        } catch (e: java.lang.Exception) {
            if (e is SocketException) {
                if (type == Constant.SOCKET_DISCONNECT_DEVICE) {
                    mSocketCallBack.disconnect(1, ip)
                    alive = false
                } else if (type == Constant.SOCKET_DISCONNECT_APP) {
                    mSocketCallBack.disconnect(2, ip)
                    alive = false
                }
                mOutputStream!!.close()
                mInputStream!!.close()
                mSocket!!.close()
                mStop = false
            } else if (e is IOException) {
                L.e("读取通道关闭")
            }
            return false
        }


    }

    override fun write(byteArray: ByteArray): Boolean {
        return if (mStop) {
            try {
                L.e(StringUtil.byte2HexStr(byteArray))
                mOutputStream!!.write(byteArray)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            true
        } else {
            false
        }
    }

    override fun close() {
        mStop = false
        mOutputStream!!.close()
        mInputStream!!.close()
        mSocket!!.close()
    }


}