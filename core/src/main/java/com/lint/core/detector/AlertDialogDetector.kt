package com.lint.core.detector

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiType
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UVariable

/**
 * AlertDialog规范
 * @author like
 * @date 2022/11/24 10:40
 */
class AlertDialogDetector : Detector(), Detector.UastScanner {

    companion object {

        private const val alertDialogClass = "android.app.AlertDialog"

        const val MESSAGE = "弹窗应该使用【XPopup】三方弹窗，以便统一管理"

        @JvmField
        val ISSUE = Issue.create(
            "AlertDialogStandard",
            "AlertDialog使用不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(AlertDialogDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf<Class<out UElement>>(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? =
        AlertDialogUsageHandler(context)

    class AlertDialogUsageHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitVariable(node: UVariable) = process(node.type, node)

        override fun visitClass(node: UClass) = node.uastSuperTypes.forEach { process(it.type, it) }

        private fun process(type: PsiType, node: UElement) {
            if (context.evaluator.typeMatches(type, alertDialogClass)) {
                context.report(ISSUE, node, context.getLocation(node), MESSAGE)
            }
        }

    }
}