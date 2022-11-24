package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiAnonymousClass
import org.jetbrains.uast.UClass

/**
 * Fragment规范
 * @author like
 * @date 2022/6/22 18:06
 */
class FragmentDetector : Detector(), SourceCodeScanner {

    private val fragmentClass = "androidx.fragment.app.Fragment"

    companion object {

        /**
         * 需要继承的类
         */
        private const val superClass = "com.tsy.base.fragment.BaseFragment"

        const val MESSAGE = "Fragment应继承自【${superClass}】或其子类"

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
        return listOf(fragmentClass)
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
        if (!evaluator.extendsClass(declaration.javaPsi, superClass, true)) {
            context.report(ISSUE, declaration, context.getNameLocation(declaration), MESSAGE)
        }
    }

}