package com.lint.core.detector

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import com.lint.core.Constants
import org.w3c.dom.Element

/**
 * 布局引用控件规范
 * @author like
 * @date 2022/11/23 22:54
 */
class LayoutDetector : ResourceXmlDetector() {

    companion object {

        /**实现*/
        private val implementation =
            Implementation(LayoutDetector::class.java, Scope.RESOURCE_FILE_SCOPE)

        /**问题严重程度*/
        private val severity = Severity.FATAL

        /**权重*/
        private const val priority = 7

        /**在IDE中的分类*/
        private val category = Category.MESSAGES

        /**文本替换类*/
        private const val replaceTextView = "${Constants.projectPackageName}.XTextView"

        /**文本提示消息*/
        private const val TEXT_VIEW_MESSAGE = "建议使用项目封装的【${replaceTextView}】作为TextView"

        /**文本(新)*/
        private const val APP_COMPAT_TEXT_VIEW_NAME =
            "${Constants.androidPackageName}.AppCompatTextView"

        /**文本(旧)*/
        private const val TEXT_VIEW_NAME = "TextView"

        /**输入文本替换类*/
        private const val replaceEditText = "${Constants.projectPackageName}.edit.XEditText"

        /**输入文本提示消息*/
        private const val EDIT_TEXT_MESSAGE = "建议使用项目封装的【${replaceEditText}】作为EditText"

        /**输入文本(新)*/
        private const val APP_COMPAT_EDIT_TEXT_NAME =
            "${Constants.androidPackageName}.AppCompatEditText"

        /**输入文本(旧)*/
        private const val EDIT_TEXT_NAME = "EditText"

        /**Button替换类*/
        private const val replaceButton = "${Constants.projectPackageName}.button.XButton"

        /**Button替换类(渐变)*/
        private const val replaceGradientButton =
            "${Constants.projectPackageName}.button.GradientButton"

        /**Button替换类(描边)*/
        private const val replaceStrokeButton =
            "${Constants.projectPackageName}.button.StrokeButton"

        /**Button提示消息*/
        private const val BUTTON_MESSAGE = "建议使用项目封装的【${replaceButton}】或扩展类作为Button"

        /**Button(新)*/
        private const val APP_COMPAT_BUTTON_NAME =
            "${Constants.androidPackageName}.AppCompatButton"

        /**Button(旧)*/
        private const val BUTTON_NAME = "Button"

        /**ImageView替换类*/
        private const val replaceImageView = "${Constants.projectPackageName}.XImageView"

        /**ImageView圆角替换类*/
        private const val replaceCornerImageView =
            "${Constants.projectPackageName}.XShapeableImageView"

        /**ImageView提示消息*/
        private const val IMAGE_VIEW_MESSAGE = "建议使用项目封装的【${replaceImageView}】作为ImageView"

        /**ImageView(新)*/
        private const val APP_COMPAT_IMAGE_VIEW_NAME =
            "${Constants.androidPackageName}.AppCompatImageView"

        /**ImageView(旧)*/
        private const val IMAGE_VIEW_NAME = "ImageView"

        /**Card替换类*/
        private const val replaceCardView = "${Constants.projectPackageName}.XCardView"

        /**Card提示消息*/
        private const val CARD_VIEW_MESSAGE = "建议使用项目封装的【${replaceCardView}】作为CardView"

        /**Card*/
        private const val APP_COMPAT_CARD_VIEW_NAME =
            "${Constants.androidCardPackageName}.CardView"

        /**CheckBox替换类*/
        private const val replaceCheckBox =
            "${Constants.projectPackageName}.XCheckBox"

        /**CheckBox提示消息*/
        private const val CHECK_BOX_MESSAGE = "建议使用项目封装的【${replaceCheckBox}】作为CheckBox"

        /**CheckBox(新)*/
        private const val APP_COMPAT_CHECK_BOX_NAME =
            "${Constants.androidPackageName}.AppCompatCheckBox"

        /**CheckBox(旧)*/
        private const val CHECK_BOX_NAME = "CheckBox"

        /**ScrollView替换类*/
        private const val replaceScrollView =
            "${Constants.projectPackageName}.XNestedScrollView"

        /**ScrollView提示消息*/
        private const val SCROLL_VIEW_MESSAGE = "建议使用项目封装的【${replaceScrollView}】作为ScrollView"

        /**ScrollView(新)*/
        private const val APP_COMPAT_SCROLL_VIEW_NAME =
            "${Constants.androidCorePackageName}.NestedScrollView"

        /**ScrollView(旧)*/
        private const val SCROLL_VIEW_NAME = "ScrollView"

        /**TabLayout替换类*/
        private const val replaceTabLayout =
            "${Constants.projectPackageName}.XTabLayout"

        /**TabLayout提示消息*/
        private const val TAB_LAYOUT_MESSAGE = "建议使用项目封装的【${replaceTabLayout}】作为TabLayout"

        /**TabLayout*/
        private const val APP_COMPAT_TAB_LAYOUT_NAME = "com.google.android.material.tabs.TabLayout"

        /**RecyclerView替换类*/
        private const val replaceRecyclerView =
            "${Constants.projectPackageName}.recyclerview.XRecyclerView"

        /**RecyclerView提示消息*/
        private const val RECYCLER_VIEW_MESSAGE = "建议使用项目封装的【${replaceRecyclerView}】作为RecyclerView"

        /**RecyclerView(新)*/
        private const val RECYCLER_VIEW_NAME = "com.google.android.material.tabs.TabLayout"

        /**RecyclerView(旧)*/
        private const val LIST_VIEW_NAME = "ListView"

        /**文本Issue*/
        @JvmField
        val TEXT_VIEW_ISSUE = Issue.create(
            "TextViewStandard",
            TEXT_VIEW_MESSAGE,
            TEXT_VIEW_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**输入文本Issue*/
        @JvmField
        val EDIT_TEXT_ISSUE = Issue.create(
            "EditTextStandard",
            EDIT_TEXT_MESSAGE,
            EDIT_TEXT_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**Button Issue*/
        @JvmField
        val BUTTON_ISSUE = Issue.create(
            "ButtonStandard",
            BUTTON_MESSAGE,
            BUTTON_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**Image Issue*/
        @JvmField
        val IMAGE_ISSUE = Issue.create(
            "ImageStandard",
            IMAGE_VIEW_MESSAGE,
            IMAGE_VIEW_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**Card Issue*/
        @JvmField
        val CARD_ISSUE = Issue.create(
            "CardStandard",
            CARD_VIEW_MESSAGE,
            CARD_VIEW_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**CheckBox Issue*/
        @JvmField
        val CHECK_BOX_ISSUE = Issue.create(
            "CheckBoxStandard",
            CHECK_BOX_MESSAGE,
            CHECK_BOX_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**ScrollView Issue*/
        @JvmField
        val SCROLL_VIEW_ISSUE = Issue.create(
            "ScrollViewStandard",
            SCROLL_VIEW_MESSAGE,
            SCROLL_VIEW_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**TabLayout Issue*/
        @JvmField
        val TAB_LAYOUT_ISSUE = Issue.create(
            "TabLayoutStandard",
            TAB_LAYOUT_MESSAGE,
            TAB_LAYOUT_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )

        /**RecyclerView Issue*/
        @JvmField
        val RECYCLER_VIEW_ISSUE = Issue.create(
            "RecyclerViewStandard",
            RECYCLER_VIEW_MESSAGE,
            RECYCLER_VIEW_MESSAGE,
            category,
            priority,
            severity,
            implementation
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableElements(): Collection<String>? {
        return listOf(
            APP_COMPAT_TEXT_VIEW_NAME,
            TEXT_VIEW_NAME,
            APP_COMPAT_EDIT_TEXT_NAME,
            EDIT_TEXT_NAME,
            APP_COMPAT_BUTTON_NAME,
            BUTTON_NAME,
            APP_COMPAT_IMAGE_VIEW_NAME,
            IMAGE_VIEW_NAME,
            APP_COMPAT_CARD_VIEW_NAME,
            APP_COMPAT_CHECK_BOX_NAME,
            CHECK_BOX_NAME,
            APP_COMPAT_SCROLL_VIEW_NAME,
            SCROLL_VIEW_NAME,
            APP_COMPAT_TAB_LAYOUT_NAME,
            RECYCLER_VIEW_NAME,
            LIST_VIEW_NAME
        )
    }

    override fun visitElement(context: XmlContext, element: Element) {
        when (val name = element.tagName) {
            APP_COMPAT_TEXT_VIEW_NAME, TEXT_VIEW_NAME -> {
                //文本视图
                context.report(
                    TEXT_VIEW_ISSUE,
                    element,
                    context.getNameLocation(element),
                    TEXT_VIEW_MESSAGE,
                    lintFix(replaceTextView, name)
                )
            }
            APP_COMPAT_EDIT_TEXT_NAME, EDIT_TEXT_NAME -> {
                //文本输入
                context.report(
                    EDIT_TEXT_ISSUE,
                    element,
                    context.getNameLocation(element),
                    EDIT_TEXT_MESSAGE,
                    lintFix(replaceEditText, name)
                )
            }
            APP_COMPAT_BUTTON_NAME, BUTTON_NAME -> {
                //按钮
                context.report(
                    BUTTON_ISSUE,
                    element,
                    context.getNameLocation(element),
                    BUTTON_MESSAGE,
                    buttonLintFix(replaceButton, name)
                )
            }
            APP_COMPAT_IMAGE_VIEW_NAME, IMAGE_VIEW_NAME -> {
                //ImageView
                context.report(
                    BUTTON_ISSUE,
                    element,
                    context.getNameLocation(element),
                    BUTTON_MESSAGE,
                    imageLintFix(replaceButton, name)
                )
            }
            APP_COMPAT_CARD_VIEW_NAME -> {
                //Card
                context.report(
                    CARD_ISSUE,
                    element,
                    context.getNameLocation(element),
                    CARD_VIEW_MESSAGE,
                    lintFix(replaceCardView, name)
                )
            }
            APP_COMPAT_CHECK_BOX_NAME, CHECK_BOX_NAME->{
                //CheckBox
                context.report(
                    CHECK_BOX_ISSUE,
                    element,
                    context.getNameLocation(element),
                    CHECK_BOX_MESSAGE,
                    lintFix(replaceCheckBox, name)
                )
            }
            APP_COMPAT_SCROLL_VIEW_NAME, SCROLL_VIEW_NAME->{
                //ScrollView
                context.report(
                    SCROLL_VIEW_ISSUE,
                    element,
                    context.getNameLocation(element),
                    SCROLL_VIEW_MESSAGE,
                    lintFix(replaceScrollView, name)
                )
            }
            APP_COMPAT_TAB_LAYOUT_NAME->{
                //TabLayout
                context.report(
                    TAB_LAYOUT_ISSUE,
                    element,
                    context.getNameLocation(element),
                    TAB_LAYOUT_MESSAGE,
                    lintFix(replaceTabLayout, name)
                )
            }
            RECYCLER_VIEW_NAME, LIST_VIEW_NAME->{
                //RecyclerView
                context.report(
                    RECYCLER_VIEW_ISSUE,
                    element,
                    context.getNameLocation(element),
                    RECYCLER_VIEW_MESSAGE,
                    lintFix(replaceRecyclerView, name)
                )
            }
        }
    }

    /**
     * 通用替换方案
     * @param [newPlan] 新方案
     * @param [oldPlan] 老方案
     */
    private fun lintFix(newPlan: String, oldPlan: String): LintFix {
        return fix().name("推荐使用「${newPlan}」替换「${oldPlan}」").replace().with(newPlan).build()
    }

    /**
     * Button替换方案
     * @param [newPlan] 新方案
     * @param [oldPlan] 老方案
     */
    private fun buttonLintFix(newPlan: String, oldPlan: String): LintFix {
        val plan1 = lintFix(newPlan, oldPlan)
        val plan2 = fix().name("使用渐变按钮「${replaceGradientButton}」替换「${oldPlan}」").replace()
            .with(replaceGradientButton).build()
        val plan3 = fix().name("使用描边按钮「${replaceStrokeButton}」替换「${oldPlan}」").replace()
            .with(replaceStrokeButton).build()
        return fix().group(plan1, plan2, plan3)
    }

    /**
     * Image替换方案
     * @param [newPlan] 新方案
     * @param [oldPlan] 老方案
     */
    private fun imageLintFix(newPlan: String, oldPlan: String): LintFix {
        val plan1 = lintFix(newPlan, oldPlan)
        val plan2 = fix().name("使用圆角「${replaceCornerImageView}」替换「${oldPlan}」").replace()
            .with(replaceCornerImageView).build()
        return fix().group(plan1, plan2)
    }

}