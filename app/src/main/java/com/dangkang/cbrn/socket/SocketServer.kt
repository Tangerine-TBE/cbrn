package com.dangkang.cbrn.socket

import com.dangkang.Constant
import com.dangkang.core.utils.L
import java.net.ServerSocket

/**
 * handler 画面更新
 * 单例
 * 回调
 * 构造
 * 策略
 * 心跳
 * 超时*/
class SocketServer private constructor() {
    private var serverSocket: ServerSocket? = null
    private var connect = true
    private var close = true
    private val devicesCache: ArrayList<AbstractDevice> = ArrayList()

    companion object {
        val instance: SocketServer by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SocketServer()
        }
    }

    /*初始化设置*/
    /*打开socket端口*/
    fun start(socketCallBack: SocketCallBack) {
        close = true
        serverSocket = ServerSocket(Constant.SOCKET_SERVER_PORT)
        Thread {
            try {
                while (close) {
                    val socketDevice = serverSocket!!.accept()
                    val device = SocketDevice(socketDevice, socketCallBack,
                        readTimeOut = true,
                    )
                    devicesCache.add(device)
                    Thread {
                        while (connect && close) {
                            connect = device.read()
                            if (!connect) {
                                devicesCache.remove(device)
                            }
                        }
                        connect = true
                    }.start()

                }

            } catch (e: Exception) {
                L.e(e.message)
            }

        }.start()
    }

    fun destroyServer() {
        close = false
        if (serverSocket != null) {
            serverSocket?.close()
        }
        for (i in 0 until devicesCache.size) {
            devicesCache[i].close()
        }
        devicesCache.clear()
    }

    fun findDeviceByIp(ip: String):AbstractDevice? {

        for (item in devicesCache) {
           if (item.ip == ip) {
               return item
           }
        }
        return null
    }

    fun devicesCache(): ArrayList<AbstractDevice> {
        if (!close) {
            L.e("Socket端口已关闭")
        }
        return devicesCache
    }

    fun isStart(): Boolean {
        return close
    }
}