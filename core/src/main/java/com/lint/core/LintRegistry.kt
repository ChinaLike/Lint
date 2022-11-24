package com.lint.core

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.lint.core.detector.*

/**
 * 注册器
 * @author like
 * @date 2022/6/21 10:49
 */
@Suppress("UnstableApiUsage")
class LintRegistry : IssueRegistry() {
    /**
     * The list of issues that can be found by all known detectors
     * (including those that may be disabled!)
     */
    override val issues: List<Issue> = listOf(
        LogDetector.ISSUE,
        ToastDetector.ISSUE,
        ActivityDetector.ISSUE,
        FragmentDetector.ISSUE,
        AdapterDetector.ISSUE,
        ThreadDetector.ISSUE,
        PermissionDetector.ISSUE,
        ParseDetector.COLOR_ISSUE,
        DialogDetector.ISSUE,
        AlertDialogDetector.ISSUE,
        LayoutDetector.TEXT_VIEW_ISSUE,
        LayoutDetector.EDIT_TEXT_ISSUE,
        LayoutDetector.BUTTON_ISSUE,
        LayoutDetector.IMAGE_ISSUE,
        LayoutDetector.CARD_ISSUE,
        LayoutDetector.CHECK_BOX_ISSUE,
        LayoutDetector.SCROLL_VIEW_ISSUE,
        LayoutDetector.TAB_LAYOUT_ISSUE,
        LayoutDetector.RECYCLER_VIEW_ISSUE,
//        SerializableClassDetector.ISSUE
    )

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        "Android Lint",
        "https://github.com/ChinaLike/Lint/issues",
        "https://github.com/ChinaLike/Lint"
    )
}