package com.anningtex.networkrequestchild.demo.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.anningtex.networkrequestchild.R
import com.anningtex.networkrequestchild.databinding.ActivityLoginBinding
import com.anningtex.networkrequestchild.demo.anlogLogin.LoginAnLogActivity
import com.anningtex.networkrequestchild.demo.base.BaseActivity
import com.anningtex.networkrequestchild.demo.base.showMessage
import com.anningtex.networkrequestchild.demo.utils.CacheUtil
import com.anningtex.networkrequestparent.jetpack.ext.parseState
import com.anningtex.networkrequestparent.jetpack.ext.util.setOnclickNoRepeat
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

/**
 * @author Song
 * desc:AnStore
 */
class LoginActivity : BaseActivity<LoginModel, ActivityLoginBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        mDatabind.viewModel = mViewModel

//        toolbar.initClose("登录") {
//            Navigation.findNavController(it).navigateUp()
//        }
    }

    override fun createObserver() {
        //监听请求结果
        mViewModel.loginResult.observe(this) { resultState ->
            parseState(resultState, {
                //登录成功 通知账户数据发生改变
                CacheUtil.setUser(it)
                appViewModel.userinfo.postValue(it)
//                Navigation.findNavController(toolbar).navigateUp()
                showToast(it.msg)
            }, {
                //登录失败
                showToast(it.errorMsg)
            })
        }
    }

    override fun onViewClicked() {
        setOnclickNoRepeat(loginClear, loginSub, loginAnLog) {
            when (it.id) {
                R.id.loginClear -> {
                    mViewModel.userName.set("")
                }
                R.id.loginSub -> {
                    when {
                        mViewModel.userName.get().isEmpty() -> showMessage("请填写账号")
                        mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                        else -> mViewModel.loginRequest()
                    }
                }
                R.id.loginAnLog -> {
                    startActivity<LoginAnLogActivity>()
                }
            }
        }
        loginKey.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                loginPwd.transformationMethod = HideReturnsTransformationMethod.getInstance();
            } else {
                loginPwd.transformationMethod = PasswordTransformationMethod.getInstance();
            }
            loginPwd.setSelection(loginPwd.length())
        }
    }
}