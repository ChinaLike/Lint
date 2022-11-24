package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiAnonymousClass
import org.jetbrains.uast.UClass

/**
 * 适配器规范
 * @author like
 * @date 2022/11/21 14:19
 */
class AdapterDetector : Detector(), SourceCodeScanner {

    private val adapterClass = "androidx.recyclerview.widget.RecyclerView.Adapter"


    companion object {

        /**
         * 单布局需要继承的类
         */
        private const val superSingleClass = "com.tsy.base.adapter.BaseViewBindingAdapter"

        /**
         * 多布局需要继承的类
         */
        private const val superMultiClass = "com.chad.library.adapter.base.BaseProviderMultiAdapter"

        const val MESSAGE = "Adapter应继承自【${superSingleClass}】或【${superMultiClass}】或其子类"

        @JvmField
        val ISSUE = Issue.create(
            "AdapterStandard",
            "Adapter继承不规范",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(AdapterDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun applicableSuperClasses(): List<String>? {
        return listOf(adapterClass)
    }

    override fun visitClass(context: JavaContext, declaration: UClass) {
        val evaluator = context.evaluator
        //判断是否继承指定类
        if (!evaluator.extendsClass(declaration.javaPsi, superSingleClass, true) ||
            !evaluator.extendsClass(declaration.javaPsi, superMultiClass, true)
        ) {
            context.report(ISSUE, declaration, context.getNameLocation(declaration), MESSAGE)
        }
    }
}