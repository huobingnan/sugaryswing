package test

import java.awt.Dimension
import javax.swing.JFrame


val TestMainFrame = JFrame("Main Frame").apply {
    isVisible = false
    size = Dimension(800, 600)
    preferredSize = size
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    setLocationRelativeTo(null)
}