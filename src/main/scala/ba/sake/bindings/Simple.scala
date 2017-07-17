package ba.sake.bindings

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Inject

object SimpleApp {

  // This is usually managed by your framework
  // You only need to know how to bind types (trait/interface)
  // to implementations (class)
  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new AppModule())

    val myInterface1 = injector.getInstance(classOf[MyInterface])
    val myInterface2 = injector.getInstance(classOf[MyInterface])
    myInterface1.myMethod
    myInterface2.myMethod

    // Guice knows about Service class (and how to get it's instance)
    // since it has @Inject annotation
    val s = injector.getInstance(classOf[Service])

    // Guice doesn't know how to PROPERLY BUILD an UnknownClass
    //val dontDoThis = injector.getInstance(classOf[UnknownClass])
  }

  /* Guice bindings */
  // Whenever you request a MyInterface from Guice 
  // Guice will give you a MyImpl
  class AppModule extends AbstractModule {
    override def configure() {
      bind(classOf[MyInterface]).to(classOf[MyImpl])
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

  /* Our MyInterface-dependant classes */
  class Service @Inject() (myInterface: MyInterface) {
    myInterface.myMethod
  }

  class UnknownClass(bla: Int)

  /* helper counter to see what the Guice is really doing */
  var myImplCounter = 0
  def getMyImplCount() = {
    myImplCounter += 1
    myImplCounter
  }

}
