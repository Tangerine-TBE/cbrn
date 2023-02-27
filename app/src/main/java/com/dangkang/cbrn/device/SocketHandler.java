package com.dangkang.cbrn.device;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.logging.LogRecord;

public class SocketHandler extends Handler {
    public SocketHandler(@NonNull Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 1:

        }
    }
}
