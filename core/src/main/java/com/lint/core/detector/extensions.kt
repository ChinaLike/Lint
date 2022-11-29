package com.lint.core.detector

import com.android.SdkConstants.TOOLS_URI
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.lint.core.Constants
import org.w3c.dom.Node

/**
 *
 * @author like
 * @date 2022/11/25 15:56
 */

internal fun Node.attributes() = (0 until attributes.length).map { attributes.item(it) }

internal fun Node.hasToolsNamespace() = TOOLS_URI.equals(namespaceURI, ignoreCase = true)

internal fun Node.hasParent(parent: String) = parentNode.isOf(parent)

internal fun Node.isOf(value: String) =
    value == localName || value == attributes?.getNamedItem("tools:parentTag")?.nodeValue

/**
 * 引用包名中是否有指定引用
 */
internal fun JavaContext.importIsContainer(packageName: String): Boolean {
    var isContainer: Boolean = false
    uastFile?.imports?.forEach {
        if (it.asSourceString().contains(packageName)) {
            isContainer = true
        }
    }
    return isContainer
}

/**
 * 导包
 */
internal fun JavaContext.importPackage(packageName: String): LintFix? {
    var importClass: LintFix? = null
    if (!importIsContainer(packageName)) {
        val importList = uastFile?.imports
        if (importList != null && importList.isNotEmpty()) {
            val statement = importList?.get(importList.size - 1)
            val lastImport = statement?.asSourceString()
            importClass = LintFix.create()
                .replace()
                .text(lastImport)
                .with("$lastImport\nimport $packageName")
                .range(getLocation(statement))
                .build()
        }
    }
    return importClass
}