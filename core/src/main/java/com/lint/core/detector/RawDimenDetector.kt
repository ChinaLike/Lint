package com.lint.core.detector

import com.android.SdkConstants.ATTR_LAYOUT_HEIGHT
import com.android.SdkConstants.ATTR_LAYOUT_MIN_HEIGHT
import com.android.SdkConstants.ATTR_LAYOUT_MIN_WIDTH
import com.android.SdkConstants.ATTR_LAYOUT_WIDTH
import com.android.SdkConstants.ATTR_MIN_HEIGHT
import com.android.SdkConstants.ATTR_MIN_WIDTH
import com.android.SdkConstants.CLASS_CONSTRAINT_LAYOUT
import com.android.SdkConstants.TAG_DIMEN
import com.android.resources.ResourceFolderType
import com.android.resources.ResourceFolderType.DRAWABLE
import com.android.resources.ResourceFolderType.LAYOUT
import com.android.resources.ResourceFolderType.VALUES
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity.WARNING
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr
import org.w3c.dom.Element
import java.util.EnumSet

/**
 * 维度值定义规范
 * @author like
 * @date 2022/11/25 17:43
 */
class RawDimenDetector : ResourceXmlDetector() {

    private var collector = ElementCollectReporter(TAG_DIMEN)

    companion object {

        const val MESSAGE =
            "dimen都应该被定义为dimen资源。这的好处是，您可以在一个文件中轻松查看所有dimen。还有一个好处是，当设计师更改整个应用程序的轮廓时，您只需在一个地方进行调整"

        @JvmField
        val ISSUE = Issue.create(
            "RawDimenStandard",
            "未定义为资源维度",
            MESSAGE,
            CORRECTNESS,
            7,
            WARNING,
            Implementation(RawDimenDetector::class.java, RESOURCE_FILE_SCOPE)
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return EnumSet.of(LAYOUT, DRAWABLE, VALUES).contains(folderType)
    }

    override fun getApplicableElements(): Collection<String>? = ALL

    override fun beforeCheckEachProject(context: Context) {
        collector = ElementCollectReporter(TAG_DIMEN)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        collector.collect(element)

        val hasLayoutWeight = element.attributes.getNamedItem("android:layout_weight") != null
//        val isParentConstraintLayout = element.hasParent(CLASS_CONSTRAINT_LAYOUT.oldName()) || element.hasParent( CLASS_CONSTRAINT_LAYOUT.newName())
        val isVectorGraphic = "vector" == element.localName || "path" == element.localName
        element.attributes()
            .asSequence()
            .filterNot { it.hasToolsNamespace() }
            .filterNot { isVectorGraphic }
            .filterNot { it.nodeName == "app:behavior_peekHeight" && it.nodeValue == "0dp" }
//            .filterNot { (hasLayoutWeight || isParentConstraintLayout) && it.nodeValue == "0dp" && (ATTR_LAYOUT_WIDTH == it.localName || ATTR_LAYOUT_HEIGHT == it.localName) }
            .filterNot { hasLayoutWeight && it.nodeValue == "0dp" && (ATTR_LAYOUT_WIDTH == it.localName || ATTR_LAYOUT_HEIGHT == it.localName) }
            .filterNot {
                it.nodeValue == "0dp" && listOf(
                    ATTR_MIN_HEIGHT,
                    ATTR_LAYOUT_MIN_HEIGHT,
                    ATTR_MIN_WIDTH,
                    ATTR_LAYOUT_MIN_WIDTH
                ).any { ignorable -> it.localName == ignorable }
            }
            .filter { it.nodeValue.matches("-?[\\d.]+(sp|dp|dip)".toRegex()) }
            .filterNot { context.driver.isSuppressed(context, ISSUE, it) }
            .map { it to context.getValueLocation(it as Attr) }
            .toCollection(collector)
    }

    override fun afterCheckEachProject(context: Context) {
        collector.report(ISSUE, context, MESSAGE)
    }

}