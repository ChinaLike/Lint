package com.lint.core

import com.android.SdkConstants.ANDROIDX_APPCOMPAT_PKG
import com.android.SdkConstants.ANDROID_MATERIAL_PKG

/**
 * 常量
 * @author like
 * @date 2022/11/24 14:35
 */
object Constants {

    /**android View*/
    const val ANDROID_VIEW = "android.view.View"

    /**android ContextCompat*/
    const val ANDROID_CONTEXT_COMPAT = "androidx.core.content.ContextCompat"

    /**android ActivityCompat*/
    const val ANDROID_ACTIVITY_COMPAT = "androidx.core.app.ActivityCompat"

    /**android Serializable*/
    const val ANDROID_SERIALIZABLE = "java.io.Serializable"

    /**android Thread*/
    const val ANDROID_THREAD = "java.lang.Thread"

    /**android Toast*/
    const val ANDROID_TOAST = "android.widget.Toast"

    /**安卓组件包名*/
    const val ANDROID_WIDGET_PACKAGE = "${ANDROIDX_APPCOMPAT_PKG}widget"

    /**android TextView*/
    const val ANDROID_TEXT_VIEW = "$ANDROID_WIDGET_PACKAGE.AppCompatTextView"

    /**android EditText*/
    const val ANDROID_EDIT_TEXT = "$ANDROID_WIDGET_PACKAGE.AppCompatEditText"

    /**android Button*/
    const val ANDROID_BUTTON = "$ANDROID_WIDGET_PACKAGE.AppCompatButton"

    /**android ImageView*/
    const val ANDROID_IMAGE_VIEW = "$ANDROID_WIDGET_PACKAGE.AppCompatImageView"

    /**android CheckBox*/
    const val ANDROID_CHECK_BOX = "$ANDROID_WIDGET_PACKAGE.AppCompatCheckBox"

    /**android ShapeableImageView*/
    const val ANDROID_SHAPEABLE_IMAGE_VIEW = "${ANDROID_MATERIAL_PKG}imageview.ShapeableImageView"

    /**android Log*/
    const val ANDROID_LOG = "android.util.Log"

    /**android Resources*/
    const val ANDROID_RESOURCES = "android.content.res.Resources"

    /**android TextUtils*/
    const val ANDROID_TEXTUTILS = "android.text.TextUtils"

    /**android Activity*/
    const val ANDROID_ACTIVITY = "android.app.Activity"

    /**android Fragment*/
    const val ANDROID_FRAGMENT = "androidx.fragment.app.Fragment"

    /**android Context*/
    const val ANDROID_CONTEXT = "android.content.Context"

    /**android Adapter*/
    const val ANDROID_ADAPTER = "androidx.recyclerview.widget.RecyclerView.Adapter"

    /**android Dialog*/
    const val ANDROID_DIALOG = "android.app.Dialog"

    /**android PopupWindow*/
    const val ANDROID_POPUP_WINDOW = "android.widget.PopupWindow"

    /**android AlertDialog*/
    const val ANDROID_ALERT_DIALOG = "android.app.AlertDialog"

    /**android AlertDialog.Builder*/
    const val ANDROID_ALERT_DIALOG_BUILDER = "android.app.AlertDialog.Builder"

    /**android Color*/
    const val ANDROID_COLOR = "android.graphics.Color"

    /**android FragmentStateAdapter*/
    const val ANDROID_FRAGMENT_STATE_ADAPTER = "androidx.viewpager2.adapter.FragmentStateAdapter"


    /**项目组件包名*/
    const val PROJECT_WIDGET_PACKAGE = "com.tsy.widget"

    /**项目 TextView*/
    const val PROJECT_TEXT_VIEW = "$PROJECT_WIDGET_PACKAGE.XTextView"

    /**项目 EditText*/
    const val PROJECT_EDIT_TEXT = "$PROJECT_WIDGET_PACKAGE.edit.XEditText"

    /**项目 Button*/
    const val PROJECT_BUTTON = "$PROJECT_WIDGET_PACKAGE.button.XButton"

    /**项目 GradientButton*/
    const val PROJECT_GRADIENT_BUTTON = "$PROJECT_WIDGET_PACKAGE.button.GradientButton"

    /**项目 StrokeButton*/
    const val PROJECT_STROKE_BUTTON = "$PROJECT_WIDGET_PACKAGE.button.StrokeButton"

    /**项目 ImageView*/
    const val PROJECT_IMAGE_VIEW = "$PROJECT_WIDGET_PACKAGE.XImageView"

    /**项目 ShapeableImageView*/
    const val PROJECT_SHAPEABLE_IMAGE_VIEW = "$PROJECT_WIDGET_PACKAGE.XShapeableImageView"

    /**项目 CardView*/
    const val PROJECT_CARD_VIEW = "$PROJECT_WIDGET_PACKAGE.XCardView"

    /**项目 CheckBox*/
    const val PROJECT_CHECK_BOX = "$PROJECT_WIDGET_PACKAGE.XCheckBox"

    /**项目 NestedScrollView*/
    const val PROJECT_NESTED_SCROLL_VIEW = "$PROJECT_WIDGET_PACKAGE.XNestedScrollView"

    /**项目 TabLayout*/
    const val PROJECT_TAB_LAYOUT = "$PROJECT_WIDGET_PACKAGE.XTabLayout"

    /**项目 RecyclerView*/
    const val PROJECT_RECYCLER_VIEW = "$PROJECT_WIDGET_PACKAGE.recyclerview.XRecyclerView"

    /**项目 Activity*/
    const val PROJECT_ACTIVITY = "com.tsy.base.activity.BaseActivity"

    /**项目 Fragment*/
    const val PROJECT_FRAGMENT = "com.tsy.base.fragment.BaseFragment"

    /**项目 单布局Adapter*/
    const val PROJECT_SINGLE_ADAPTER = "com.tsy.base.adapter.BaseViewBindingAdapter"

    /**项目 多布局Adapter*/
    const val PROJECT_MULTI_ADAPTER = "com.chad.library.adapter.base.BaseProviderMultiAdapter"

    /**项目 Dialog*/
    const val PROJECT_DIALOG = "com.lxj.xpopup.core.BasePopupView"

    /**项目 ColorUtil*/
    const val PROJECT_COLOR_UTIL = "com.core.util.ColorUtil"

    /**项目 ResourceUtil*/
    const val PROJECT_RESOURCE_UTIL = "com.core.util.ResourceUtil"

    /**项目 util*/
    const val PROJECT_UTIL = "com.core.util"

    /**项目 GroupedRecyclerViewAdapter*/
    const val PROJECT_GROUPED_RECYCLER_VIEW_ADAPTER = "com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter"

    /**项目 BaseBannerAdapter*/
    const val PROJECT_BANNER_ADAPTER = "com.zhpan.bannerview.BaseBannerAdapter"

    /**项目 Click*/
    const val PROJECT_CLICK = "com.core.ex.onDebouncedClick"

}