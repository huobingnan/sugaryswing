@file: JvmName("JViewHelper")
package sugaryswing

class JViewHelper {
    companion object {

        @JvmStatic fun install(view: JView) {
            view.createView()
            view.layoutComponent()
        }
    }
}