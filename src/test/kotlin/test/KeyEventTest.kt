package test

import sugaryswing.E
import sugaryswing.on
import java.awt.*
import java.awt.event.KeyEvent
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

object KeyEventTest {

    private val pane = JPanel().apply {
        val label = JLabel("HELLO WORLD", JLabel.CENTER)
        label.size = Dimension(300, 200)
        label.font = Font(Font.SANS_SERIF, Font.BOLD, 20)
        label.foreground = Color.RED
        label.border = BorderFactory.createRaisedBevelBorder()

        on(this, E.KeyPress) {
            val e = it as KeyEvent
            val keyString = KeyEvent.getKeyText(e.keyCode)
            println("press key is => $keyString")
        }

        on(this, E.ComponentAdded) {
            println("A component added")
        }

        add(label)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        EventQueue.invokeLater {
            TestMainFrame.contentPane.layout = BorderLayout()
            TestMainFrame.contentPane.add(pane, BorderLayout.CENTER)
            TestMainFrame.isVisible = true
        }
    }
}