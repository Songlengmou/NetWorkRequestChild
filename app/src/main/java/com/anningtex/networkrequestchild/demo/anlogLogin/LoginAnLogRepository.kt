package com.anningtex.networkrequestchild.demo.anlogLogin

import com.anningtex.networkrequestchild.demo.api.ApiResponse
import com.anningtex.networkrequestchild.demo.net.NetworkApi

/**
 * @Author Song
 * @Date：2023-03-17
 */
class LoginAnLogRepository {

    suspend fun login(
        userName: String,
        pwd: String,
        deviceId: String
    ): ApiResponse<LoginAnLogEntity> {
        return NetworkApi().service.getLoginAnLog(userName, pwd, deviceId)
    }
}