package ba.sake.bindings

import com.google.inject.AbstractModule
import com.google.inject.Guice

object InstanceApp {

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new AppModule())

    val myInterface1 = injector.getInstance(classOf[MyInterface])
    val myInterface2 = injector.getInstance(classOf[MyInterface])

    myInterface1.myMethod
    myInterface2.myMethod

    val magicString = injector.getInstance(classOf[String])
    println(magicString)
  }

  /* Guice bindings */
  // Now whenever you need a MyInterface
  // Guice will always give you the same INSTANCE of MyImpl
  class AppModule extends AbstractModule {
    override def configure() {

      val myInstance = new MyImpl
      bind(classOf[MyInterface]).toInstance(myInstance)

      // not very smart to do in real life, just for demonstration purposes
      // there are better ways to achieve this goal
      // @see Named and BindingAnottation
      bind(classOf[String]).toInstance("Magic $tring")
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

  /* helper counters */
  var myImplCounter = 0
  def getMyImplCount() = {
    myImplCounter += 1
    myImplCounter
  }

}
