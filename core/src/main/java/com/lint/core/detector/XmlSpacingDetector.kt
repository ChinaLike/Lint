package com.lint.core.detector

import com.android.ide.common.blame.SourcePosition
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Document

/**
 * 布局规范
 * @author like
 * @date 2022/11/26 10:06
 */
class XmlSpacingDetector : ResourceXmlDetector() {

    companion object {

        const val MESSAGE = "在xml文件中使用换行符只会增加代码的不美观，应该避免！"

        @JvmField
        val ISSUE = Issue.create(
            "XmlSpacingStandard",
            "XML文件不应包含任何的新行",
            MESSAGE,
            Category.MESSAGES,
            7,
            Severity.FATAL,
            Implementation(XmlSpacingDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }

    override fun visitDocument(context: XmlContext, document: Document) {
        val contents = context.client.readFile(context.file).toString().split("\n")
        contents
            .withIndex()
            .windowed(2)
            .filter {
                it[0].value.isBlank() && it.getOrNull(1)?.value?.trim()?.startsWith("<!--") == false
            }
            .map { it[0] }
            .filterNot { it.index == contents.size - 1 }
            .toList()
            .forEach {
                val location =
                    Location.create(context.file, SourcePosition(it.index, 0, it.value.length))
                val fix = fix()
                    .name("Remove new line")
                    .replace()
                    .range(location)
                    .all()
                    .autoFix(robot = true, independent = false)
                    .build()

                context.report(ISSUE, location, "Unnecessary new line at line ${it.index + 1}", fix)
            }
    }

}