package com.lint.core.detector

import com.android.SdkConstants.*
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import com.lint.core.Constants.ANDROID_BUTTON
import com.lint.core.Constants.ANDROID_CHECK_BOX
import com.lint.core.Constants.ANDROID_EDIT_TEXT
import com.lint.core.Constants.ANDROID_IMAGE_VIEW
import com.lint.core.Constants.ANDROID_SHAPEABLE_IMAGE_VIEW
import com.lint.core.Constants.ANDROID_TEXT_VIEW
import com.lint.core.Constants.PROJECT_BUTTON
import com.lint.core.Constants.PROJECT_CARD_VIEW
import com.lint.core.Constants.PROJECT_CHECK_BOX
import com.lint.core.Constants.PROJECT_EDIT_TEXT
import com.lint.core.Constants.PROJECT_GRADIENT_BUTTON
import com.lint.core.Constants.PROJECT_IMAGE_VIEW
import com.lint.core.Constants.PROJECT_NESTED_SCROLL_VIEW
import com.lint.core.Constants.PROJECT_RECYCLER_VIEW
import com.lint.core.Constants.PROJECT_SHAPEABLE_IMAGE_VIEW
import com.lint.core.Constants.PROJECT_STROKE_BUTTON
import com.lint.core.Constants.PROJECT_TAB_LAYOUT
import com.lint.core.Constants.PROJECT_TEXT_VIEW
import org.w3c.dom.Element

/**
 * 布局引用控件规范
 * @author like
 * @date 2022/11/23 22:54
 */
class CustomLayoutDetector : ResourceXmlDetector() {

    companion object {

        /**实现*/
        private val implementation =
            Implementation(CustomLayoutDetector::class.java, Scope.RESOURCE_FILE_SCOPE)

        /**问题严重程度*/
        private val severity = Severity.FATAL

        /**权重*/
        private const val priority = 7

        /**在IDE中的分类*/
        private val category = Category.MESSAGES

        /**文本提示消息*/
        private val TEXT_VIEW_MESSAGE = message(PROJECT_TEXT_VIEW, "TextView")

        /**输入文本提示消息*/
        private val EDIT_TEXT_MESSAGE = message(PROJECT_EDIT_TEXT, "EditText")

        /**Button提示消息*/
        private val BUTTON_MESSAGE = message(PROJECT_BUTTON, "Button")

        /**ImageView提示消息*/
        private val IMAGE_VIEW_MESSAGE = message(PROJECT_IMAGE_VIEW, "ImageView")

        /**ShapeableImageView提示消息*/
        private val SHAPEABLE_IMAGE_VIEW_MESSAGE =
            message(PROJECT_SHAPEABLE_IMAGE_VIEW, "ShapeableImageView")

        /**Card提示消息*/
        private val CARD_VIEW_MESSAGE = message(PROJECT_CARD_VIEW, "CardView")

        /**CheckBox提示消息*/
        private val CHECK_BOX_MESSAGE = message(PROJECT_CHECK_BOX, "CheckBox")

        /**ScrollView提示消息*/
        private val SCROLL_VIEW_MESSAGE = message(PROJECT_NESTED_SCROLL_VIEW, "ScrollView")

        /**TabLayout提示消息*/
        private val TAB_LAYOUT_MESSAGE = message(PROJECT_TAB_LAYOUT, "TabLayout")

        /**RecyclerView提示消息*/
        private val RECYCLER_VIEW_MESSAGE = message(PROJECT_RECYCLER_VIEW, "RecyclerView")

        /**
         * 提示消息
         */
        private fun message(projectPackageName: String, name: String): String {
            return "建议使用项目封装的【${projectPackageName}】作为${name}"
        }


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

        /**ShapeableImage Issue*/
        @JvmField
        val SHAPEABLE_IMAGE_ISSUE = Issue.create(
            "ShapeableImageStandard",
            SHAPEABLE_IMAGE_VIEW_MESSAGE,
            SHAPEABLE_IMAGE_VIEW_MESSAGE,
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
            ANDROID_TEXT_VIEW, TEXT_VIEW,
            ANDROID_EDIT_TEXT, EDIT_TEXT,
            ANDROID_BUTTON, BUTTON,
            ANDROID_IMAGE_VIEW, IMAGE_VIEW, ANDROID_SHAPEABLE_IMAGE_VIEW,
            CARD_VIEW.oldName(), CARD_VIEW.newName(),
            ANDROID_CHECK_BOX, CHECK_BOX,
            NESTED_SCROLL_VIEW.oldName(), NESTED_SCROLL_VIEW.newName(), SCROLL_VIEW,
            TAB_LAYOUT.newName(), TAB_LAYOUT.oldName(),
            RECYCLER_VIEW.newName(), RECYCLER_VIEW.oldName(), LIST_VIEW
        )
    }

