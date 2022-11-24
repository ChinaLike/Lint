package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * 权限规范
 * @author like
 * @date 2022/11/21 14:23
 */
class PermissionDetector : Detector(), Detector.UastScanner {

    private val contextCompatClass = "androidx.core.content.ContextCompat"
    private val activityCompatClass = "androidx.core.app.ActivityCompat"

    companion object {

        const val MESSAGE = "请使用【com.tsy.helper.PermissionHelper】替换系统权限获取"

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
            context.evaluator.isMemberInClass(method, contextCompatClass) ||
                    context.evaluator.isMemberInClass(method, activityCompatClass)
        val isMemberInSubClassOf =
            context.evaluator.isMemberInSubClassOf(method, contextCompatClass, true) ||
                    context.evaluator.isMemberInSubClassOf(method, activityCompatClass, true)
        if (isMemberInClass || isMemberInSubClassOf) {
            context.report(ISSUE, node, context.getLocation(node), MESSAGE)
        }
    }

}