package com.anningtex.networkrequestchild.demo.net

import com.anningtex.networkrequestchild.MyApplication
import com.anningtex.networkrequestchild.demo.api.ApiConstants
import com.anningtex.networkrequestchild.demo.api.ApiService
import com.anningtex.networkrequestparent.jetpack.network.BaseNetworkApi
import com.anningtex.networkrequestparent.jetpack.network.CoroutineCallAdapterFactory
import com.anningtex.networkrequestparent.jetpack.network.interceptor.CacheInterceptor
import com.anningtex.networkrequestparent.jetpack.network.interceptor.logging.LogInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Author Song
 * desc: 网络请求构建器，继承BaseNetworkApi 并实现setHttpClientBuilder/setRetrofitBuilder方法，
 * 在这里可以添加拦截器，设置构造器可以对Builder做任意操作
 */
class NetworkApi : BaseNetworkApi() {
    //封装NetApiService变量
    val service: ApiService by lazy {
        getApi(ApiService::class.java, ApiConstants.baseUrl)
    }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(MyApplication.mContext.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(HeadInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }
    }

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.mContext))
    }
}
