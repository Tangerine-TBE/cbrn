package com.dangkang.cbrn.service

import com.dangkang.Constant
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
class SocketServer private constructor(port: Int) {
    private var serverSocket: ServerSocket? = null
    private var connect = true
    private var close = true
    private val devicesCache:ArrayList<AbstractDevice> = ArrayList()

    companion object {
        val instance: SocketServer by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SocketServer(Constant.SOCKET_SERVER_PORT)
        }
    }

    /*初始化设置*/
    init {
        serverSocket = ServerSocket(port)
    }
    fun start(){
        close = true
        Thread{
            while (close){
                /*过长时间没有设备连入时*/
                val socketDevice =  serverSocket!!.accept()
                val device = SocketDevice(socketDevice)
                /*拟定一个设备为可用设备*/
                devicesCache.add(device)
                while (connect && close){
                    if (socketDevice != null){
                        /*过长时间没有心跳时，当作隐身设备处理*/
                        /**1.计时器处理*/
                        /*阻塞的*/
                        connect = device.read()
                        if (!connect){
                            //当连接不可用时，将这个设备移除设备存储区
                            devicesCache.remove(device)
                        }
                    }
                }
                connect = true
            }

        }.start()
    }


    fun destroyServer() {
        close = false
        if (serverSocket != null) {
            serverSocket?.close()
        }
    }
}