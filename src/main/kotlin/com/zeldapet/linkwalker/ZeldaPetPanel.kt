package com.zeldapet.linkwalker

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBPanel
import java.awt.*
import javax.swing.*

class ZeldaPetPanel(project: Project) : JBPanel<ZeldaPetPanel>(BorderLayout()) {
    private val linkWalker: LinkWalkerComponent

    init {
        // Set panel background
        background = JBColor(Color(0x2B2B2B), Color(0x3C3F41))

        // Create the Link walker component
        linkWalker = LinkWalkerComponent()

        // Create a wrapper panel to position Link at the bottom
        val wrapperPanel = JBPanel<JBPanel<*>>(BorderLayout()).apply {
            isOpaque = false
            add(linkWalker, BorderLayout.SOUTH)
        }

        // Add wrapper to main panel
        add(wrapperPanel, BorderLayout.CENTER)

        // Start the animation
        linkWalker.startAnimation()

        // Dispose animation when panel is disposed
        Disposer.register(project, linkWalker)
    }
}
