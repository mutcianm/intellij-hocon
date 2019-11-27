package org.jetbrains.plugins.hocon
package ref

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.PackageIndex
import com.intellij.psi.{PsiFileSystemItem, PsiManager}
import com.intellij.psi.search.GlobalSearchScope

class JavaClasspathPackageDirsProvider extends ClasspathPackageDirsProvider {
  override def classpathPackageDirs(project: Project, scope: GlobalSearchScope, pkgName: String): List[PsiFileSystemItem] = {
    val psiManager = PsiManager.getInstance(project)
    PackageIndex.getInstance(project).getDirectoriesByPackageName(pkgName, false).iterator
      .filter(scope.contains).flatMap(dir => Option(psiManager.findDirectory(dir)))
      .toList
  }
}
