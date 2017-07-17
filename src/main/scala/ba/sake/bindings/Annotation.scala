package ba.sake.bindings

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.name.Named
import com.google.inject.name.Names

object AnnotationApp {

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new AppModule())

    injector.getInstance(classOf[ServiceAnnotated])
    injector.getInstance(classOf[ServiceNamed])
    injector.getInstance(classOf[Service])
  }

  /* Guice bindings */
  // annotation called Special is defined in the JAVA PACKAGE of project
  class AppModule extends AbstractModule {
    override def configure() {
      bind(classOf[MyInterface]).annotatedWith(classOf[Special]).to(classOf[MyImplSpecial])
      bind(classOf[MyInterface]).annotatedWith(Names.named("Bla")).to(classOf[MyImplNamed])
      bind(classOf[MyInterface]).to(classOf[MyImpl])
    }
  }

  /* Our traits and classes */
  trait MyInterface {
    def myMethod: Unit
  }

  /* MyInterface IMPLEMENTATIONS! */
  class MyImplSpecial extends MyInterface {
    override def myMethod: Unit = println("I AM THE @Special ONE!!!")
  }

  class MyImplNamed extends MyInterface {
    override def myMethod: Unit = println("I AM THE @Named ONE!")
  }

  class MyImpl extends MyInterface {
    override def myMethod: Unit = println("I AM THE ... regular .. one ...")
  }

  /* MyInterface USERS! */
  class ServiceAnnotated @Inject() (@Special myInterface: MyInterface) {
    myInterface.myMethod
  }

  class ServiceNamed @Inject() (@Named("Bla") myInterface: MyInterface) {
    myInterface.myMethod
  }

  class Service @Inject() (myInterface: MyInterface) {
    myInterface.myMethod
  }

}
