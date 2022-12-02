package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_TEXTUTILS
import org.jetbrains.uast.UCallExpression

/**
 * 字符串使用规范
 * @author like
 * @date 2022/12/2 11:11
 */
class StringDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val MESSAGE_EMPTY = "建议使用Strings的扩展方法isEmpty()或isNullOrEmpty()"

        @JvmField
        val ISSUE_EMPTY = Issue.create(
            "StringStandard",
            MESSAGE_EMPTY,
            MESSAGE_EMPTY,
            Category.MESSAGES, 7, Severity.FATAL,
            Implementation(StringDetector::class.java, Scope.JAVA_FILE_SCOPE),
        )

    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("isEmpty")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isInResources = context.evaluator.isMemberInClass(method, ANDROID_TEXTUTILS)
        val methodName = node.methodName

        if ("isEmpty" == methodName && isInResources) {
            val params: String = node.valueArguments[0].asSourceString()
            context.report(
                ISSUE_EMPTY,
                node,
                context.getLocation(node),
                MESSAGE_EMPTY,
                fix().group(
                    lintFix(context, "isEmpty", params),
                    lintFix(context, "isNullOrEmpty", params)
                )
            )
        }
    }

    /**
     * 修复方案
     */
    private fun lintFix(context: JavaContext, methodName: String, params: String): LintFix {
        return fix()
            .name("$params.${methodName}() replace")
            .replace()
            .with("${params}.${methodName}()")
            .build()
    }
}