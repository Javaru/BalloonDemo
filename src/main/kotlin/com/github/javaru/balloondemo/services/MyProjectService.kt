package com.github.javaru.balloondemo.services

import com.intellij.openapi.project.Project
import com.github.javaru.balloondemo.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
