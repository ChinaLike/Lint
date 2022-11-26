package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * 点击事件规范
 * @author like
 * @date 2022/11/26 15:02
 */
class OnClickListenerDetector : Detector(), Detector.UastScanner {

    companion object {

        const val MESSAGE = "请使用onDebouncedClick替换setOnClickListener，可以有效防重点击"

        @JvmField
        val ISSUE = Issue.create(
            "ClickStandard",
            "setOnClickListener不能防重点击，建议替换",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(OnClickListenerDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("setOnClickListener")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass = context.evaluator.isMemberInClass(method, "android.view.View")
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, "android.view.View", true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(
                ISSUE,
                node,
                context.getNameLocation(node),
                MESSAGE,
                fix().name("replace onDebouncedClick").replace().with("onDebouncedClick").build()
            )
        }
    }

}