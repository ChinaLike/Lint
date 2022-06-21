package com.lint.core

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

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
    override val issues: List<Issue> = listOf(LogDetector.ISSUE)

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        "Android Open Source Project",
        "https://github.com/googlesamples/android-custom-lint-rules/issues",
        "https://github.com/googlesamples/android-custom-lint-rules"
    )
}