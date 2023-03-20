package com.anningtex.networkrequestchild.demo.login

import java.io.Serializable
import com.google.gson.annotations.SerializedName

/**
 * @Author Song
 * @Date：2023-03-17
 */
data class LoginEntity(
    @SerializedName("cityid")
    val cityid: String,
    @SerializedName("cityname")
    val cityname: String,
    @SerializedName("countryid")
    val countryid: String,
    @SerializedName("countryid_userinfo")
    val countryidUserinfo: String,
    @SerializedName("countryname")
    val countryname: String,
    @SerializedName("IsClient")
    val isClient: String,
    @SerializedName("ispass_anstore")
    val ispassAnstore: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("servername")
    val servername: String,
    @SerializedName("session_token")
    val sessionToken: String,
    @SerializedName("to_do_exchange_rate")
    val toDoExchangeRate: Boolean,
    @SerializedName("truename")
    val truename: String,
    @SerializedName("uname")
    val uname: String,
    @SerializedName("userclient_up")
    val userclientUp: String,
    @SerializedName("version")
    val version: String
)
//) : Serializable