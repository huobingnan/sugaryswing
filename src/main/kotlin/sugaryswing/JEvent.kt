@file: JvmName("JEvent")

package sugaryswing

import java.awt.Container
import java.awt.event.*
import java.lang.IllegalArgumentException
import java.util.function.Consumer
import javax.print.Doc
import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.AbstractDocument
import javax.swing.text.Document
import javax.swing.text.JTextComponent

// 用于初始化处理事件函数, 同时也可以用于删除一个事件
val HANDLE_FUNCTION_DO_NOTHING = Consumer<Any> { }

// 事件的安全转换
@SuppressWarnings("unchecked")
fun <EventType> eventSafeCast(eventClass: Class<EventType>, rawEvent: Any): EventType? {
    return if (rawEvent::class.java == eventClass) {
        rawEvent as EventType
    } else {
        null
    }
}

// 鼠标监听器包装类
class MouseListnerWrapper : MouseListener {

    var mouseClickHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        } // 处理鼠标点击事件的函数

    var mouseEnterHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        } // 处理鼠标进入事件的函数

    var mousePressHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        } // 处理鼠标按压事件的函数

    var mouseReleaseHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        } // 鼠标松开事件处理函数

    var mouseExitHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }// 鼠标退出事件处理函数

    override fun mouseClicked(p0: MouseEvent?) {
        mouseClickHandleFunction.accept(p0!!)
    }

    override fun mousePressed(p0: MouseEvent?) {
        mousePressHandleFunction.accept(p0!!)
    }

    override fun mouseReleased(p0: MouseEvent?) {
        mouseReleaseHandleFunction.accept(p0!!)
    }

    override fun mouseEntered(p0: MouseEvent?) {
        mouseEnterHandleFunction.accept(p0!!)
    }

    override fun mouseExited(p0: MouseEvent?) {
        mouseExitHandleFunction.accept(p0!!)
    }
}


// 焦点监听器装饰类
class FocusListenerWrapper : FocusListener {
    var focusGainHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }
    var focusLostHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun focusGained(p0: FocusEvent?) {
        focusGainHandleFunction.accept(p0!!)
    }

    override fun focusLost(p0: FocusEvent?) {
        focusLostHandleFunction.accept(p0!!)
    }
}

// 键盘监听器，装饰类
class KeyListenerWrapper : KeyListener {
    var keyPressHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }
    var keyReleaseHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }
    var keyTypeHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun keyTyped(p0: KeyEvent?) {
        keyTypeHandleFunction.accept(p0!!)
    }

    override fun keyPressed(p0: KeyEvent?) {
        keyPressHandleFunction.accept(p0!!)
    }

    override fun keyReleased(p0: KeyEvent?) {
        keyReleaseHandleFunction.accept(p0!!)
    }

}

// 容器监听器
class ContainerListenerWrapper : ContainerListener {

    var componentAddedHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    var componentRemovedHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun componentAdded(p0: ContainerEvent?) {
        componentAddedHandleFunction.accept(p0!!)
    }

    override fun componentRemoved(p0: ContainerEvent?) {
        componentRemovedHandleFunction.accept(p0!!)
    }
}


// 组件监听器
class ComponentListenerWrapper : ComponentListener {

    var resizeHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    var moveHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    var showHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    var hiddenHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun componentResized(p0: ComponentEvent?) {
        resizeHandleFunction.accept(p0!!)
    }

    override fun componentMoved(p0: ComponentEvent?) {
        moveHandleFunction.accept(p0!!)
    }

    override fun componentShown(p0: ComponentEvent?) {
        showHandleFunction.accept(p0!!)
    }

    override fun componentHidden(p0: ComponentEvent?) {
        hiddenHandleFunction.accept(p0!!)
    }
}


// 鼠标滚轮监听器
class MouseWheelListenerWrapper : MouseWheelListener {

    var moveHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun mouseWheelMoved(p0: MouseWheelEvent?) {
        moveHandleFunction.accept(p0!!)
    }
}

// 鼠标移动监听器
class MouseMotionListenerWrapper : MouseMotionListener {

