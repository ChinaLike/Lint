package com.lint.core.detector

import com.android.SdkConstants.TOOLS_URI
import org.w3c.dom.Node

/**
 *
 * @author like
 * @date 2022/11/25 15:56
 */

internal fun Node.attributes() = (0 until attributes.length).map { attributes.item(it) }

internal fun Node.hasToolsNamespace() = TOOLS_URI.equals(namespaceURI, ignoreCase = true)

internal fun Node.hasParent(parent: String) = parentNode.isOf(parent)

internal fun Node.isOf(value: String) = value == localName || value == attributes?.getNamedItem("tools:parentTag")?.nodeValue