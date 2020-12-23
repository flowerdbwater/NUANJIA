package com.example.nuanjia.view.home

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.example.nuanjia.R


/**
 * 自定义左侧搜索图标 & 一键删除图标的输入框
 */
open class EditText_Clear : AppCompatEditText {
    /**
     * 步骤1：定义左侧搜索图标 & 一键删除图标
     */
    private var clearDrawable: Drawable? = null
    private var searchDrawable: Drawable? = null

    constructor(context: Context?) : super(context!!) {
        init()
        // 初始化该组件时，对EditText_Clear进行初始化 ->>步骤2
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    /**
     * 步骤2：初始化 图标资源
     */
    private fun init() {
        clearDrawable = resources.getDrawable(R.drawable.ic_clear)
        searchDrawable = resources.getDrawable(R.drawable.ic_main_search_green)
        setCompoundDrawablesWithIntrinsicBounds(
            searchDrawable, null,
            null, null
        )
        // setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)介绍
        // 作用：在EditText上、下、左、右设置图标（相当于android:drawableLeft=""  android:drawableRight=""）
        // 注1：setCompoundDrawablesWithIntrinsicBounds（）传入的Drawable的宽高=固有宽高（自动通过getIntrinsicWidth（）& getIntrinsicHeight（）获取）
        // 注2：若不想在某个地方显示，则设置为null
        // 此处设置了左侧搜索图标

        // 另外一个相似的方法：setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)介绍
        // 与setCompoundDrawablesWithIntrinsicBounds（）的区别：可设置图标大小
        // 传入的Drawable对象必须已经setBounds(x,y,width,height)，即必须设置过初始位置、宽和高等信息
        // x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点 width:组件的长度 height:组件的高度
    }

    /**
     * 步骤3：通过监听复写EditText本身的方法来确定是否显示删除图标
     * 监听方法：onTextChanged（） & onFocusChanged（）
     * 调用时刻：当输入框内容变化时 & 焦点发生变化时
     */
    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setClearIconVisible(this.hasFocus() && text.isNotEmpty())
        // hasFocus()返回是否获得EditTEXT的焦点，即是否选中
        // setClearIconVisible（） = 根据传入的是否选中 & 是否有输入来判断是否显示删除图标->>关注1
    }

     override fun onFocusChanged(
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setClearIconVisible(focused && length() > 0)
        // focused = 是否获得焦点
        // 同样根据setClearIconVisible（）判断是否要显示删除图标
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标
     */
    private fun setClearIconVisible(visible: Boolean) {
        setCompoundDrawablesWithIntrinsicBounds(
            searchDrawable, null,
            if (visible) clearDrawable else null, null
        )
    }

    /**
     * 步骤4：对删除图标区域设置点击事件，即"点击 = 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val drawable = clearDrawable
                if (drawable != null && event.x <= getWidth() - getPaddingRight() && event.x >= getWidth() - getPaddingRight() - drawable.bounds
                        .width()
                ) {
                    setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }
}