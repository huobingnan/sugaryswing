package test

import sugaryswing.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.event.MouseEvent
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSplitPane
import javax.swing.JTextArea
import javax.swing.event.DocumentEvent

object TestPipeline {

    private val emitter = createPipeline("textarea.sync")
    private val logEmitter = createPipeline("textarea.log")

    private val pane = JPanel().apply {
        layout = BorderLayout()
        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT)

        val leftTextArea = JTextArea().apply { size = Dimension(300, 600) }

        val rightTextArea = JTextArea().apply { size = Dimension(300, 600) }


        splitPane.leftComponent = JScrollPane(leftTextArea)
        splitPane.rightComponent = JScrollPane(rightTextArea)

        size = TestMainFrame.size
        preferredSize = TestMainFrame.preferredSize
        splitPane.dividerLocation = 50

        on(leftTextArea, E.TextInput) {
            it as DocumentEvent
            logEmitter.emit(it.document.getText(0, it.document.length))
        }

        on(leftTextArea, E.MouseClick) {
            it as MouseEvent
            if (it.clickCount == 2 && it.button == MouseEvent.BUTTON1) {
                emitter.emit((it.source as JTextArea).text)
            }
        }

        val client = HttpClient.newHttpClient()

        val requestObserver = DefaultPipelineObserver(Scope.Pool) {
            val tname = Thread.currentThread().name
            rightTextArea.text = "request => ${it.toString()}, thread => $tname"
            Thread.sleep(1000)
            val req =
                client.send(HttpRequest.newBuilder(URI(it.toString())).build(), HttpResponse.BodyHandlers.ofString())

            rightTextArea.text = req.body()
        }

        val logObserver = DefaultPipelineObserver(Scope.Pool) {
            Thread.sleep(1000)
            println(it.toString())
        }



        subscribePipeline("textarea.log", logObserver)

        subscribePipeline("textarea.sync", requestObserver)

        add(splitPane, BorderLayout.CENTER)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        launchPipeline()
        EventQueue.invokeLater {
            TestMainFrame.contentPane.layout = BorderLayout()
            TestMainFrame.contentPane.add(pane, BorderLayout.CENTER)
            TestMainFrame.pack()
            TestMainFrame.isVisible = true
        }
    }
}