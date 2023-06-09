package com.anningtex.networkrequestchild.demo.base

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.anningtex.networkrequestchild.AppViewModel
import com.anningtex.networkrequestchild.MyApplication
import com.anningtex.networkrequestchild.demo.utils.SettingUtil
import com.blankj.utilcode.util.Utils
import com.anningtex.networkrequestparent.jetpack.Ktx
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

fun AppCompatActivity.getAppViewModel(): AppViewModel {
    (Utils.getApp() as MyApplication).let {
        return it.getAppViewModelProvider()[AppViewModel::class.java]
    }
}

fun Fragment.getAppViewModel(): AppViewModel {
    (Ktx.app as MyApplication).let {
        return it.getAppViewModelProvider()[AppViewModel::class.java]
    }
}

/**
 * @param message 显示对话框的内容 必填项
 * @param title 显示对话框的标题 默认 温馨提示
 * @param positiveButtonText 确定按钮文字 默认确定
 * @param positiveAction 点击确定按钮触发的方法 默认空方法
 * @param negativeButtonText 取消按钮文字 默认空 不为空时显示该按钮
 * @param negativeAction 点击取消按钮触发的方法 默认空方法
 *
 */
fun AppCompatActivity.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "",
    negativeAction: () -> Unit = {}
) {
    MaterialDialog(this).cancelable(false).lifecycleOwner(this).show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveButtonText) {
            positiveAction.invoke()
        }
        if (negativeButtonText.isNotEmpty()) {
            negativeButton(text = negativeButtonText) {
                negativeAction.invoke()
            }
        }
        getActionButton(WhichButton.POSITIVE).updateTextColor(SettingUtil.getColor(this@showMessage))
        getActionButton(WhichButton.NEGATIVE).updateTextColor(SettingUtil.getColor(this@showMessage))
    }
}

/**
 * @param message 显示对话框的内容 必填项
 * @param title 显示对话框的标题 默认 温馨提示
 * @param positiveButtonText 确定按钮文字 默认确定
 * @param positiveAction 点击确定按钮触发的方法 默认空方法
 * @param negativeButtonText 取消按钮文字 默认空 不为空时显示该按钮
 * @param negativeAction 点击取消按钮触发的方法 默认空方法
 */
fun Fragment.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "",
    negativeAction: () -> Unit = {}
) {
    activity?.let {
        MaterialDialog(it).cancelable(false).lifecycleOwner(viewLifecycleOwner).show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) {
                positiveAction.invoke()
            }
            if (negativeButtonText.isNotEmpty()) {
                negativeButton(text = negativeButtonText) {
                    negativeAction.invoke()
                }
            }
            getActionButton(WhichButton.POSITIVE).updateTextColor(SettingUtil.getColor(it))
            getActionButton(WhichButton.NEGATIVE).updateTextColor(SettingUtil.getColor(it))
        }
    }
}

/**
 * 获取进程号对应的进程名
 *
 * @param pid 进程号
 * @return 进程名
 */
fun getProcessName(pid: Int): String? {
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
        var processName = reader.readLine()
        if (!TextUtils.isEmpty(processName)) {
            processName = processName.trim { it <= ' ' }
        }
        return processName
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
    } finally {
        try {
            reader?.close()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }
    return null
}
