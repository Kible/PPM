package ProjectManager

import javafx.fxml.FXML
import javafx.scene.chart.{BarChart, XYChart}
import javafx.scene.control._

import java.lang.reflect.InvocationTargetException


class MainController {



  @FXML
  private var searchText: TextField = _

  @FXML
  private var searchButton: Button = _

  @FXML
  private var projectList: ListView[String] = _

  @FXML
  private var taskList: ListView[String] = _

  @FXML
  private var infoArea: TextArea = _

  @FXML
  private var textArea: TextArea = _

  @FXML
  private var graphArea: BarChart [String,Int]= _

  @FXML
  private var nameProjectText: TextField = _

  @FXML
  private var newProjectButton: Button = _

  @FXML
  private var projNameButton: Button = _

  @FXML
  private var projectSetDoneButton: Button = _

  @FXML
  private var deleteProjectButton: Button = _

  @FXML
  private var nameTaskText: TextField = _

  @FXML
  private var descTaskText: TextField = _

  @FXML
  private var newTaskButton: Button = _

  @FXML
  private var newTaskButton2: Button = _

  @FXML
  private var taskNameButton: Button = _

  @FXML
  private var changeDescButton: Button = _

  @FXML
  private var addNoteButton: Button = _

  @FXML
  private var deadLineDate: DatePicker = _

  @FXML
  private var setDeadLineButton: Button = _

  @FXML
  private var graphButton: Button = _

  @FXML
  private var deleteTaskButton: Button = _

  @FXML
  private var clearCampsButtonP: Button = _

  @FXML
  private var clearCampsButtonT: Button = _

  @FXML
  private var startTimerButton: Button = _

  @FXML
  private var stopTimerButton: Button = _

  @FXML
  private var setTaskDoneButton: Button = _

  @FXML
  private var startTimerButton2: Button = _

  @FXML
  private var stopTimerButton2: Button = _

  @FXML
  private var setTaskDoneButton2: Button = _

  def search(): Unit = {
    val l = FxApp.dbProjeto.searchList(searchText.getText())
    editSearch(l)
  }

  def newProject(): Unit = {
    FxApp.dbProjeto = FxApp.dbProjeto.insert(Projeto.create(nameProjectText.getText()))
    editProjects()
  }

