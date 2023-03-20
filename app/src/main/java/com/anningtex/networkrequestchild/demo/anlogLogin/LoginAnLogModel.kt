package com.anningtex.networkrequestchild.demo.anlogLogin

import androidx.lifecycle.MutableLiveData
import com.anningtex.networkrequestparent.jetpack.BaseViewModel
import com.anningtex.networkrequestparent.jetpack.databind.StringObservableField
import com.anningtex.networkrequestparent.jetpack.ext.request
import com.anningtex.networkrequestparent.jetpack.state.ResultState
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @Author Song
 * @Date：2023-03-17
 */
class LoginAnLogModel : BaseViewModel() {
    var userName = StringObservableField()
    var password = StringObservableField()

    private val loginAnLogRepository: LoginAnLogRepository by lazy { LoginAnLogRepository() }

    var loginResult = MutableLiveData<ResultState<LoginAnLogEntity>>()

    fun loginRequest() {
        request(
            { loginAnLogRepository.login(userName.get(), encodes(password.get()), "0") },
            loginResult,
            true,//是否显示等待框
            "正在登录中..."//等待框内容
        )
    }

    fun encodes(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest: ByteArray = instance.digest(text.toByteArray())
            val sb = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i: Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0$hexString"
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}