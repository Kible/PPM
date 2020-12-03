package ProjectManager

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage


class Main extends Application {



   override def start(stage1: Stage): Unit = {
     stage1.setTitle("Project Manager: Login")
     val fxmlLoader =
       new FXMLLoader(getClass.getResource("LoginController.fxml"))
     val mainViewRoot: Parent = fxmlLoader.load()
     val scene = new Scene(mainViewRoot)
     stage1.setScene(scene)
     stage1.show()
   }

  def mainStart(stage2 : Stage): Unit = {
    val stage2 = new Stage()
    stage2.setTitle("Project Manager")
    val fxmlLoader =
      new FXMLLoader(getClass.getResource("MainController.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    stage2.setScene(scene)
    stage2.show()
    loginButton.getScene().getWindow.hide()
  }
}

object FxApp extends App{

  var dbUser = new UserDB(List())
  var dbProjeto = new ProjetoDB(List())


  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)


  }
}