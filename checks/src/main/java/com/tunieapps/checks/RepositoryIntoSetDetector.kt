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
import com.intellij.psi.PsiType
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UMethod


class RepositoryIntoSetDetector : Detector(), SourceCodeScanner
{
    companion object Issues {
        private val IMPLEMENTATION = Implementation(RepositoryIntoSetDetector::class.java, Scope.JAVA_FILE_SCOPE)
        private val ANNOTATION_USAGE =   Issue.create(
            "IncorrectProvidesAnnotationUsage",
            "Incorrect provides annotation usage",
            "",
            Category.CORRECTNESS,
            2,
            Severity.ERROR,
            IMPLEMENTATION)
    }


    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object  : UElementHandler() {
            override fun visitAnnotation(annotation: UAnnotation) {
                val type = annotation.qualifiedName
                if (type == null || type.startsWith("javax.inject.")) {
                    return
                }
                if (type == "javax.inject.Provides"){ //need to also check if return type is a sepcific class
                    annotation.uastParent?.let {
                        (it as UMethod).apply {
                            if ( it.hasAnnotation("dagger.multibindings.IntoSet")){
                                context.report(
                                    issue = ANNOTATION_USAGE,
                                    annotation,
                                    context.getLocation(annotation),
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