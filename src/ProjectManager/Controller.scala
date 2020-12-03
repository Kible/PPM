package ProjectManager

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import javafx.stage.Stage

class Controller {

  @FXML
  private var loginButton: Button = _

  @FXML
  private var signButton: Button = _

  @FXML
  private var logText: TextField = _

  @FXML
  private var logPass: PasswordField = _

  @FXML
  private var signText: TextField = _

  @FXML
  private var signPass: PasswordField = _

  @FXML
  private var logLabel: Label = _

  @FXML
  private var signLabel: Label = _


  def logClicked(): Unit = {
    val id = logText.getText
    val pass = logPass.getText
    val log = UserDB.authenticate(FxApp.dbUser, (id, pass))
    println(log)
    if(log){
    //lança scene principal

    }
    else{
      println("User ou Pass incorreta")
      logLabel.setText("User ou Pass incorreta")
      logLabel.setVisible(true)

    }
  }

  def signClicked(): Unit = {
    val name = signText.getText
    val pw = signPass.getText

    val sign = UserDB.search(FxApp.dbUser.users,name)
    sign match {
      case None => if(User.goodPass(pw)){FxApp.dbUser = UserDB.addUser(FxApp.dbUser, new User(name, pw)); signLabel.setText("Registo com sucesso"); signLabel.setVisible(true)}
                    else{signLabel.setText("Password must have more than 5 characters. Letters, numbers and symbols"); signLabel.setVisible(true)}
      case Some(_) => signLabel.setText("User ID utilizado"); signLabel.setVisible(true)
    }
  }


}
