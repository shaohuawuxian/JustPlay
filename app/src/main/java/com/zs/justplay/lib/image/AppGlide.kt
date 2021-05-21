package com.zs.justplay.lib.image

import android.annotation.SuppressLint
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class AppGlide : AppGlideModule(){

    @SuppressLint("CheckResult")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val sdPath = context.externalCacheDir
        val path = "$sdPath/img/"
        builder.setDiskCache(DiskLruCacheFactory(path, (1024 * 1024 * 256).toLong()))//256M
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))


        val resId = context.resources.getIdentifier("icon_default_bitmap", "mipmap", context.packageName)
        //默认图片
        val options = RequestOptions()
            .set(Option.memory<Any>("com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout"), 6000)//网络连接和读取数据的超时时间
        if(resId>0){
            options.placeholder(resId)
                .error(resId)
                .fallback(resId)
        }

        builder.setDefaultRequestOptions(options)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }
}