package ProjectManager

import scala.annotation.tailrec


object ProjetoDB {


  def insert(db: ProjetoDB, projeto: Projeto): ProjetoDB = {
    if(search(db,projeto.name) == Nil){
      new ProjetoDB(db.projetos ++ List(projeto))
    }
    else{
      println("Nome usado por outro projeto")
      db
    }
  }

  def remove(db: ProjetoDB, projeto: Projeto): ProjetoDB =
    new ProjetoDB(db.projetos.filter(x => x == projeto))

  def search(db: ProjetoDB, nome: String): List[Projeto] = {
    @tailrec
    def searchList(l: List[Projeto], nome: String): List[Projeto] = {
      l match {
        case Nil => List()
        case x :: xs => if(x.name == nome) List(x)  else searchList(xs, nome)
      }
    }
    searchList(db.projetos, nome)
  }

  def update(db1: ProjetoDB, p1: Projeto, p2: Projeto): ProjetoDB = {
    val index = db1.projetos.indexWhere(x => {x == p1})
    val db2 = new ProjetoDB(db1.projetos.updated(index, p2))
    db2
  }

}

case class ProjetoDB(projetos: List[Projeto]){

  def insert(projeto: Projeto): ProjetoDB = ProjetoDB.insert(this, projeto)

  def remove(projeto: Projeto): ProjetoDB = ProjetoDB.remove(this, projeto)

  def search(nome: String): List[Projeto] = ProjetoDB.search(this, nome)

  def update(p1: Projeto, p2: Projeto): ProjetoDB = ProjetoDB.update(this, p1, p2)
}
