package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.lint.core.Constants.ANDROID_ACTIVITY_COMPAT
import com.lint.core.Constants.ANDROID_CONTEXT_COMPAT
import org.jetbrains.uast.UCallExpression

/**
 * 权限规范
 * @author like
 * @date 2022/11/21 14:23
 */
class PermissionDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val MESSAGE = "请统一使用PermissionHelper替换系统权限获取"

        @JvmField
        val ISSUE = Issue.create(
            "PermissionStandard",
            "Permission使用不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(PermissionDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf(
            "checkSelfPermission",
            "requestPermissions",
            "shouldShowRequestPermissionRationale"
        )
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val isMemberInClass =
            context.evaluator.isMemberInClass(method, ANDROID_CONTEXT_COMPAT) ||
                    context.evaluator.isMemberInClass(method, ANDROID_ACTIVITY_COMPAT)
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, ANDROID_CONTEXT_COMPAT, true) ||
                    context.evaluator.isMemberInSubClassOf(method, ANDROID_ACTIVITY_COMPAT, true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(ISSUE, node, context.getLocation(node), MESSAGE)
        }
    }

}