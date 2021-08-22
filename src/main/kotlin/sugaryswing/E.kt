package sugaryswing



// Swing中的大部分事件都会被此类枚举
enum class E{

    MouseEnter,/* 鼠标进入 */
    MouseExit, /* 鼠标退出 */
    MouseClick, /* 鼠标点击 */
    MousePress, /* 鼠标按下 */
    MouseRelease, /* 鼠标松开 */
    Focus, /* 获取焦点 */
    Blur, /* 失去焦点 */
    KeyPress, /* 键盘按下 */
    KeyRelease, /* 键盘松开 */
    KeyType, /* 键盘按下 */
    ComponentAdded, /* 有组件添加进来时触发的事件 */
    ComponentRemoved, /* 有组件被移除时触发 */
    MouseWheelMoved, /* 鼠标滚轮移动 */
    MouseDrag, /* 鼠标拖拽事件 */
    MouseMove, /* 鼠标移动事件 */
    TextInput, /* 文本输入事件 */
}