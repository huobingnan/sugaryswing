@file: JvmName("JPipeline")

package sugaryswing

import java.awt.AWTEvent
import java.awt.EventQueue
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.function.Consumer


private const val CORE_POOL_SIZE = 10

private val blockingQueue = LinkedBlockingQueue<Runnable>()

private val emitterCache = ConcurrentHashMap<String, PipelineEmitter>()
private val observerCache = ConcurrentHashMap<String, MutableList<PipelineObserver>>()
private lateinit var poolObserverExecutor: ThreadPoolExecutor

enum class Scope {
    EventDispatcher,
    New,
    Default,
    Pool
}

// 通道观察者
interface PipelineObserver {

    fun observeOn(): Scope

    // onData方法体中严禁调用订阅事件发射器的emit方法。
    fun onData(data: Any?)
}


interface PipelineEmitter {

    fun emit(data: Any?)

    fun addObserver(observer: PipelineObserver)
}


class DefaultPipelineEmitter : PipelineEmitter {

    private val observerCollection = Vector<PipelineObserver>()


    override fun emit(data: Any?) {
        for (each in observerCollection) {

            when (each.observeOn()) {
                Scope.Default -> {each.onData(data)}
                Scope.EventDispatcher -> {EventQueue.invokeLater { each.onData(data) }}
                Scope.New -> {
                    Thread {each.onData(data)}.start()
                }
                Scope.Pool -> {
                    // 用的时候再去初始化
                    if (!::poolObserverExecutor.isInitialized) {
                        poolObserverExecutor = ThreadPoolExecutor(CORE_POOL_SIZE, 1000,
                        1000, TimeUnit.MILLISECONDS, blockingQueue)
                    }
                    poolObserverExecutor.execute { each.onData(data) }
                }
            }

        }
    }

    override fun addObserver(observer: PipelineObserver) {
        observerCollection.add(observer)
    }
}

class DefaultPipelineObserver(private val scope: Scope, private val consumer: Consumer<Any?>) : PipelineObserver {


    constructor(consumer: Consumer<Any?>) : this(Scope.Default, consumer)

    override fun observeOn(): Scope {
        return this.scope
    }

    override fun onData(data: Any?) {
        consumer.accept(data)
    }

}

// 创建一个通信Pipeline
// 所有的Pipeline的绑定都是延迟绑定，该函数在调用时并不会立即与订阅的观察者建立关系
// 只有在调用**launchPipeline**函数时才会完成观察者与被观察者之间的订阅关系
fun createPipeline(name: String): PipelineEmitter {
    val res = DefaultPipelineEmitter()
    emitterCache[name] = res
    return res
}

// 订阅一个通信Pipeline
// 调用订阅函数时，会先将观察者处理函数放入缓存中
fun subscribePipeline(name: String, observer: PipelineObserver) {
    if (observerCache.containsKey(name)) {
        observerCache[name]!!.add(observer)
    } else {
        observerCache[name] = mutableListOf(observer)
    }
}

// 启动通信Pipeline
fun launchPipeline() {

    emitterCache.forEach { println(it.key) }
    observerCache.forEach { (name, observers) ->

        if (emitterCache.containsKey(name)) {
            val emitter = emitterCache[name]!!
            observers.forEach { emitter.addObserver(it) }
        } else {
            throw IllegalArgumentException("No such pipeline named $name")
        }
    }
    observerCache.clear() // 清空对observer的引用

}