    var moveHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }


    var dragHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun mouseDragged(p0: MouseEvent?) {
        dragHandleFunction.accept(p0!!)
    }

    override fun mouseMoved(p0: MouseEvent?) {
        moveHandleFunction.accept(p0!!)
    }
}


// 文本监听器
class DocumentListenerWrapper : DocumentListener {

    var textInputHandleFunction: Consumer<Any> = HANDLE_FUNCTION_DO_NOTHING
        set(value) {
            field = value
        }

    override fun insertUpdate(p0: DocumentEvent?) {
        textInputHandleFunction.accept(p0!!)
    }

    override fun removeUpdate(p0: DocumentEvent?) {
        textInputHandleFunction.accept(p0!!)
    }

    override fun changedUpdate(p0: DocumentEvent?) {}
}


@SuppressWarnings("unchecked")
private fun <EventListnerType> findEventListnerOrNull(
    genericArray: Array<out Any>?,
    clazz: Class<EventListnerType>
): EventListnerType? {
    if (genericArray == null) return null

    for (each in genericArray) {
        if (each.javaClass == clazz) {
            return each as EventListnerType
        }
    }
    return null
}



// 事件绑定函数
// 此方法可以渐进式，无侵入的嵌入到你已有的代码中
// 此方法还有许多重载方法，用于绑定不同的组件事件
// 函数重载的使用方法是一致的
// on函数的绑定方式是替换绑定，如果多次为同一事件添加处理函数，那么后面的会覆盖先前绑定的处理函数
// 与之形成对比的是addEventListener函数
fun on(component: Container, event: E, handler: Consumer<Any>) {

    when (event) {
        E.MouseEnter -> {
            // 绑定鼠标进入事件
            var listner = findEventListnerOrNull(component.mouseListeners, MouseListnerWrapper::class.java)
            if (listner == null) {
                listner = MouseListnerWrapper().apply { mouseEnterHandleFunction = handler }
                component.addMouseListener(listner)
            } else {
                listner.mouseEnterHandleFunction = handler
            }
        }

        // 绑定鼠标退出事件
        E.MouseExit -> {
            var listner = findEventListnerOrNull(component.mouseListeners, MouseListnerWrapper::class.java)
            if (listner == null) {
                listner = MouseListnerWrapper().apply { mouseExitHandleFunction = handler }
                component.addMouseListener(listner)
            } else {
                listner.mouseExitHandleFunction = handler
            }
        }

        // 绑定鼠标点击事件
        E.MouseClick -> {
            var listner = findEventListnerOrNull(component.mouseListeners, MouseListnerWrapper::class.java)
            if (listner == null) {
                listner = MouseListnerWrapper().apply { mouseClickHandleFunction = handler }
                component.addMouseListener(listner)
            } else {
                listner.mouseClickHandleFunction = handler
            }
        }

        // 绑定鼠标按下事件
        E.MousePress -> {
            var listner = findEventListnerOrNull(component.mouseListeners, MouseListnerWrapper::class.java)
            if (listner == null) {
                listner = MouseListnerWrapper().apply { mousePressHandleFunction = handler }
                component.addMouseListener(listner)
            } else {
                listner.mouseClickHandleFunction = handler
            }
        }

        // 绑定鼠标松开事件
        E.MouseRelease -> {
            var listner = findEventListnerOrNull(component.mouseListeners, MouseListnerWrapper::class.java)
            if (listner == null) {
                listner = MouseListnerWrapper().apply { mouseReleaseHandleFunction = handler }
                component.addMouseListener(listner)
            } else {
                listner.mouseReleaseHandleFunction = handler
            }
        }

        // 获取焦点
        E.Focus -> {
            var listner = findEventListnerOrNull(component.focusListeners, FocusListenerWrapper::class.java)
            if (listner == null) {
                listner = FocusListenerWrapper().apply { focusGainHandleFunction = handler }
                component.addFocusListener(listner)
            } else {
                listner.focusGainHandleFunction = handler
            }
        }

        // 失去焦点
        E.Blur -> {
            var listner = findEventListnerOrNull(component.focusListeners, FocusListenerWrapper::class.java)
            if (listner == null) {
                listner = FocusListenerWrapper().apply { focusLostHandleFunction = handler }
                component.addFocusListener(listner)
            } else {
                listner.focusLostHandleFunction = handler
            }
        }

        // 键盘按下
        E.KeyPress -> {
            var listener = findEventListnerOrNull(component.keyListeners, KeyListenerWrapper::class.java)
            if (listener == null) {
                listener = KeyListenerWrapper().apply { keyPressHandleFunction = handler }
                component.addKeyListener(listener)
            } else {
                listener.keyPressHandleFunction = handler
            }
        }

        // 键盘松开
        E.KeyRelease -> {
            var listner = findEventListnerOrNull(component.keyListeners, KeyListenerWrapper::class.java)
            if (listner == null) {
                listner = KeyListenerWrapper().apply { keyReleaseHandleFunction = handler }
                component.addKeyListener(listner)
            } else {
                listner.keyReleaseHandleFunction = handler
            }
        }

        // 打字
        E.KeyType -> {
            var listner = findEventListnerOrNull(component.keyListeners, KeyListenerWrapper::class.java)
            if (listner == null) {
                listner = KeyListenerWrapper().apply { keyTypeHandleFunction = handler }
                component.addKeyListener(listner)
            } else {
                listner.keyTypeHandleFunction = handler
            }
        }

        // 有组件被添加进来
        E.ComponentAdded -> {
            var listner = findEventListnerOrNull(component.containerListeners, ContainerListenerWrapper::class.java)
            if (listner == null) {
                listner = ContainerListenerWrapper().apply { componentAddedHandleFunction = handler }
                component.addContainerListener(listner)
            } else {
                listner.componentAddedHandleFunction = handler
            }
        }

        // 有组件被移除
        E.ComponentRemoved -> {
            var listner = findEventListnerOrNull(component.containerListeners, ContainerListenerWrapper::class.java)
            if (listner == null) {
                listner = ContainerListenerWrapper().apply { componentRemovedHandleFunction = handler }
                component.addContainerListener(listner)
            } else {
                listner.componentRemovedHandleFunction = handler
            }
        }

        // 鼠标滚轮移动
        E.MouseWheelMoved -> {
            var listner = findEventListnerOrNull(component.mouseWheelListeners, MouseWheelListenerWrapper::class.java)
            if (listner == null) {
                listner = MouseWheelListenerWrapper().apply { moveHandleFunction = handler }
                component.addMouseWheelListener(listner)
            } else {
                listner.moveHandleFunction = handler
            }
        }

        //鼠标移动
        E.MouseMove -> {
            var listner = findEventListnerOrNull(component.mouseMotionListeners, MouseMotionListenerWrapper::class.java)
            if (listner == null) {
                listner = MouseMotionListenerWrapper().apply { moveHandleFunction = handler }
                component.addMouseMotionListener(listner)
            } else {
                listner.moveHandleFunction = handler
            }
        }

        // 鼠标拖动
        E.MouseDrag -> {
            var listener =
                findEventListnerOrNull(component.mouseMotionListeners, MouseMotionListenerWrapper::class.java)
            if (listener == null) {
                listener = MouseMotionListenerWrapper().apply { dragHandleFunction = handler }
                component.addMouseMotionListener(listener)
            } else {
                listener.dragHandleFunction = handler
            }
        }


        // 文本有输入的时候触发
        E.TextInput -> {
            if (component is JTextComponent) {
                val document = component.document as AbstractDocument
                var listner =
                    findEventListnerOrNull(document.documentListeners, DocumentListenerWrapper::class.java)
                if (listner == null) {
                    listner = DocumentListenerWrapper().apply {
                        textInputHandleFunction = handler
                    }
                    document.addDocumentListener(listner)
                } else {
                    listner.textInputHandleFunction = handler
                }
            } else {
                throw IllegalArgumentException("can't bind an text input event on a non text component")
            }
        }

    } //end-when

}//end-on
