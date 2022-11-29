package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_RESOURCES
import com.lint.core.Constants.PROJECT_RESOURCE_UTIL
import org.jetbrains.uast.UCallExpression

/**
 * 资源文件引用规范
 * @author like
 * @date 2022/11/28 11:06
 */
class AndroidDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val MESSAGE_RESOURCES_GET_DRAWABLE =
            "应该使用ContextCompat或带有Theme Overload 的方法，而不是getDrawable()。"
        private const val MESSAGE_RESOURCES_GET_COLOR =
            "应该使用ContextCompat或带有Theme Overload 的方法，而不是getColor()。"
        private const val MESSAGE_RESOURCES_GET_COLOR_STATE_LIST =
            "应该使用ContextCompat或带有Theme Overload 的方法，而不是getColorStateList()。"

        @JvmField
        val ISSUE_RESOURCES_GET_DRAWABLE = Issue.create(
            "ResourcesGetDrawableStandard",
            "Resources 已经弃用getDrawable()",
            MESSAGE_RESOURCES_GET_DRAWABLE,
            Category.MESSAGES, 7, Severity.FATAL,
            Implementation(AndroidDetector::class.java, Scope.JAVA_FILE_SCOPE),
        )

        @JvmField
        val ISSUE_RESOURCES_GET_COLOR = Issue.create(
            "ResourcesGetColorStandard",
            "Resources 已经弃用getColor()",
            MESSAGE_RESOURCES_GET_COLOR,
            Category.MESSAGES, 7, Severity.FATAL,
            Implementation(AndroidDetector::class.java, Scope.JAVA_FILE_SCOPE),
        )

        @JvmField
        val ISSUE_RESOURCES_GET_COLOR_STATE_LIST = Issue.create(
            "ResourcesGetColorStateListStandard",
            "Resources 已经弃用getColorStateList()",
            MESSAGE_RESOURCES_GET_COLOR_STATE_LIST,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(AndroidDetector::class.java, Scope.JAVA_FILE_SCOPE),
        )

    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("getColor", "getDrawable", "getColorStateList")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isInResources = context.evaluator.isMemberInClass(method, ANDROID_RESOURCES)
        val methodName = node.methodName

        if ("getDrawable" == methodName && isInResources) {
            context.report(
                ISSUE_RESOURCES_GET_DRAWABLE,
                node,
                context.getLocation(node),
                MESSAGE_RESOURCES_GET_DRAWABLE,
                lintFix(context, "getDrawable", node.valueArguments[0].asSourceString())
            )
        }

        if ("getColor" == methodName && isInResources) {
            context.report(
                ISSUE_RESOURCES_GET_COLOR,
                node,
                context.getNameLocation(node),
                MESSAGE_RESOURCES_GET_COLOR,
                lintFix(context, "getColor", node.valueArguments[0].asSourceString())
            )
        }

        if ("getColorStateList" == methodName && isInResources) {
            context.report(
                ISSUE_RESOURCES_GET_COLOR_STATE_LIST,
                node,
                context.getNameLocation(node),
                MESSAGE_RESOURCES_GET_COLOR_STATE_LIST,
                lintFix(context, "getColorStateList", node.valueArguments[0].asSourceString())
            )
        }
    }

    private fun lintFix(context: JavaContext, methodName: String, params: String): LintFix {
        val fix1 = fix()
            .name("ContextCompat替代")
            .replace()
            .with("ContextCompat.${methodName}(context, ${params})")
            .build()
        val importClass = context.importPackage(PROJECT_RESOURCE_UTIL)
        val builder = fix()
            .replace()
            .with("ResourceUtil.${methodName}(${params})")
            .build()
        val fix2 = fix()
            .name("ResourceUtil替代")
            .composite()
            .add(builder).apply {
                if (importClass != null){
                    add(importClass)
                }
            }
            .build()

        return fix().group(fix1, fix2)
    }

}