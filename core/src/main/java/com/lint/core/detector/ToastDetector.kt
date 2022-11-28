package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_TOAST
import org.jetbrains.uast.UCallExpression

/**
 * 提示规范检测
 * @author like
 * @date 2022/11/21 10:32
 */
class ToastDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val MESSAGE = "请统一使用ToastUtil作为轻提示"

        @JvmField
        val ISSUE = Issue.create(
            "ToastStandard",
            "Toast使用不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(ToastDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("makeText")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass = context.evaluator.isMemberInClass(method, ANDROID_TOAST)
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, ANDROID_TOAST, true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(ISSUE, node, context.getLocation(node), MESSAGE)
        }
    }

}