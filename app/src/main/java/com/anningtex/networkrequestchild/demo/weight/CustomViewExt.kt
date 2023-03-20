package com.anningtex.networkrequestchild.demo.weight

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.anningtex.networkrequestchild.R
import com.anningtex.networkrequestchild.demo.utils.CacheUtil
import com.anningtex.networkrequestchild.demo.utils.SettingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhpan.bannerview.BannerViewPager

/**
 * @author Song
 * desc:项目中自定义类的拓展函数
 */
fun BannerViewPager<*, *>.setPageListener(onPageSelected: (Int) -> Unit) {
    this.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int, positionOffset: Float, positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    })
}

fun LoadService<*>.setErrorText(message: String) {
    this.setCallBack(ErrorCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.error_text).text = message
    }
}

fun LoadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadSir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadSir.showCallback(LoadingCallback::class.java)
    SettingUtil.setLoadingColor(SettingUtil.getColor(view.context.applicationContext), loadSir)
    return loadSir
}

//绑定普通的Recyclerview
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.initFooter(loadMoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
    val footerView = DefineLoadMoreView(context.applicationContext)
    //给尾部设置颜色
    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(context.applicationContext))
    //设置尾部点击回调
    footerView.setmLoadMoreListener {
        footerView.onLoading()
        loadMoreListener.onLoadMore()
    }
    this.run {
        //添加加载更多尾部
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //设置加载更多回调
        setLoadMoreListener(loadMoreListener)
    }
    return footerView
}

fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
    floatBtn.backgroundTintList = SettingUtil.getOneColorStateList(context.applicationContext)
    floatBtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//没有动画迅速返回到顶部(马上)
        } else {
            smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
        }
    }
}

//初始化 SwipeRefreshLayout
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //设置主题颜色
        setColorSchemeColors(SettingUtil.getColor(context.applicationContext))
    }
}

/**
 * 初始化普通的toolbar 只设置标题
 */
fun Toolbar.init(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(context.applicationContext))
    title = titleStr
    return this
}

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "", backImg: Int = R.mipmap.ic_back, onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    setBackgroundColor(SettingUtil.getColor(context.applicationContext))
    title = Html.fromHtml(titleStr)
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

/**
 * 根据控件的类型设置主题，注意，控件具有优先级， 基本类型的控件建议放到最后，像 Textview，FragmentLayout，不然会出现问题，
 * 列如下面的BottomNavigationViewEx他的顶级父控件为FragmentLayout，如果先 is Fragmentlayout判断在 is BottomNavigationViewEx上面
 * 那么就会直接去执行 is FragmentLayout的代码块 跳过 is BottomNavigationViewEx的代码块了
 */
fun setUiTheme(color: Int, vararg anylist: Any) {
    anylist.forEach {
        when (it) {
            is LoadService<*> -> SettingUtil.setLoadingColor(color, it as LoadService<Any>)
            is FloatingActionButton -> it.backgroundTintList =
                SettingUtil.getOneColorStateList(color)
            is SwipeRefreshLayout -> it.setColorSchemeColors(color)
            is DefineLoadMoreView -> it.setLoadViewColor(SettingUtil.getOneColorStateList(color))
            is BottomNavigationViewEx -> {
                it.itemIconTintList = SettingUtil.getColorStateList(color)
                it.itemTextColor = SettingUtil.getColorStateList(color)
            }
            is Toolbar -> it.setBackgroundColor(color)
            is TextView -> it.setTextColor(color)
            is LinearLayout -> it.setBackgroundColor(color)
            is ConstraintLayout -> it.setBackgroundColor(color)
            is FrameLayout -> it.setBackgroundColor(color)
        }
    }
}

//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimion(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun ViewPager2.init(
    fragment: Fragment, fragments: ArrayList<Fragment>, isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击 跳转前做登录校验
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastLoginClickTime = 0L
fun View.clickNoRepeatLogin(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastLoginClickTime != 0L && (currentTime - lastLoginClickTime < interval)) {
            return@setOnClickListener
        }
        lastLoginClickTime = currentTime
        if (CacheUtil.isLogin()) {
            action(it)
        } else {
            //跳转到登录界面

        }
    }
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击 跳转前做登录校验
 * @param view 触发的view集合
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
fun clickNoRepeatLogin(vararg view: View?, interval: Long = 500, action: (view: View) -> Unit) {
    view.forEach { it ->
        it?.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (lastLoginClickTime != 0L && (currentTime - lastLoginClickTime < interval)) {
                return@setOnClickListener
            }
            lastLoginClickTime = currentTime
            if (CacheUtil.isLogin()) {
                action(it)
            } else {
                //跳转到登录界面
            }
        }
    }
}
