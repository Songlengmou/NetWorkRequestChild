package com.anningtex.networkrequestchild.demo.api

import com.anningtex.networkrequestparent.jetpack.network.BaseResponse

/**
 * desc:服务器返回数据的基类
 * 1.继承 BaseResponse
 * 2.重写isSuccess 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 3.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 */
class ApiResponse<T>(var code: Int, var msg: String, var data: T) : BaseResponse<T>() {

    override fun isSuccess(): Boolean {
        // 这里是示例，返回的码为 1 就代表请求成功
        return code == 1
    }

    override fun getResponseCode(): Int {
        //返回code
        return code
    }

    override fun getResponseMsg(): String {
        //返回消息
        return msg
    }

    override fun getResponseData(): T {
        //返回data
        return data
    }
}