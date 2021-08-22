package test

import sugaryswing.*
import java.awt.BorderLayout
import java.awt.EventQueue
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.MouseWheelEvent
import javax.swing.JEditorPane
import javax.swing.JPanel
import javax.swing.border.TitledBorder
import javax.swing.event.DocumentEvent


object ChangeEditorFont {

    private val emitter = createPipeline("TEST")

    private val mainPane = JPanel().apply {
        border = TitledBorder("editor")
        layout = BorderLayout()
        val editorPane = JEditorPane()
        val editorPaneDefaultFont = Font(Font.SANS_SERIF, Font.PLAIN, 15)
        editorPane.font = editorPaneDefaultFont
        add(editorPane, BorderLayout.CENTER)
        val controlKeyCode = 157
        var isControlPress = false
        on(editorPane, E.KeyPress) {
            val e = it as KeyEvent
            if (e.keyCode == controlKeyCode) {
                isControlPress = true
            }
            emitter.emit("Hello world")

        }

        subscribePipeline("TEST",
            DefaultPipelineObserver(Scope.EventDispatcher){

            }
        )

        subscribePipeline("TEST", DefaultPipelineObserver{

        })






        on(editorPane, E.KeyRelease) {
            val e = it as KeyEvent
            if (e.keyCode == controlKeyCode) {
                isControlPress = false
            }
        }
        on(editorPane, E.MouseWheelMoved) {
            if (!isControlPress) return@on
            val e = it as MouseWheelEvent
            val newSize = editorPane.font.size - e.wheelRotation
            val newFont = editorPane.font.deriveFont(newSize.toFloat())
            editorPane.font = newFont

        }


        on(editorPane, E.TextInput) {
            it as DocumentEvent
            val text = it.document.getText(0, it.document.length)
            println(text)
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        EventQueue.invokeLater {
            on(TestMainFrame, E.MouseDrag) {
                println("Drag")
            }
            launchPipeline()
            TestMainFrame.contentPane = mainPane
            TestMainFrame.isVisible = true
        }

    }
}