package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiAnonymousClass
import com.lint.core.Constants.ANDROID_FRAGMENT
import com.lint.core.Constants.PROJECT_FRAGMENT
import org.jetbrains.uast.UClass

/**
 * Fragment规范
 * @author like
 * @date 2022/6/22 18:06
 */
class FragmentDetector : Detector(), SourceCodeScanner {

    companion object {

        private const val MESSAGE = "Fragment应继承自【${PROJECT_FRAGMENT}】或其子类"

        @JvmField
        val ISSUE = Issue.create(
            "FragmentStandard",
            "Fragment继承不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(FragmentDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun applicableSuperClasses(): List<String>? {
        return listOf(ANDROID_FRAGMENT)
    }

    override fun visitClass(context: JavaContext, declaration: UClass) {
        val evaluator = context.evaluator
        if (evaluator.isAbstract(declaration) || evaluator.isPrivate(declaration) || declaration is PsiAnonymousClass) {
            return
        }
        if (declaration.containingClass != null && !evaluator.isStatic(declaration)) {
            //忽略静态内部类
            return
        }
        //判断是否继承指定类
        if (!evaluator.extendsClass(declaration.javaPsi, PROJECT_FRAGMENT, true)) {
            context.report(ISSUE, declaration, context.getNameLocation(declaration), MESSAGE)
        }
    }

}