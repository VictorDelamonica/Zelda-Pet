package com.zeldapet.linkwalker

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.application.ApplicationListener
import com.intellij.openapi.startup.StartupActivity

class ZeldaPetApplicationListener : ApplicationListener {
    override fun applicationExiting() {
        // Cleanup if needed when application is exiting
    }
}

class ZeldaPetStartupActivity : StartupActivity {
    override fun runActivity(project: Project) {
        // Auto-show the floating window when the application starts
        ApplicationManager.getApplication().invokeLater {
            val service = LinkFloatingWindowService.getInstance(project)
            service.showFloatingWindow()
        }
    }
}
