package com.anningtex.networkrequestchild.demo.base

import android.content.res.Resources
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.anningtex.networkrequestchild.AppViewModel
import com.anningtex.networkrequestchild.R
import com.anningtex.networkrequestchild.demo.utils.SettingUtil
import com.anningtex.networkrequestparent.jetpack.BaseViewModel
import com.anningtex.networkrequestparent.jetpack.BaseVmDbActivity
import com.blankj.utilcode.util.ToastUtils
import me.jessyan.autosize.AutoSizeCompat

/**
 * @author Song
 * desc: 项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，就继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    private var dialog: MaterialDialog? = null

    val appViewModel: AppViewModel by lazy { getAppViewModel() }

    abstract override fun layoutId(): Int

    abstract override fun initView()

    /**
     * 创建观察者
     */
    abstract override fun createObserver()

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        if (dialog == null) {
            dialog = this.let {
                MaterialDialog(it).cancelable(true).cancelOnTouchOutside(false)
                    .customView(R.layout.layout_custom_progress_dialog_view).lifecycleOwner(this)
            }
            dialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.loading_tips).text = message
                appViewModel.appColor.value?.let {
                    this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList =
                        SettingUtil.getOneColorStateList(it)
                }
            }
        }
        dialog?.show()
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dialog?.dismiss()
    }

    /**
     * 吐司
     */
    override fun showToast(message: String) {
        ToastUtils.showShort(message)
    }

    /**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     */
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }
}