package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * 日志输出规范检测
 * @author like
 * @date 2022/6/21 10:51
 */
@Suppress("UnstableApiUsage")
class LogDetector : Detector(), Detector.UastScanner {

    companion object {

        const val MESSAGE = "请使用【com.tsy.commonsdk.utils.log.Logger】替换【Log】"

        @JvmField
        val ISSUE = Issue.create(
            "LogStandard",
            "日志输出不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(LogDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("v", "d", "i", "w", "e","wtf")
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
                MESSAGE
            )
        }
    }

}