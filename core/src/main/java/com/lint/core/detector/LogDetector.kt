package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_LOG
import org.jetbrains.uast.UCallExpression

/**
 * 日志输出规范检测
 * @author like
 * @date 2022/6/21 10:51
 */
@Suppress("UnstableApiUsage")
class LogDetector : Detector(), Detector.UastScanner {

    companion object {

        /**
         * 方法列表
         */
        private val methodList = listOf("v", "d", "i", "w", "e", "wtf")

        private const val MESSAGE = "规范日志使用，建议使用项目工具类Logger"

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
        return methodList
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass = context.evaluator.isMemberInClass(method, ANDROID_LOG)
        val isMemberInSubClassOf = context.evaluator.isMemberInSubClassOf(method, ANDROID_LOG, true)
        val methodName = node.methodName
        val params = node.valueArguments
        methodList.forEach {
            if (it == methodName && (isMemberInClass || isMemberInSubClassOf)) {
                context.report(
                    ISSUE,
                    node,
                    context.getLocation(node),
                    MESSAGE,
                    lintFix(
                        context,
                        methodName,
                        params[0].asSourceString(),
                        params[1].asSourceString()
                    )
                )
            }
        }
    }

    private fun lintFix(
        context: JavaContext,
        methodName: String,
        tag: String,
        message: String
    ): LintFix {
        val list = context.uastFile?.imports
        val statement = list?.get(list.size - 1)
        val lastImport = statement?.asSourceString()

        val importClass = fix()
            .replace()
            .text(lastImport)
            .with("$lastImport\nimport com.tsy.commonsdk.utils.log.Logger")
            .range(context.getLocation(statement))
            .build()
        val builder = fix()
            .replace()
            .with("Logger.${methodName}(${tag},${message})")
            .build()

        return fix()
            .name("Logger替代")
            .composite()
            .add(builder)
            .add(importClass)
            .build()
    }

}