package ProjectManager

import java.util.Date


object Tarefa {
  private var idNum = 0

  private def newID(): Int = {idNum += 1; idNum}

  def create(): Tarefa = {
    //admin
    new Tarefa("", List(), false, null,null)
  }

  def changeDesc(task: Tarefa, desc: String): Tarefa = {
    new Tarefa(desc, task.doneBy, task.done, task.deadLine, task.finish)
  }

  def addUser(task : Tarefa, user: User): Tarefa = {
    //admin
    val newList = task.doneBy ++ List(user)
    new Tarefa(task.description, newList, task.done, task.deadLine, task.finish)
  }

  def setDone(task: Tarefa): Tarefa = {
    val data = new Date()
    new Tarefa(task.description, task.doneBy, true, task.deadLine, data)
  }

  def setDeadLine(task: Tarefa, data: Date): Tarefa = {
    //admin
    new Tarefa(task.description, task.doneBy, true, data, task.finish)
  }

  def participation(task: Tarefa, user: User): List[String] = {
    val start = System.currentTimeMillis().toInt
    var isCounting = true
    /*while (isCounting) {
      //until button stop
    }*/
    val finish = System.currentTimeMillis().toInt - start

    def search(l: List[String]): List[String] = {
      l match {
        case Nil => l ++ List(user.id + "/" + finish)
        case x :: xs => if (x.split("/").head.toInt == user.id) (user.id + "/" + (x.split("/").tail.head + finish)) :: search(xs) else x :: search(xs)
      }
    }
    search(task.pList)
  }

  def updatePart(task: Tarefa, partList: List[String]): Unit = {
    task.pList = partList
  }

}

case class Tarefa(description: String, doneBy: List[User], done: Boolean, deadLine : Date, finish: Date){
  val id : Int = Tarefa.newID()
  var pList: List[String] = List("")


  def create(): Tarefa = Tarefa.create()

  def changeDesc(desc: String): Tarefa = Tarefa.changeDesc(this, desc)

  def addUser(user: User): Tarefa = Tarefa.addUser(this, user)

  def setDone(): Tarefa = Tarefa.setDone(this)

  def setDeadLine(data: Date): Tarefa = Tarefa.setDeadLine(this, data)

  def participation(user: User): List[String] = Tarefa.participation(this, user)

  def updatePart(partList: List[String]): Unit = Tarefa.updatePart(this, partList)

}
