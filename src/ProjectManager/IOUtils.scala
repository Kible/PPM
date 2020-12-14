package ProjectManager


import java.io.{EOFException, FileInputStream, FileNotFoundException, FileOutputStream, ObjectInputStream, ObjectOutputStream}

object IOUtils {

  val usersFile: String = System.getProperty("user.dir") + "/PPM_DB/UserList.txt"

  val projectFile: String = System.getProperty("user.dir") + "/PPM_DB/ProjetosList.txt"


  def saveUsers(): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(usersFile))
    oos.writeObject(FxApp.dbUser)
    oos.close()
  }

  def loadUsers(): UserDB = {
      try {
        val ois = new ObjectInputStream(new FileInputStream(usersFile))
        val usr = ois.readObject().asInstanceOf[UserDB]
        ois.close()
        usr
      }
    catch {case e :FileNotFoundException => new UserDB(List())
    case e: EOFException => new UserDB(List())
    case _ => new UserDB(List())
    }
  }

  def saveProject(): Unit= {
    val oos2 = new ObjectOutputStream(new FileOutputStream(projectFile))
    oos2.writeObject(FxApp.dbProjeto)
    oos2.close()
  }

  def loadProject(): ProjetoDB = {
    try {
      val ois = new ObjectInputStream(new FileInputStream(projectFile))
      val p = ois.readObject().asInstanceOf[ProjetoDB]
      ois.close()
      p
    }
    catch {case e: FileNotFoundException => new ProjetoDB(List())
    case e: EOFException => new ProjetoDB(List())
    case _ => new ProjetoDB(List())
    }
  }
}