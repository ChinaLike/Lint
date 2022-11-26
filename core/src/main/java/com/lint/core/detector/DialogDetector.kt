package com.lint.core.detector

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import org.jetbrains.uast.*

/**
 * 弹窗规范
 * @author like
 * @date 2022/11/23 16:28
 */
class DialogDetector : Detector(), SourceCodeScanner {


    companion object {

        private const val dialogClass = "android.app.Dialog"
        private const val popupClass = "android.widget.PopupWindow"
        private const val alertDialogClass = "android.app.AlertDialog"
        private const val alertDialogBuilderClass = "${alertDialogClass}.Builder"

        /**
         * 需要继承的类
         */
        private const val superClass = "com.lxj.xpopup.core.BasePopupView"

        const val MESSAGE = "弹窗应该使用【XPopup】三方弹窗，以便统一管理"

        @JvmField
        val ISSUE = Issue.create(
            "DialogStandard",
            "Dialog继承不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(DialogDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )

        @JvmField
        val ALERT_DIALOG_ISSUE = Issue.create(
            "AlertDialogStandard",
            "AlertDialog使用不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(DialogDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun applicableSuperClasses(): List<String>? {
        return listOf(dialogClass, popupClass)
    }

    override fun getApplicableConstructorTypes(): List<String>? {
        return listOf(alertDialogClass, alertDialogBuilderClass)
    }

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod
    ) {
        context.report(ALERT_DIALOG_ISSUE, node, context.getLocation(node), MESSAGE)
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf<Class<out UElement>>(UVariable::class.java, UClass::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? =
        AlertDialogUsageHandler(context)

    override fun visitClass(context: JavaContext, declaration: UClass) {
        val evaluator = context.evaluator
        //判断是否继承指定类
        if (!evaluator.extendsClass(declaration.javaPsi, superClass, true)) {
            context.report(ISSUE, declaration, context.getNameLocation(declaration), MESSAGE)
        }
    }

    class AlertDialogUsageHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitVariable(node: UVariable) = process(node.type, node)

        override fun visitClass(node: UClass) = node.uastSuperTypes.forEach { process(it.type, it) }

        private fun process(type: PsiType, node: UElement) {
            if (context.evaluator.typeMatches(type, alertDialogClass)) {
                context.report(ALERT_DIALOG_ISSUE, node, context.getLocation(node), MESSAGE)
            }
        }

    }

}