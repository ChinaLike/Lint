package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * 字符串判断规范
 * @author like
 * @date 2022/11/21 14:21
 */
class StringDetector : Detector(), Detector.UastScanner {

    companion object {

        const val MESSAGE = "请使用【com.core.util.ToastUtil】替换【Toast.makeText】"

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
        return listOf("isEmpty","isDigitsOnly")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass = context.evaluator.isMemberInClass(method, "android.widget.Toast")
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, "android.widget.Toast", true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(ISSUE, node, context.getLocation(node), MESSAGE)
        }
    }

}