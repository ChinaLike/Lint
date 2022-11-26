package com.lint.core

import com.android.SdkConstants.ANDROIDX_APPCOMPAT_PKG
import com.android.SdkConstants.ANDROID_MATERIAL_PKG

/**
 * 常量
 * @author like
 * @date 2022/11/24 14:35
 */
object Constants {

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
    const val PROJECT_RECYCLER_VIEW = "$PROJECT_WIDGET_PACKAGE.XRecyclerView"

}