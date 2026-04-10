package com.zeldapet.linkwalker

import com.intellij.openapi.Disposable
import com.intellij.ui.JBColor
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

class DraggableLinkComponent : JComponent(), Disposable, ActionListener {
    private var timer: Timer? = null
    private var linkImage: Image? = null
    private var scaledImage: Image? = null
    var isBeingDragged = false
    var parentWindow: Window? = null

    // Animation properties
    private var xPosition = 0
    private var direction = 1 // 1 for right, -1 for left
    private val animationSpeed = 100 // milliseconds per frame
    private val moveSpeed = 3 // pixels per frame
    private val scaleFactor = 0.33 // Scale down to 1/3 of original size

    init {
        // Load the Link image
        loadImage()

        // Set component size based on scaled image
        if (scaledImage != null) {
            preferredSize = Dimension(scaledImage!!.getWidth(null), scaledImage!!.getHeight(null))
        } else {
            preferredSize = Dimension(100, 100)
        }

        isOpaque = false
    }

    private fun loadImage() {
        try {
            // Try to load from resources
            val resource = javaClass.classLoader.getResourceAsStream("assets/link.png")
            if (resource != null) {
                linkImage = ImageIO.read(resource)
            } else {
                // Try to load from file
                val file = File("src/main/resources/assets/link.png")
                if (file.exists()) {
                    linkImage = ImageIO.read(file)
                }
            }

            // Scale the image down
            if (linkImage != null) {
                val originalWidth = linkImage!!.getWidth(null)
                val originalHeight = linkImage!!.getHeight(null)
                val scaledWidth = (originalWidth * scaleFactor).toInt()
                val scaledHeight = (originalHeight * scaleFactor).toInt()
                scaledImage = linkImage!!.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH)
            }
        } catch (e: Exception) {
            println("Failed to load Link image: ${e.message}")
            // Create a fallback colored rectangle if image fails to load
            linkImage = null
            scaledImage = null
        }
    }

    fun startAnimation() {
        if (timer == null) {
            timer = Timer(animationSpeed, this).apply {
                start()
            }
        }
    }

    fun stopAnimation() {
        timer?.stop()
        timer = null
    }

    override fun actionPerformed(e: ActionEvent) {
        // Don't move if being dragged
        if (!isBeingDragged) {
            // Update position
            xPosition += moveSpeed * direction

            // Get screen bounds
            val screenSize = Toolkit.getDefaultToolkit().screenSize
            val maxX = screenSize.width - (scaledImage?.getWidth(null) ?: 100)

            // Bounce off edges
            if (xPosition >= maxX || xPosition <= 0) {
                direction *= -1
            }

            // Update parent window position
            parentWindow?.let { window ->
                val newLocation = Point(
                    window.location.x + moveSpeed * direction,
                    window.location.y
                )

                // Keep within screen bounds
                newLocation.x = newLocation.x.coerceIn(0, screenSize.width - window.width)
                newLocation.y = newLocation.y.coerceIn(0, screenSize.height - window.height)

                window.location = newLocation
            }
        }

        repaint()
    }

    override fun paintComponent(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        // Clear the background to be transparent
        g2d.composite = AlphaComposite.Clear
        g2d.fillRect(0, 0, width, height)
        g2d.composite = AlphaComposite.SrcOver

        // Draw the Link image
        if (scaledImage != null) {
            // Flip image if moving left
            if (direction == -1) {
                val imgWidth = scaledImage!!.getWidth(null)
                val imgHeight = scaledImage!!.getHeight(null)
                val flippedImage = createFlippedImage(scaledImage!!, imgWidth, imgHeight)
                g2d.drawImage(flippedImage, 0, 0, null)
            } else {
                g2d.drawImage(scaledImage, 0, 0, null)
            }
        } else {
            // Fallback: draw a placeholder
            drawPlaceholder(g2d)
        }
    }

    private fun createFlippedImage(image: Image, width: Int, height: Int): Image {
        val flipped = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g = flipped.createGraphics()
        g.drawImage(image, width, 0, -width, height, null)
        g.dispose()
        return flipped
    }

    private fun drawPlaceholder(g2d: Graphics2D) {
        // Draw a simple placeholder if image failed to load
        g2d.color = JBColor(Color(0x228B22), Color(0x1A6B1A))
        g2d.fillRoundRect(10, 10, 80, 80, 10, 10)

        g2d.color = JBColor(Color(0xFFD700), Color(0xE5C100))
        g2d.fillOval(30, 20, 40, 30)

        g2d.color = JBColor(Color.BLACK, Color.BLACK)
        g2d.fillOval(40, 30, 8, 8)
        g2d.fillOval(52, 30, 8, 8)

        g2d.color = JBColor(Color.WHITE, Color.WHITE)
        g2d.drawString("Link", 35, 70)
    }

    override fun dispose() {
        stopAnimation()
    }
}
