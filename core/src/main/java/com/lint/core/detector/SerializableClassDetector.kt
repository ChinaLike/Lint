package com.lint.core.detector

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiClassType
import org.jetbrains.uast.UClass

/**
 * Serializable类规范
 * @author like
 * @date 2022/11/23 23:23
 */
class SerializableClassDetector : Detector(), Detector.UastScanner {

    companion object {
        private const val MESSAGE = "该对象必须要实现Serializable接口，因为外部类实现了Serializable接口"
        private const val CLASS_SERIALIZABLE = "java.io.Serializable"
        val ISSUE = Issue.create(
            "SerializableClassStandard",
            "Serializable类使用不规范",
            MESSAGE,
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            Implementation(SerializableClassDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun applicableSuperClasses(): List<String>? {
        return listOf(CLASS_SERIALIZABLE)
    }

    override fun visitClass(context: JavaContext, declaration: UClass) {
        for (field in declaration.fields) {
            //字段是引用类型，并且可以拿到该class
            val psiClass = (field.type as? PsiClassType)?.resolve() ?: continue
            if (!context.evaluator.implementsInterface(psiClass, CLASS_SERIALIZABLE, true)) {
                context.report(ISSUE, context.getLocation(field.typeReference!!), MESSAGE)
            }
        }
    }
}