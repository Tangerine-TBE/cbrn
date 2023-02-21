package com.dangkang.cbrn.service

import com.dangkang.Constant
import com.dangkang.core.utils.L
import java.net.ServerSocket
import java.net.Socket
import java.sql.Time
import java.util.Timer
import java.util.TimerTask

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
    fun start() {
        close = true
        serverSocket = ServerSocket(Constant.SOCKET_SERVER_PORT)
        Thread {
            try {
                while (close) {
                    /*过长时间没有设备连入时*/
                    val socketDevice = serverSocket!!.accept()
                    val device = SocketDevice(socketDevice)
                    /*拟定一个设备为可用设备*/
                    /*discover and connect*/
                    L.e("${socketDevice!!.inetAddress.hostAddress}:正在连接")
                    devicesCache.add(device)
                    Thread{
                        while (connect && close) {
                            connect = device.read()
                            if (!connect) {
                                //当连接不可用时，将这个设备移除设备存储区
                                /*disconnect*/
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