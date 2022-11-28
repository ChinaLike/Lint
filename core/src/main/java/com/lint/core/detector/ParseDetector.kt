package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiTryStatement
import com.intellij.psi.impl.source.tree.java.MethodElement
import com.lint.core.Constants.ANDROID_COLOR
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UTryExpression
import org.jetbrains.uast.getParentOfType
import org.jetbrains.uast.kotlin.KotlinUFile


/**
 * 转换规范
 * @author like
 * @date 2022/11/23 14:52
 */
class ParseDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val COLOR_MESSAGE = "使用Color.parseColor需对异常进行处理"

        @JvmField
        val COLOR_ISSUE = Issue.create(
            "ColorStandard",
            "parseColor使用不规范",
            COLOR_MESSAGE,
            Category.MESSAGES,
            8,
            Severity.ERROR,
            Implementation(ParseDetector::class.java, Scope.JAVA_FILE_SCOPE)
        ).setAndroidSpecific(true)
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("parseColor")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (isWrappedByTryCatch(node, context)) {
            return
        }

        if (context.evaluator.isMemberInClass(method, ANDROID_COLOR)) {
            val fix = LintFix.create()
                .name("add try catch")
                .replace()
                .with(
                    "try {\n" +
                            "   Color.${node.methodName}(${node.valueArguments[0].asSourceString()})\n" +
                            "} catch (e: IllegalArgumentException) {\n" +
                            "    e.printStackTrace()\n" +
                            "}"
                )
                .build()
            context.report(
                COLOR_ISSUE, node,
                context.getCallLocation(node, includeReceiver = true, includeArguments = true),
                COLOR_MESSAGE,
                fix
            )
        }
    }

    /**
     * 是否已经做了 try处理
     */
    private fun isWrappedByTryCatch(node: UCallExpression, context: JavaContext): Boolean {
        if (context.uastFile is KotlinUFile) {
            return node.uastParent?.getParentOfType<UTryExpression>(true) != null
        }
        var parent = node.sourcePsi?.parent
        while (parent != null && parent !is MethodElement) {
            if (parent is PsiTryStatement) {
                return true
            }
            parent = parent.parent
        }
        return false
    }

}