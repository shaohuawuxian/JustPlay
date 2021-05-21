package net.wecash.spacebox.wecashlibrary.utils

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.content.ContextCompat
import java.net.NetworkInterface
import java.net.SocketException
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

/**
 * Created by zhangshao on 2018/6/5.
 */
class UDIDUtil {

    companion object {


        private var udid=""
        /**
         * 注意事项:
         *
         *  1. 非手机设备(如平板、电子书阅读器、电视、音乐播放器等)没有TELEPHONY_SERVICE。
         *  1. 获取DEVICE_ID需要[READ_PHONE_STATE][android.Manifest.permission.READ_PHONE_STATE]权限。
         *  1. 厂商定制系统中的Bug:少数手机设备上，由于该实现有漏洞，会返回垃圾数据，如:zeros或者asterisks。
         *
         *
         * @param context Context
         * @return 获取设备标识.
         */
        @SuppressLint("MissingPermission")
        fun getDeviceId(context: Context): String {
            return if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
            }else{
                ""
            }

        }


        /**
         * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来。
         * 当设备被wipe后该值会被重置。
         *
         *
         * 注意事项如下:
         *
         *  1.
         * 在2.2的系统上不是100%可靠,所以最低使用环境为2.3。
         *
         *  1.
         * 厂商定制系统的Bug:不同的设备可能会产生相同的ANDROID_ID:9774d56d682e549c。
         *
         *  1.
         * 厂商定制系统的Bug:有些设备返回的值为null。
         *
         *  1.
         * 设备差异:对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId()返回相同的值。
         *
         *
         * 综上:如果考虑重置系统的影响,不要使用ANDROID_ID.
         *
         * @param context Context
         * @return 16进制的字符串.
         */
        fun getAndroidId(context: Context): String {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        /**
         * Android系统2.3版本以上可以通过下面的方法得到Serial Number，且非手机设备也可以通过该接口获取。
         *
         * @return 获取设备序列号
         */
        fun getSerialNumber(): String {
            return Build.SERIAL
        }


        /**
         * 注意事项:
         *
         *  1. 硬件限制:并不是所有的设备都有Wifi硬件。
         *  1. 获取限制:WiFi没有打开时,获取不到Mac地址。
         *
         *
         * @param context Context
         * @return 获取WIFI的Mac地址
         */
        fun getWiFiMacAddress(context: Context): String? {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    val interfaceName = "wlan0"
                    val interfaces = NetworkInterface.getNetworkInterfaces()
                    while (interfaces.hasMoreElements()) {
                        val networkInterface = interfaces.nextElement()
                        if (networkInterface.name.equals(interfaceName, ignoreCase = true)) {
                            val addr = networkInterface.hardwareAddress
                            if (addr == null || addr.isEmpty()) {
                                continue
                            }

                            val sb = StringBuilder()
                            for (b in addr) {
                                sb.append(String.format("%02X:", b))
                            }
                            if (sb.isNotEmpty()) {
                                sb.deleteCharAt(sb.length - 1)
                            }
                            return sb.toString()
                        }
                    }
                } catch (e: SocketException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return null
            } else {
                return (context.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo.macAddress
            }
        }


        fun getUDID(context: Context):String{
            if(!TextUtils.isEmpty(udid)){
                return udid
            }
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            udid= preference.getString("android_udid","")!!
            if(!TextUtils.isEmpty(udid)){
                return udid
            }

            var deviceId= deviceUdid(context)
            if(TextUtils.isEmpty(deviceId)){
                deviceId = UUID.randomUUID().toString() + System.currentTimeMillis().toString()
            }
            val messageDigest = MessageDigest.getInstance("MD5")
            udid = bytes2HexStr(messageDigest.digest(deviceId.toByteArray()))
            preference.edit().putString("android_udid", udid).apply()
            return udid
        }


        private fun deviceUdid(context: Context):String{

            val stringBuilder = StringBuilder()
            stringBuilder.append(getDeviceId(context))
                    .append(getAndroidId(context)).append(getSerialNumber())
                    .append(getWiFiMacAddress(context))
            return stringBuilder.toString()
        }

        private fun bytes2HexStr(bytes: ByteArray): String {
            val sb = StringBuilder(bytes.size)
            var temp: String
            for (b in bytes) {
                temp = Integer.toHexString(0xFF and b.toInt())
                if (temp.length < 2)
                    sb.append(0)
                sb.append(temp)
            }
            return sb.toString()
        }

    }
}