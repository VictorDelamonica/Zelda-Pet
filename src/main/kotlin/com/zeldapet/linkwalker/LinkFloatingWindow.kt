package com.zeldapet.linkwalker

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.JBColor
import java.awt.*
import java.awt.event.*
import javax.swing.*

class LinkFloatingWindow(private val project: Project) : Disposable, MouseListener, MouseMotionListener {
    private var floatingWindow: Window? = null
    private var linkComponent: DraggableLinkComponent? = null
    private var isDragging = false
    private var dragOffsetX = 0
    private var dragOffsetY = 0

    init {
        createFloatingWindow()
    }

    private fun createFloatingWindow() {
        // Create undecorated window for floating effect
        val frame = JFrame()
        frame.isUndecorated = true
        frame.isAlwaysOnTop = true
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        frame.background = Color(0, 0, 0, 0) // Transparent background

        floatingWindow = frame

        // Create the draggable Link component
        linkComponent = DraggableLinkComponent().apply {
            addMouseListener(this@LinkFloatingWindow)
            addMouseMotionListener(this@LinkFloatingWindow)
        }

        // Create transparent panel and add component
        val panel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                // Make panel transparent
                val g2d = g as Graphics2D
                g2d.composite = AlphaComposite.Clear
                g2d.fillRect(0, 0, width, height)
                g2d.composite = AlphaComposite.SrcOver
                super.paintComponent(g)
            }
        }.apply {
            isOpaque = false
            layout = BorderLayout()
            add(linkComponent, BorderLayout.CENTER)
        }

        frame.add(panel)
        frame.pack()

        // Position at bottom left initially
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        frame.location = Point(50, (screenSize.height - 150).toInt())

        // Make window visible
        frame.isVisible = true

        // Dispose when project is disposed
        Disposer.register(project, this)
    }

    // MouseListener methods
    override fun mousePressed(e: MouseEvent) {
        isDragging = true
        dragOffsetX = e.x
        dragOffsetY = e.y
        linkComponent?.isBeingDragged = true
    }

    override fun mouseReleased(e: MouseEvent) {
        isDragging = false
        linkComponent?.isBeingDragged = false
    }

    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}
    override fun mouseClicked(e: MouseEvent) {}

    // MouseMotionListener methods
    override fun mouseDragged(e: MouseEvent) {
        if (isDragging && floatingWindow != null) {
            val window = floatingWindow!!
            val newLocation = Point(
                window.location.x + e.x - dragOffsetX,
                window.location.y + e.y - dragOffsetY
            )

            // Keep within screen bounds
            val screenSize = Toolkit.getDefaultToolkit().screenSize
            newLocation.x = newLocation.x.coerceIn(0, screenSize.width - window.width)
            newLocation.y = newLocation.y.coerceIn(0, screenSize.height - window.height)

            window.location = newLocation
        }
    }

    override fun mouseMoved(e: MouseEvent) {}

    fun show() {
        floatingWindow?.isVisible = true
        linkComponent?.startAnimation()
    }

    fun hide() {
        floatingWindow?.isVisible = false
        linkComponent?.stopAnimation()
    }

    fun isVisible(): Boolean {
        return floatingWindow?.isVisible == true
    }

    override fun dispose() {
        linkComponent?.stopAnimation()
        floatingWindow?.dispose()
        floatingWindow = null
        linkComponent = null
    }
}
