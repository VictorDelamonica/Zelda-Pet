package com.zeldapet.linkwalker

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class ZeldaPetToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val zeldaPetPanel = ZeldaPetPanel(project)
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(zeldaPetPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
