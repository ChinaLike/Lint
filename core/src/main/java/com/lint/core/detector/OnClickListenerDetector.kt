package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants
import com.lint.core.Constants.ANDROID_VIEW
import com.lint.core.Constants.PROJECT_CLICK
import org.jetbrains.uast.UCallExpression

/**
 * 点击事件规范
 * @author like
 * @date 2022/11/26 15:02
 */
class OnClickListenerDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val MESSAGE = "请使用onDebouncedClick替换setOnClickListener，可以有效防重点击"

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
        val isMemberInClass = context.evaluator.isMemberInClass(method, ANDROID_VIEW)
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, ANDROID_VIEW, true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(
                ISSUE,
                node,
                context.getNameLocation(node),
                MESSAGE,
                lintFix(context)
            )
        }
    }

    private fun  lintFix(context: JavaContext):LintFix{
        val importClass = context.importPackage(PROJECT_CLICK)
        val builder = fix()
            .replace()
            .with("onDebouncedClick")
            .build()
        return fix()
            .name("Use onDebouncedClick instead")
            .composite()
            .add(builder).apply {
                if (importClass != null){
                    add(importClass)
                }
            }
            .build()
    }

}