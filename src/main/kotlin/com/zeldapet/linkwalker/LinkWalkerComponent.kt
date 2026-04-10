package com.zeldapet.linkwalker

import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.ui.JBColor
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

class LinkWalkerComponent : JComponent(), Disposable, ActionListener {
    private var timer: Timer? = null
    private var currentFrame = 0
    private var xPosition = 0
    private var direction = 1 // 1 for right, -1 for left
    private val animationSpeed = 100 // milliseconds per frame
    private val moveSpeed = 2 // pixels per frame
    private val totalFrames = 4 // Number of walking frames

    // Link sprite colors (classic green tunic Link)
    private val hatColor = JBColor(Color(0x006400), Color(0x004D00))
    private val skinColor = JBColor(Color(0xFFDBAC), Color(0xE8C8A0))
    private val tunicColor = JBColor(Color(0x228B22), Color(0x1A6B1A))
    private val beltColor = JBColor(Color(0x8B4513), Color(0x6B3510))
    private val pantsColor = JBColor(Color(0xFFFFFF), Color(0xE0E0E0))
    private val bootColor = JBColor(Color(0x8B4513), Color(0x6B3510))
    private val hairColor = JBColor(Color(0xFFD700), Color(0xE5C100))

    init {
        preferredSize = Dimension(200, 100)
        isOpaque = false
    }

    fun startAnimation() {
        if (timer == null) {
            timer = Timer(animationSpeed, this).apply {
                start()
            }
        }
    }

    override fun actionPerformed(e: ActionEvent) {
        // Update animation frame
        currentFrame = (currentFrame + 1) % totalFrames

        // Update position
        xPosition += moveSpeed * direction

        // Bounce off edges
        val maxX = width - 60 // 60 is approximate sprite width
        if (xPosition >= maxX || xPosition <= 0) {
            direction *= -1
        }

        repaint()
    }

    override fun paintComponent(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Draw Link at current position
        drawLink(g2d, xPosition, height - 80, currentFrame, direction)
    }

    private fun drawLink(g: Graphics2D, x: Int, y: Int, frame: Int, dir: Int) {
        val scale = 1.0
        val baseX = x.toDouble()
        val baseY = y.toDouble()

        // Calculate leg positions based on frame
        val leftLegOffset = when (frame) {
            0 -> 0.0
            1 -> -5.0
            2 -> 0.0
            3 -> 5.0
            else -> 0.0
        }
        val rightLegOffset = when (frame) {
            0 -> 0.0
            1 -> 5.0
            2 -> 0.0
            3 -> -5.0
            else -> 0.0
        }

        // Calculate arm positions based on frame
        val leftArmOffset = when (frame) {
            0 -> 0.0
            1 -> 5.0
            2 -> 0.0
            3 -> -5.0
            else -> 0.0
        }
        val rightArmOffset = when (frame) {
            0 -> 0.0
            1 -> -5.0
            2 -> 0.0
            3 -> 5.0
            else -> 0.0
        }

        // Draw boots
        g.color = bootColor
        // Left boot
        g.fillRoundRect(
            (baseX + 10 + leftLegOffset).toInt(),
            (baseY + 65).toInt(),
            12, 8, 2, 2
        )
        // Right boot
        g.fillRoundRect(
            (baseX + 30 + rightLegOffset).toInt(),
            (baseY + 65).toInt(),
            12, 8, 2, 2
        )

        // Draw pants
        g.color = pantsColor
        // Left leg
        g.fillRect(
            (baseX + 12 + leftLegOffset).toInt(),
            (baseY + 50).toInt(),
            8, 18
        )
        // Right leg
        g.fillRect(
            (baseX + 32 + rightLegOffset).toInt(),
            (baseY + 50).toInt(),
            8, 18
        )

        // Draw tunic
        g.color = tunicColor
        g.fillRoundRect(
            (baseX + 8).toInt(),
            (baseY + 30).toInt(),
            36, 25, 5, 5
        )

        // Draw belt
        g.color = beltColor
        g.fillRect(
            (baseX + 8).toInt(),
            (baseY + 45).toInt(),
            36, 4
        )

        // Draw arms
        g.color = tunicColor
        // Left arm
        g.fillRoundRect(
            (baseX - 2 + leftArmOffset).toInt(),
            (baseY + 32).toInt(),
            10, 18, 3, 3
        )
        // Right arm
        g.fillRoundRect(
            (baseX + 44 + rightArmOffset).toInt(),
            (baseY + 32).toInt(),
            10, 18, 3, 3
        )

        // Draw hands
        g.color = skinColor
        // Left hand
        g.fillOval(
            (baseX - 2 + leftArmOffset).toInt(),
            (baseY + 48).toInt(),
            8, 8
        )
        // Right hand
        g.fillOval(
            (baseX + 44 + rightArmOffset).toInt(),
            (baseY + 48).toInt(),
            8, 8
        )

        // Draw head
        g.color = skinColor
        g.fillOval(
            (baseX + 10).toInt(),
            (baseY + 5).toInt(),
            32, 28
        )

        // Draw hair
        g.color = hairColor
        // Hair on top
        g.fillOval(
            (baseX + 8).toInt(),
            (baseY + 2).toInt(),
            36, 15
        )
        // Side hair
        g.fillOval(
            (baseX + 4).toInt(),
            (baseY + 10).toInt(),
            10, 15
        )
        g.fillOval(
            (baseX + 38).toInt(),
            (baseY + 10).toInt(),
            10, 15
        )

        // Draw hat
        g.color = hatColor
        // Hat base
        g.fillOval(
            (baseX + 6).toInt(),
            (baseY - 2).toInt(),
            40, 20
        )
        // Hat point
        val hatPoints = intArrayOf(
            (baseX + 26).toInt(), (baseY - 15).toInt(),
            (baseX + 20).toInt(), (baseY - 2).toInt(),
            (baseX + 32).toInt(), (baseY - 2).toInt()
        )
        g.fillPolygon(hatPoints, intArrayOf(
            (baseY - 15).toInt(), (baseY - 2).toInt(), (baseY - 2).toInt()
        ), 3)

        // Draw eyes
        g.color = JBColor(Color.BLACK, Color.BLACK)
        // Left eye
        g.fillOval(
            (baseX + 18).toInt(),
            (baseY + 15).toInt(),
            4, 5
        )
        // Right eye
        g.fillOval(
            (baseX + 30).toInt(),
            (baseY + 15).toInt(),
            4, 5
        )

        // Draw eye highlights
        g.color = JBColor(Color.WHITE, Color.WHITE)
        g.fillOval(
            (baseX + 19).toInt(),
            (baseY + 16).toInt(),
            2, 2
        )
        g.fillOval(
            (baseX + 31).toInt(),
            (baseY + 16).toInt(),
            2, 2
        )

        // Draw nose
        g.color = JBColor(Color(0xE8B89D), Color(0xD4A685))
        g.fillOval(
            (baseX + 24).toInt(),
            (baseY + 20).toInt(),
            4, 3
        )

        // Draw mouth (slight smile)
        g.color = JBColor(Color(0xCC6666), Color(0xB35555))
        g.drawArc(
            (baseX + 22).toInt(),
            (baseY + 24).toInt(),
            8, 5, 180, 180
        )
    }

    override fun dispose() {
        timer?.stop()
        timer = null
    }
}
