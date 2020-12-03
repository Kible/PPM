package ProjectManager

object Projeto {

  def create(nome: String): Projeto = {
    new Projeto(nome,List(), null, List(),false )
  }

  def changeName(projeto: Projeto, nome: String): Projeto = {
    //só o admin pode mudar o nome
    new Projeto(nome, projeto.tasks, projeto.admin, projeto.authUsers, projeto.done)
  }

  def addUser(projeto: Projeto, user: User): Projeto = {
    //admin
    val userList = projeto.authUsers ++ List(user)
    new Projeto(projeto.name, projeto.tasks,projeto.admin,userList, projeto.done)
  }

  def removeUser(projeto: Projeto, user: User): Projeto = {
    //admin
    new Projeto(projeto.name, projeto.tasks, projeto.admin, projeto.authUsers.filter(x => x.id != user.id), projeto.done)
  }

  def addTask(projeto: Projeto, task: Tarefa): Projeto = {
    //admin
    val index = projeto.tasks.indexWhere(x => x.id == task.id)
    if(index == -1){
      val taskList = projeto.tasks ++ List(task)
      new Projeto(projeto.name, taskList,  projeto.admin, projeto.authUsers, projeto.done)
    }
    else{println("Task already listed"); projeto}
  }

  def removeTask(projeto: Projeto, task: Tarefa): Projeto = {
    //admin
    new Projeto(projeto.name, projeto.tasks.filter(x => x.id != task.id), projeto.admin, projeto.authUsers, projeto.done)
  }

  def updateTask(projeto: Projeto, task: Tarefa): Projeto = {
    val index = projeto.tasks.indexWhere(x => x.id == task.id)
    new Projeto(projeto.name, projeto.tasks.updated(index, task), projeto.admin, projeto.authUsers, projeto.done)
  }


}

case class Projeto(name: String, tasks: List[Tarefa], admin: User, authUsers: List[User], done: Boolean){


  def create(nome: String): Projeto = Projeto.create(nome)

  def changeName(nome: String): Projeto = Projeto.changeName(this, nome)

  def addUser(projeto: Projeto, user: User): Projeto = Projeto.addUser(this, user)

  def removeUser(projeto: Projeto, user: User): Projeto = Projeto.removeUser(this, user)

  def addTask(task: Tarefa): Projeto = Projeto.addTask(this, task)

  def removeTask(task: Tarefa): Projeto = Projeto.removeTask(this, task)

  def updateTask(task: Tarefa): Projeto = Projeto.updateTask(this, task)


}
