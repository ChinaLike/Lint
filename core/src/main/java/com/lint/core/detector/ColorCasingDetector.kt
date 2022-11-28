package com.lint.core.detector

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr
import org.w3c.dom.Element
import java.util.*

/**
 * 颜色字符串规范
 * @author like
 * @date 2022/11/25 15:44
 */
class ColorCasingDetector : ResourceXmlDetector() {

    companion object {

        private val COLOR_REGEX = Regex("#[a-fA-F\\d]{3,8}")

        private const val MESSAGE = "颜色值应为大写字母，例如:#FF0099有效，而#ff0099无效，因为ff应该大写"

        @JvmField
        val ISSUE = Issue.create(
            "ColorCasingStandard",
            "颜色值应使用大写字母定义",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(ColorCasingDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean = true

    override fun getApplicableElements(): Collection<String>? = ALL

    override fun visitElement(context: XmlContext, element: Element) {
        element.attributes()
            .filter { it.nodeValue.matches(COLOR_REGEX) }
            .filter { it.nodeValue.any { it.isLowerCase() } }
            .forEach {
                val fix = fix()
                    .name("convert to uppercase")
                    .replace()
                    .text(it.nodeValue)
                    .with(it.nodeValue.uppercase(Locale.US))
                    .autoFix()
                    .build()
                context.report(ISSUE, it, context.getValueLocation(it as Attr), MESSAGE, fix)
            }
    }

}