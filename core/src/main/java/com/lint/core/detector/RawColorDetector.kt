package com.lint.core.detector

import com.android.SdkConstants.*
import com.android.resources.ResourceFolderType
import com.android.resources.ResourceFolderType.*
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr
import org.w3c.dom.Element
import java.util.*

/**
 * 颜色值定义规范
 * @author like
 * @date 2022/11/25 17:43
 */
class RawColorDetector : ResourceXmlDetector() {

    private var collector = ElementCollectReporter(ATTR_COLOR)

    companion object {

        private const val MESSAGE = "颜色值应全部定义为颜色资源。这有一个好处，即您可以在一个文件中轻松查看所有颜色。例如:一个好处是更容易添加深色主题。"

        @JvmField
        val ISSUE = Issue.create(
            "RawColorStandard",
            "未定义为资源的标志颜色",
            MESSAGE,
            Category.CORRECTNESS,
            7,
            Severity.WARNING,
            Implementation(RawColorDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return EnumSet.of(LAYOUT, DRAWABLE, VALUES).contains(folderType)
    }

    override fun getApplicableElements(): Collection<String>? = ALL

    override fun beforeCheckEachProject(context: Context) {
        collector = ElementCollectReporter(ATTR_COLOR)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        collector.collect(element)

        element.attributes()
            .asSequence()
            .filterNot { TAG_VECTOR == element.localName || ATTR_PATH == element.localName }
            .filterNot { it.hasToolsNamespace() }
            .filter { it.nodeValue.matches("#[a-fA-F\\d]{3,8}".toRegex()) }
            .filterNot { context.driver.isSuppressed(context, ISSUE, it) }
            .map { it to context.getValueLocation(it as Attr) }
            .toCollection(collector)
    }

    override fun afterCheckEachProject(context: Context) {
        collector.report(ISSUE, context, MESSAGE)
    }

}