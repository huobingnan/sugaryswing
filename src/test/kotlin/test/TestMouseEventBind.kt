package test

import sugaryswing.*
import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

object TestMouseEventBind {


    private val pane = JPanel().apply {
        val label = JLabel("**Test Mouse Event**")
        label.size = Dimension(200, 50)
        label.preferredSize = label.size
        label.foreground = Color.RED
        label.border = BorderFactory.createLineBorder(Color.BLUE, 2)

        on(label, E.MouseEnter) {
            val e = eventSafeCast(MouseEvent::class.java, it)!!
            println("Mouse enter => button = ${e.button}")
        }

        on(label, E.MouseClick) {
            val e = it as MouseEvent
            println("Mouse click => button = ${e.button}")
        }

        on(label, E.MouseExit) {
            println("Mouse exit")
        }

        on(label, E.MousePress) {
            println("Mouse press")
        }
        on(label, E.MousePress) {
            println("override press")
        }

        on(label, E.MousePress, HANDLE_FUNCTION_DO_NOTHING)

        on(this, E.MousePress) {
            println("JPanel press")
        }

        add(label)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        EventQueue.invokeLater {
            TestMainFrame.contentPane = pane
            TestMainFrame.isVisible = true
        }
    }
}