    override fun visitElement(context: XmlContext, element: Element) {
        when (val name = element.tagName) {
            ANDROID_TEXT_VIEW, TEXT_VIEW -> {
                //文本视图
                context.report(
                    TEXT_VIEW_ISSUE,
                    element,
                    context.getNameLocation(element),
                    TEXT_VIEW_MESSAGE,
                    lintFix(PROJECT_TEXT_VIEW, name)
                )
            }
            ANDROID_EDIT_TEXT, EDIT_TEXT -> {
                //文本输入
                context.report(
                    EDIT_TEXT_ISSUE,
                    element,
                    context.getNameLocation(element),
                    EDIT_TEXT_MESSAGE,
                    lintFix(PROJECT_EDIT_TEXT, name)
                )
            }
            ANDROID_BUTTON, BUTTON -> {
                //按钮
                context.report(
                    BUTTON_ISSUE,
                    element,
                    context.getNameLocation(element),
                    BUTTON_MESSAGE,
                    buttonLintFix(PROJECT_BUTTON, name)
                )
            }
            ANDROID_IMAGE_VIEW, IMAGE_VIEW -> {
                //ImageView
                context.report(
                    IMAGE_ISSUE,
                    element,
                    context.getNameLocation(element),
                    IMAGE_VIEW_MESSAGE,
                    imageLintFix(PROJECT_IMAGE_VIEW, name)
                )
            }
            ANDROID_SHAPEABLE_IMAGE_VIEW -> {
                //ShapeableImageView
                context.report(
                    SHAPEABLE_IMAGE_ISSUE,
                    element,
                    context.getNameLocation(element),
                    SHAPEABLE_IMAGE_VIEW_MESSAGE,
                    lintFix(PROJECT_SHAPEABLE_IMAGE_VIEW, name)
                )
            }
            CARD_VIEW.oldName(), CARD_VIEW.newName() -> {
                //Card
                println("测试"+ CARD_VIEW.oldName())
                context.report(
                    CARD_ISSUE,
                    element,
                    context.getNameLocation(element),
                    CARD_VIEW_MESSAGE,
                    lintFix(PROJECT_CARD_VIEW, name)
                )
            }
            ANDROID_CHECK_BOX, CHECK_BOX -> {
                //CheckBox
                context.report(
                    CHECK_BOX_ISSUE,
                    element,
                    context.getNameLocation(element),
                    CHECK_BOX_MESSAGE,
                    lintFix(PROJECT_CHECK_BOX, name)
                )
            }
            NESTED_SCROLL_VIEW.oldName(), NESTED_SCROLL_VIEW.newName(), SCROLL_VIEW -> {
                //ScrollView
                context.report(
                    SCROLL_VIEW_ISSUE,
                    element,
                    context.getNameLocation(element),
                    SCROLL_VIEW_MESSAGE,
                    lintFix(PROJECT_NESTED_SCROLL_VIEW, name)
                )
            }
            TAB_LAYOUT.newName(), TAB_LAYOUT.oldName() -> {
                //TabLayout
                context.report(
                    TAB_LAYOUT_ISSUE,
                    element,
                    context.getNameLocation(element),
                    TAB_LAYOUT_MESSAGE,
                    lintFix(PROJECT_TAB_LAYOUT, name)
                )
            }
            RECYCLER_VIEW.newName(), RECYCLER_VIEW.oldName(), LIST_VIEW -> {
                //RecyclerView
                context.report(
                    RECYCLER_VIEW_ISSUE,
                    element,
                    context.getNameLocation(element),
                    RECYCLER_VIEW_MESSAGE,
                    lintFix(PROJECT_RECYCLER_VIEW, name)
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
        return fix().name("「${newPlan}」replace「${oldPlan}」").replace().with(newPlan).build()
    }

    /**
     * Button替换方案
     * @param [newPlan] 新方案
     * @param [oldPlan] 老方案
     */
    private fun buttonLintFix(newPlan: String, oldPlan: String): LintFix {
        val plan1 = lintFix(newPlan, oldPlan)
        val plan2 = fix().name("渐变按钮「${PROJECT_GRADIENT_BUTTON}」replace「${oldPlan}」").replace()
            .with(PROJECT_GRADIENT_BUTTON).build()
        val plan3 = fix().name("描边按钮「${PROJECT_STROKE_BUTTON}」replace「${oldPlan}」").replace()
            .with(PROJECT_STROKE_BUTTON).build()
        return fix().group(plan1, plan2, plan3)
    }

    /**
     * Image替换方案
     * @param [newPlan] 新方案
     * @param [oldPlan] 老方案
     */
    private fun imageLintFix(newPlan: String, oldPlan: String): LintFix {
        val plan1 = lintFix(newPlan, oldPlan)
        val plan2 = fix().name("圆角「${PROJECT_SHAPEABLE_IMAGE_VIEW}」replace「${oldPlan}」").replace()
            .with(PROJECT_SHAPEABLE_IMAGE_VIEW).build()
        return fix().group(plan1, plan2)
    }

}