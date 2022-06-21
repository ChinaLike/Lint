package com.lint.core

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 *
 * @author like
 * @date 2022/6/21 10:51
 */
@Suppress("UnstableApiUsage")
class LogDetector : Detector(), Detector.UastScanner {

    companion object {

        @JvmField
        val ISSUE = Issue.create(
            "LogId",
            "不要直接使用Log",
            "不要直接使用Log",
            Category.MESSAGES,
            7,
            Severity.ERROR,
            Implementation(LogDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("v", "d", "i", "w", "e")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass = context.evaluator.isMemberInClass(method, "android.util.Log")
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, "android.util.Log", true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "不要直接使用Log"
            )
        }
    }

}