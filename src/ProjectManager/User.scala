package ProjectManager

object User {

  def createUser(nome: String, pass: String): User = {
    val usr = new User(nome, pass)
    usr
  }

  def changePass(user: User, pass: String): Unit = {
    user.pw = pass
  }

  def goodPass(pass: String): Boolean = {
    if(pass.length < 5) false
    else {
      if (pass.forall(_.isLetter) || pass.forall(_.isDigit) || pass.forall(_.isLetterOrDigit)) false
      else true
    }
  }




}

case class User(id: String, var pw: String){

  def createUser(nome: String, pass: String): User = User.createUser(nome, pass)

  def changePass(pass: String): Unit = User.changePass(this, pass)



}
