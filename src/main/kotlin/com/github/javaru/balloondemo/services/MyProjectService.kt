package com.github.javaru.balloondemo.services

import com.intellij.openapi.project.Project
import com.github.javaru.balloondemo.MyBundle
import com.intellij.openapi.Disposable

class MyProjectService(project: Project): Disposable {

    init {
        println(MyBundle.message("projectService", project.name))
    }

    override fun dispose()
    {

    }
}
