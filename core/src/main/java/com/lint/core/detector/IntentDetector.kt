package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_ACTIVITY
import com.lint.core.Constants.ANDROID_CONTEXT
import org.jetbrains.uast.UCallExpression

/**
 * 界面跳转规范
 * @author like
 * @date 2022/11/26 15:27
 */
class IntentDetector : Detector(), Detector.UastScanner {

    companion object {

        /**
         * 方法列表
         */
        private val methodList = listOf("startActivity")

        private const val MESSAGE = "模块法开发中，为了减少类的耦合，建议使用ARouter作为路由导航"

        @JvmField
        val ISSUE = Issue.create(
            "IntentStandard",
            "界面导航使用不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(IntentDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return methodList
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass = context.evaluator.isMemberInClass(method, ANDROID_ACTIVITY)
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, ANDROID_ACTIVITY, true)
        val isMemberInContextClass = context.evaluator.isMemberInClass(method, ANDROID_CONTEXT)
        val isMemberInSubContextClassOf =
            context.evaluator.isMemberInSubClassOf(method, ANDROID_CONTEXT, true)
        val methodName = node.methodName
        methodList.forEach {
            if (it == methodName && (isMemberInClass || isMemberInSubClassOf || isMemberInContextClass || isMemberInSubContextClassOf)) {
                context.report(
                    ISSUE,
                    node,
                    context.getLocation(node),
                    MESSAGE
                )
            }
        }
    }

}