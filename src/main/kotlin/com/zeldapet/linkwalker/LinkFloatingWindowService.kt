package com.zeldapet.linkwalker

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class LinkFloatingWindowService(private val project: Project) {
    private var floatingWindow: LinkFloatingWindow? = null

    fun showFloatingWindow() {
        if (floatingWindow == null) {
            floatingWindow = LinkFloatingWindow(project)
        }
        floatingWindow?.show()
    }

    fun hideFloatingWindow() {
        floatingWindow?.hide()
    }

    fun toggleFloatingWindow() {
        if (floatingWindow?.isVisible() == true) {
            hideFloatingWindow()
        } else {
            showFloatingWindow()
        }
    }

    fun isFloatingWindowVisible(): Boolean {
        return floatingWindow?.isVisible() == true
    }

    companion object {
        fun getInstance(project: Project): LinkFloatingWindowService {
            return project.service()
        }
    }
}