  def changeProjectName(): Unit = {
    val p = getSelectedProject
    if(p.isDefined){
      val x = p.get.changeName(nameProjectText.getText())
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, x)
      editProjects()
    }
  }

  def setProjectDone(): Unit = {
    val p = getSelectedProject
    if(p.isDefined)
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.setDone())
  }

  def deleteProject(): Unit = {
    val p = getSelectedProject
    if(p.isDefined) {
      FxApp.dbProjeto = FxApp.dbProjeto.remove(p.get)
      editProjects()
      editTasks()
    }
  }

  def newTask(): Unit = {
    val p = getSelectedProject
    FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.addTask(Tarefa.create(nameTaskText.getText())))
    editTasks()
    clearCamps()
  }

  def changeTaskName(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined) {
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.updateTaskList(t.get, t.get.changeName(nameTaskText.getText())))
      editTasks()
      clearCamps()
    }
  }

  def changeDescription(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined) {
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.updateTaskList(t.get,t.get.changeDesc(descTaskText.getText())))
      taskSelected()
      clearCamps()
    }
  }

  def addNote(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined && !(textArea.getText() == "")) {
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.updateTaskList(t.get, t.get.addNote(FxApp.user, textArea.getText())))
      taskSelected()
      clearCamps()
    }
  }

  def setDeadLine(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined) {
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.updateTaskList(t.get, t.get.setDeadLine(deadLineDate.getEditor.getText())))
      taskSelected()
      clearCamps()
    }
  }

  def setTaskDone(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined) {
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.updateTaskList(t.get, t.get.setDone()))
      taskSelected()
    }
  }

  def doGraphic(): Unit = {
    val proj = getSelectedProject
    if(proj.isDefined){
      graphArea.getData.clear()
      val data = new XYChart.Series[String, Int]()
      if(getSelectedTask.isDefined) {
        getSelectedTask.get.pList.foreach(x => data.getData.add(new XYChart.Data(x._1, x._2.toInt)))
      }else{
       // proj.get.getParticipacaoTotal.foreach(y => data.getData.add(new XYChart.Data(y._1, y._2.toInt)))
        proj.get.getPartSum.foreach(y => data.getData.add(new XYChart.Data(y._1, y._2.toInt)))
      }
      graphArea.getData.add(data)
      graphArea.setVisible(true)
    }
  }

  def deleteTask(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined) {
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.removeTask(t.get))
      editTasks()
    }
  }

  def clearCamps(): Unit = {
    textArea.setText("")
    searchText.setText("")
    nameProjectText.setText("")
    nameTaskText.setText("")
    descTaskText.setText("")
    deadLineDate.getEditor.setText("")
    graphArea.setVisible(false)
  }

  def startTimer(): Unit = {
    if(getSelectedTask.isDefined)
    FxApp.timer = FxApp.startTimer()
  }

  def stopTimer(): Unit = {
    val p = getSelectedProject
    val t = getSelectedTask
    if(t.isDefined) {
      val dedicatedTime = FxApp.stopTimer(FxApp.timer)
      FxApp.dbProjeto = FxApp.dbProjeto.update(p.get, p.get.updateTaskList(t.get, t.get.addParticipation(FxApp.user, dedicatedTime)))
      taskSelected()
    }
  }

  def editSearch(l: List[String]): Unit = {
    projectList.getItems.clear()
    l.foreach(projectList.getItems.add(_))
    projectList.refresh()
  }

  def getSelectedProject: Option[Projeto] = {
    FxApp.dbProjeto.search(projectList.getSelectionModel.getSelectedItem)
  }

  def getSelectedTask: Option[Tarefa] = {
    getSelectedProject.get.searchTask(taskList.getSelectionModel.getSelectedItem)
  }

  def editProjects(): Unit = {
    projectList.getItems.clear()
    FxApp.dbProjeto.getNames.foreach(projectList.getItems.add(_))
    projectList.refresh()
  }

  def editTasks(): Unit = {
    taskList.getItems.clear()
    val p = getSelectedProject
    p.get.getTaskList.foreach(taskList.getItems.add(_))
    taskList.refresh()
  }

  def projectSelected(): Unit ={
    try {
      val p = getSelectedProject.get
      infoArea.setText("Name: " + p.name + System.lineSeparator()
        + "Admin: " + p.admin + System.lineSeparator()
        + "Done: " + p.done + System.lineSeparator()
        + "End Date: " + p.finished)
      editTasks()
    }catch{ case e: InvocationTargetException => editTasks()
            case e: NoSuchElementException => editTasks()}
    graphArea.setVisible(false)
    if(FxApp.user == getSelectedProject.get.admin){
      deleteProjectButton.setVisible(true)
      projNameButton.setVisible(true)
      projectSetDoneButton.setVisible(true)
      newTaskButton.setVisible(true)
      taskNameButton.setVisible(true)
      descTaskText.setVisible(true)
      startTimerButton.setVisible(true)
      startTimerButton2.setVisible(false)
      stopTimerButton.setVisible(true)
      stopTimerButton2.setVisible(false)
      setTaskDoneButton.setVisible(true)
      setTaskDoneButton2.setVisible(false)
      changeDescButton.setVisible(true)
      deadLineDate.setVisible(true)
      setDeadLineButton.setVisible(true)
      deleteTaskButton.setVisible(true)
      nameTaskText.setVisible(true)
    }else{
      nameTaskText.setVisible(false)
      deleteProjectButton.setVisible(false)
      projNameButton.setVisible(false)
      projectSetDoneButton.setVisible(false)
      newTaskButton.setVisible(false)
      taskNameButton.setVisible(false)
      descTaskText.setVisible(false)
      changeDescButton.setVisible(false)
      startTimerButton.setVisible(false)
      startTimerButton2.setVisible(true)
      stopTimerButton.setVisible(false)
      stopTimerButton2.setVisible(true)
      setTaskDoneButton.setVisible(false)
      setTaskDoneButton2.setVisible(true)
      deadLineDate.setVisible(false)
      setDeadLineButton.setVisible(false)
      deleteTaskButton.setVisible(false)

    }
  }

  def taskSelected(): Unit ={
    try {
      val t = getSelectedTask.get
      infoArea.setText("Name: " + t.id + System.lineSeparator()
        + "Description: " + System.lineSeparator() + t.description + System.lineSeparator()
        + "DeadLine: " + t.deadLine + System.lineSeparator()
        + "Participations: " + System.lineSeparator() + t.getPList + System.lineSeparator()
        + "Done: " + t.done + System.lineSeparator()
        + "End Date: " + t.finish+ System.lineSeparator()+ System.lineSeparator()
        + "Notes: " + System.lineSeparator() + t.getNoteList)
    }catch{ case e: InvocationTargetException => editTasks()
            case e: NoSuchElementException => editTasks()}
    graphArea.setVisible(false)
  }

/*
  def alertProject(): Alert ={
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Project not selected alert")
    alert.setHeaderText("Attention")
    alert.setContentText("You have to select a project first!")
    alert
  }

  def alertTask(): Alert ={
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Task not selected alert")
    alert.setHeaderText("Attention")
    alert.setContentText("You have to select a task first!")
    alert
  }*/

}
