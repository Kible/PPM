package ProjectManager

import scala.annotation.tailrec

object UserDB {


  def addUser(db: UserDB, user: User): UserDB= {
    search(db.users, user.id) match {
      case None => new UserDB(db.users ++ List(user))
      case Some(_) => println("User already on the list"); db
    }
  }

  def removeUser(db: UserDB, user: User): UserDB = {
    val newDB = db.users.filter(x => !(x.id == user.id))
    new UserDB(newDB)
  }

  def authenticate(db:UserDB, user: (String, String)): Boolean= {
    val usr = search(db.users, user._1)
     usr match {
       case None => false
       case Some(x) => if(x.pw == user._2) true else{ println("Password incorreta"); false}
    }
  }

  @tailrec
  def search(l: List[User], userID: String): Option[User] ={
    l match {
      case Nil => None
      //case x::Nil => if(x.id == userID) Some(x) else None
      case x::xs => if(x.id == userID) Some(x) else search(xs, userID)
    }
  }





}

case class UserDB(users: List[User]){

  def addUser(user: User): UserDB = UserDB.addUser(this, user)

  def removeUser(user: User): UserDB = UserDB.removeUser(this, user)

  def authenticate(user: (String, String)): Boolean = UserDB.authenticate(this, user)

  def search(l: List[User], userID: String): Option[User] = UserDB.search(l, userID)
}
