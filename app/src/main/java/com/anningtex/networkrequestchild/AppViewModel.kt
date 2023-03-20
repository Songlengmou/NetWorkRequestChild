package com.anningtex.networkrequestchild

import com.anningtex.networkrequestparent.jetpack.BaseViewModel
import com.anningtex.networkrequestparent.jetpack.callback.UnPeekLiveData
import com.anningtex.networkrequestchild.demo.login.LoginEntity
import com.anningtex.networkrequestchild.demo.utils.CacheUtil
import com.anningtex.networkrequestchild.demo.utils.SettingUtil

/**
 * @author Song
 * desc:APP全局的ViewModel，可以存放公共数据，当他数据改变时，所有监听他的地方都会收到回调,也可以做发送消息
 * 比如 全局可使用的 账户信息，事件通知等，
 */
class AppViewModel : BaseViewModel() {

    //App的账户信息
    var userinfo = UnPeekLiveData<LoginEntity>()

    //App主题颜色 大型项目不推荐以这种方式改变主题颜色，比较繁琐，且容易有遗漏某些地方没有设置主题
    var appColor = UnPeekLiveData<Int>()

    //App 列表动画
    var appAnimation = UnPeekLiveData<Int>()

    init {
        //默认值保存的账户信息，没有登陆过则为null
        userinfo.value = CacheUtil.getUser()
        //不建议ViewModel使用Context，但是这是个App级别的ViewMode，所以使用Application上下文没事
        appColor.value = SettingUtil.getColor(MyApplication.mContext)
        //初始化列表动画
        appAnimation.value = SettingUtil.getListMode()
    }
}