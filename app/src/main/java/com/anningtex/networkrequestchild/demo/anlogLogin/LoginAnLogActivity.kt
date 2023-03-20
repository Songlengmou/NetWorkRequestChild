package com.anningtex.networkrequestchild.demo.anlogLogin

import com.anningtex.networkrequestchild.R
import com.anningtex.networkrequestchild.databinding.ActivityLoginAnlogBinding
import com.anningtex.networkrequestchild.demo.base.BaseActivity
import com.anningtex.networkrequestchild.demo.base.showMessage
import com.anningtex.networkrequestparent.jetpack.ext.parseState
import com.anningtex.networkrequestparent.jetpack.ext.util.setOnclickNoRepeat
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @Author Song
 * @Desc:AnLog
 * @Date：2023-03-18
 */
class LoginAnLogActivity : BaseActivity<LoginAnLogModel, ActivityLoginAnlogBinding>() {
    override fun layoutId(): Int {
        return R.layout.activity_login_anlog
    }

    override fun initView() {
        mDatabind.viewModelAnLog = mViewModel
    }

    override fun createObserver() {
        mViewModel.loginResult.observe(this) { resultState ->
            parseState(resultState, {
                it.role?.let { it1 -> showToast(it1) }
            }, {
                //登录失败
                showToast(it.errorMsg)
            })
        }
    }

    override fun onViewClicked() {
        setOnclickNoRepeat(loginClear, loginSub, loginAnLog) {
            when (it.id) {
                R.id.loginSub -> {
                    when {
                        mViewModel.userName.get().isEmpty() -> showMessage("请填写账号")
                        mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                        else -> mViewModel.loginRequest()
                    }
                }
            }
        }
    }
}