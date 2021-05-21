package net.wecash.spacebox.wecashlibrary.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import java.text.DecimalFormat

/**
 * Created by zhangshao on 2018/5/4.
 */
open class StringUtil {

    companion object {

        /**
         * Float类型转字符串，并且处理小数点
         * 12.0 to "12"
         * 12.5 to "12.5"
         */
        fun float2String(float:Float):String{
            float.toString()
            val decimalFormat = DecimalFormat("###################.###########")
            return decimalFormat.parse(float.toString()).toString()
        }


        fun strikeString(srcString:String):SpannableString{
            val spanText = SpannableString(srcString)
            spanText.setSpan(StrikethroughSpan(), 0, spanText.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            return spanText
        }
    }
}