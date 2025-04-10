package com.tunieapps.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiType
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UMethod


class RepositoryIntoSetDetector : Detector(), SourceCodeScanner
{
    companion object Issues {
        private val IMPLEMENTATION = Implementation(RepositoryIntoSetDetector::class.java, Scope.JAVA_FILE_SCOPE)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object  : UElementHandler() {
            override fun visitAnnotation(node: UAnnotation) {
                val type = node.qualifiedName
                if (type == null || type.startsWith("javax.inject.")) {
                    return
                }
                if (type == "javax.inject.Provides"){
                    node.uastParent?.let {
                        (it as UMethod).apply {
                           // if(!it.isConstructor && it.returnType == PsiType.getTypeByName())
                        }
                    }
                }
                return
            }
        }
    }
}