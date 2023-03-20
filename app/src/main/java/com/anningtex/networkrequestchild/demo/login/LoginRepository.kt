package com.anningtex.networkrequestchild.demo.login

import com.anningtex.networkrequestchild.demo.net.NetworkApi

/**
 * @Author Song
 * @Date：2023-03-17
 */
class LoginRepository {

    suspend fun login(
        userName: String,
        pwd: String,
        countryId: String,
        deviceId: String,
        method: String,
        appVersion: String
    ): LoginEntity {
        return NetworkApi().service.getLogin(userName, pwd, countryId, deviceId, method, appVersion)
    }
}