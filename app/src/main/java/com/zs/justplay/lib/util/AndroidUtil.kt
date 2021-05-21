package net.wecash.spacebox.wecashlibrary.utils

import android.content.Context
import android.content.pm.PackageManager



/**
 * Created by zhangshao on 2018/5/2.
 */
open class AndroidUtil {

    companion object {
        /**
         * 返回屏幕宽高值value[0]=width;value[1]=height
         */
        fun getDisplayMetrics(context: Context): IntArray {
            val whArray = IntArray(2)
            var display = context.resources.displayMetrics
            whArray[0] = display.widthPixels
            whArray[1] = display.heightPixels
            return whArray
        }

        fun getDisplayWidth(context:Context):Int{
            return context.resources.displayMetrics.widthPixels
        }

        fun getDisplayHeight(context:Context):Int{
            return context.resources.displayMetrics.heightPixels
        }

        /*******
         * dp转换px
         *
         * @param dp
         * @return
         */
        fun dp2Px(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }

        /**
         * px转换dp
         *
         * @param px
         * @return
         */
        fun px2Dp(context: Context, px: Int): Int {
            val scale = context.resources.displayMetrics.density
            return ((px - 05f) * scale).toInt()
        }

        /**
         * 获取状态栏高度
         * @return
         */
        private fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        /**
         * 获取versionName
         */
        fun getVersionName(context: Context): String {
            var verName = ""
            try {
                verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verName
        }

        /**
         * 获取versionCode
         */
        fun getVersionCode(context: Context): Int {
            var versionCode = 0
            try {
                versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return versionCode
        }



    }
}