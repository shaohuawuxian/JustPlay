package net.wecash.spacebox.wecashlibrary.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by zhangshao on 2018/6/5.
 */
class NetWorkUtil {

    companion object {

        fun checkNetWork(context: Context):Boolean{
            try {
                val connectivityManager = context.applicationContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
                    return false
                }
            } catch (e: Exception) {
                return false
            }

            return true
        }


        fun getNetWorkType(context: Context):String{
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.activeNetworkInfo
            if(info!=null && info.isAvailable){
                if (info.type == ConnectivityManager.TYPE_WIFI) run {
                    return "wifi"
                }else if(info.type == ConnectivityManager.TYPE_MOBILE){
                    return "mobile"
                }
            }else{
                //网络不可用
            }
            return "UNKNOWN"
        }
    }
}