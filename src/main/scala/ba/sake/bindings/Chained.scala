package ba.sake.bindings

import com.google.inject.AbstractModule
import com.google.inject.Guice

/**
 * Bla
 */
object ChainedApp {

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new AppModule())

    val myInterface1 = injector.getInstance(classOf[MyInterface])
    val myInterface2 = injector.getInstance(classOf[MyInterface])

    myInterface1.myMethod
    myInterface2.myMethod
  }

  /* Guice bindings */
  // Now whenever you need a MyInterface
  // Guice will give you a MyImplChained
  class AppModule extends AbstractModule {
    override def configure() {
      bind(classOf[MyInterface]).to(classOf[MyImpl])
      bind(classOf[MyImpl]).to(classOf[MyImplChained])
    }
  }

  /* Our traits and classes */
  trait MyInterface {
    def myMethod: Unit
  }

  class MyImpl extends MyInterface {
    private var c = getMyImplCount
    override def myMethod: Unit = println("MyImpl " + c)
  }

  class MyImplChained extends MyImpl {
    private var c = getMyImplChainedCount
    override def myMethod: Unit = println("MyImplChained " + c)
  }

  /* helper counters */
  var myImplCounter = 0
  def getMyImplCount() = {
    myImplCounter += 1
    myImplCounter
  }

  var myImplChainedCounter = 0
  def getMyImplChainedCount() = {
    myImplChainedCounter += 1
    myImplChainedCounter
  }

}
