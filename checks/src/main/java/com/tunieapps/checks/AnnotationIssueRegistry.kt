package com.tunieapps.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class AnnotationIssueRegistry : IssueRegistry(){
    override val issues: List<Issue>
        get() = listOf(RepositoryIntoSetDetector.ANNOTATION_USAGE)

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8 // works with Studio 4.1 or later; see com.android.tools.lint.detector.api.Api / ApiKt

    // Requires lint API 30.0+; if you're still building for something
    // older, just remove this property.
    override val vendor: Vendor =
        Vendor(
            vendorName = "Android Open Source Project",
            feedbackUrl = "https://github.com/googlesamples/android-custom-lint-rules/issues",
            contact = "https://github.com/googlesamples/android-custom-lint-rules",
        )
}