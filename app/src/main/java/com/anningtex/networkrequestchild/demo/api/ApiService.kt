package com.anningtex.networkrequestchild.demo.api

import com.anningtex.networkrequestchild.demo.anlogLogin.LoginAnLogEntity
import com.anningtex.networkrequestchild.demo.login.LoginEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Author Song
 * @Date：2023-03-17
 */
interface ApiService {

    //**********AnStore*************
    @FormUrlEncoded
    @POST("/api/chkUser")
    suspend fun getLogin(
        @Field("username") username: String,
        @Field("pwd") pwd: String,
        @Field("countryid") countryId: String,
        @Field("deviceid") deviceId: String,
        @Field("method") method: String,
        @Field("app_version") appVersion: String
    ): LoginEntity

    //有code基类可用
    @FormUrlEncoded
    @POST("/api/chkUser")
    suspend fun getLogin1(
        @Field("username") username: String,
        @Field("pwd") pwd: String,
        @Field("countryid") countryId: String,
        @Field("deviceid") deviceId: String,
        @Field("method") method: String,
        @Field("app_version") appVersion: String
    ): ApiResponse<LoginEntity>

    //**********AnLog*************
    @FormUrlEncoded
    @POST("/login_in")
    suspend fun getLoginAnLog(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("device_id") deviceId: String
    ): ApiResponse<LoginAnLogEntity>

    @FormUrlEncoded
    @POST("/api/v1/getRemainingGoods")
    suspend fun getForeignRemaining(@Field("country_id") deviceId: String): ApiResponse<Any>
}