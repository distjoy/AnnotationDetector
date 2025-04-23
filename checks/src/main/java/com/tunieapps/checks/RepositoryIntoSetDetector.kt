package com.tunieapps.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod


class RepositoryIntoSetDetector : Detector(), SourceCodeScanner
{
    companion object Issues {
        private val IMPLEMENTATION = Implementation(RepositoryIntoSetDetector::class.java, Scope.JAVA_FILE_SCOPE)
        val ANNOTATION_USAGE =   Issue.create(
            "IncorrectProvidesAnnotationUsage",
            "Incorrect provides annotation usage",
            "",
            Category.CORRECTNESS,
            2,
            Severity.ERROR,
            IMPLEMENTATION)
    }

    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        return listOf(UAnnotation::class.java)
    }


    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object  : UElementHandler() {
            override fun visitAnnotation(node: UAnnotation) {
                val type = node.qualifiedName
                if (type == null || !type.startsWith("dagger.")) {
                    return
                }
                if (type == "dagger.Provides"){
                    node.uastParent?.let {
                       (it as UMethod).apply {
                           if (! it.hasAnnotation("dagger.multibindings.IntoSet") &&
                               it.returnTypeReference?.getQualifiedName() == "di.Test"){
                                context.report(
                                    issue = ANNOTATION_USAGE,
                                    node,
                                    context.getLocation(node),
                                    ""
                                )
                            }

                        }
                    }
                }
                return
            }
        }
    }
}