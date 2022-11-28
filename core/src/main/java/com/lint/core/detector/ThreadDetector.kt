package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_THREAD
import org.jetbrains.uast.UCallExpression

/**
 * 线程创建规范
 * @author like
 * @date 2022/11/23 13:57
 */
class ThreadDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val MESSAGE = "请勿直接调用new Thread()，建议使用AsyncTask或统一的线程管理工具类"

        @JvmField
        val ISSUE = Issue.create(
            "ThreadStandard",
            "Thread使用不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(ThreadDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableConstructorTypes(): List<String>? {
        return listOf(ANDROID_THREAD)
    }

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod
    ) {
        context.report(ISSUE, node, context.getLocation(node), MESSAGE)
    }
}