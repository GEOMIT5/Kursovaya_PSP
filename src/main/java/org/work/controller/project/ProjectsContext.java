package org.work.controller.project;

import org.work.controller.BaseController;
import org.work.controller.ShowException;
import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.com.factory.CommandFactory;
import org.work.domen.entity.Project;
import org.work.domen.entity.ProjectMarkStatus;
import org.work.domen.entity.Segment;
import org.work.domen.entity.UserStatus;
import org.work.domen.message.Message;
import org.work.domen.utilit.builder.impl.ProjectBuilderImpl;
import org.work.launch.Launcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Arrays;


public class ProjectsContext implements BaseController {

    @FXML
    private ChoiceBox<Segment> choiceSegment = new ChoiceBox<>();


    private static final String[] STATUS_NAMES =
            {"Долгосрочная перспектива",
                    "Среднесрочная перспектива",
                    "Краткосрочная перспектива",
                    "Отстутствие перспективы"};
    private static final ObservableList<String> MARK_STATUSES =
            FXCollections.observableList(
                    Arrays.asList("Долгосрочная перспектива",
                            "Среднесрочная перспектива",
                            "Краткосрочная перспектива",
                            "Отстутствие перспективы"));
    @FXML
    private ChoiceBox<String> choiceMark = new ChoiceBox<>(MARK_STATUSES);

    @FXML
    private TextField textName;
    @FXML
    private TextField textHead;
    @FXML
    private TextField textHumanAmount;
    @FXML
    private TextField textAverageSalary;
    @FXML
    private TextField textProfit;
    @FXML
    private TextField textTerm;
    @FXML
    private TextField textProfitability;


    private Project project;


    public void initialize() {
        textProfitability.setText("0");
        textProfitability.setEditable(false);
        //
        choiceSegment.setItems(getSegments());
        //
        //
        choiceMark.setItems(MARK_STATUSES);
        //
        setPromt();
        initTextListenersForNumber();
        //
        if (Launcher.getUser().getUserStatus().equals(UserStatus.USER)) {
            this.choiceMark.setDisable(true);
        }
        //
        choiceMark.setValue("Отсутствие");
        //
    }

    private void setPromt() {
        textName.setPromptText("введите компанию");
        textHead.setPromptText("ввведите проект-рука");
        textHumanAmount.setPromptText("введите кол-во человек на проекте");
        textAverageSalary.setPromptText("введите среднюю з.п. сотрудников проекта");
        textProfit.setPromptText("введите предполагаемую выручку");
        textTerm.setPromptText("введите продолжительность проекта в часах(прогноз.)");
        textProfitability.setPromptText("");
    }

    private void initTextListenersForNumber() {

        //////
        textHumanAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textHumanAmount.setText(newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
        textAverageSalary.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textAverageSalary.setText(newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
        textProfit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textProfit.setText(
                        newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
        textTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(FOR_MATCH)) {
                textTerm.setText(
                        newValue.replaceAll(FOR_REPLACE, CLEAR_TEXT));
            }
        });
    }

    void setNullEntity() {
        this.project = null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        if (project == null) {
            return;
        }

        //
        this.textName.setText(project.getName());
        this.textHead.setText(project.getHead());
        this.textHumanAmount.setText(String.valueOf(project.getHumanAmount()));
        this.textAverageSalary.setText(String.valueOf(project.getAverageSalary()));
        this.textProfit.setText(String.valueOf(project.getProfit()));
        this.textTerm.setText(String.valueOf(project.getTerm()));
        this.textProfitability.setText(String.valueOf(project.getProfitability()));
        //
        this.choiceSegment.setValue(project.getSegment());
        this.choiceMark.setValue(STATUS_NAMES[project.getStatus().getId() - 1]);

        if (Launcher.getUser().getUserStatus().equals(UserStatus.USER)) {
            this.choiceMark.setDisable(true);
            choiceMark.setValue("отсутствует");
        } else if (Launcher.getUser().getUserStatus().equals(UserStatus.VIEWER)) {
            //
            setProjectEditableFalse();
        }

        if (project.getStatus().getId() != 4) {
            setNotEditableProduct();
        }
    }

    private void setProjectEditableFalse() {
        this.textName.setEditable(false);
        this.textHead.setEditable(false);
        this.textHumanAmount.setEditable(false);
        this.textAverageSalary.setEditable(false);
        this.textProfit.setEditable(false);
        this.textTerm.setEditable(false);
        //
        this.choiceSegment.setDisable(true);
    }

    private void setNotEditableProduct() {
        setProjectEditableFalse();
        this.choiceMark.setDisable(true);
    }

    public void save(ActionEvent actionEvent) {

        String name = textName.getText();
        String head = textHead.getText();
        try {
            double humanAmount = Double.parseDouble(textHumanAmount.getText());
            double averageSalary = Double.parseDouble(textAverageSalary.getText());
            double profit = Double.parseDouble(textProfit.getText());
            double term = Double.parseDouble(textTerm.getText());
            double profitability = (humanAmount * averageSalary * term) /profit ;

            if (humanAmount == 0 || averageSalary == 0 || profit == 0 || term == 0) {
                ShowException.showErrorException("Данные поля не могут равняться нулю!");
            } else {
                if (!name.equals("")
                        && !head.equals("")
                        && !choiceSegment.getSelectionModel().isEmpty()) {


                    Segment segment = choiceSegment.getSelectionModel().getSelectedItem();
                    //
                    if (Launcher.getUser().getUserStatus().equals(UserStatus.VIEWER)
                            && choiceMark.getSelectionModel().isEmpty()) {
                        ShowException.showErrorException("Оцените проект!");
                    } else {
                        //
                        ProjectMarkStatus status;
                        if (Launcher.getUser().getUserStatus().equals(UserStatus.USER) ||
                                choiceMark.getSelectionModel().getSelectedItem().isEmpty()) {
                            status = ProjectMarkStatus.values()[3];
                        } else {
                            status = ProjectMarkStatus.values()[choiceMark.getSelectionModel().getSelectedIndex()];
                        }
                        //
                        if (this.project != null) {
                            project.setName(name);
                            project.setHead(head);
                            project.setSegment(segment);
                            project.setHumanAmount(humanAmount);
                            project.setAverageSalary(averageSalary);
                            project.setProfit(profit);
                            project.setProfitability(profitability);
                            project.setTerm(term);
                            project.setStatus(status);
                        } else {
                            project = new ProjectBuilderImpl()
                                    .withSegment(segment)
                                    .withName(name)
                                    .withHead(head)
                                    .withHumanAmount(humanAmount)
                                    .withAverageSalary(averageSalary)
                                    .withProfit(profit)
                                    .withProfitability(profitability)
                                    .withTerm(term)
                                    .withProjectStatus(status)
                                    .build();
                        }
                        ///
                        actionClose(actionEvent);
                    }
                } else {
                    ShowException.showErrorException("Заполните все поля");
                }
            }
        } catch (NumberFormatException ex) {
            ShowException.showErrorException("Заполните все поля!Точка должна быть одна");
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        makeClearFields();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void makeClearFields() {
        this.textName.clear();
        this.textHead.clear();
        this.textHumanAmount.clear();
        this.textAverageSalary.clear();
        this.textProfit.clear();
        this.textTerm.clear();
        this.textProfitability.clear();
        //
        choiceSegment.getSelectionModel().clearSelection();
        //
        this.textName.setEditable(true);
        this.textHead.setEditable(true);
        this.textHumanAmount.setEditable(true);
        this.textAverageSalary.setEditable(true);
        this.textProfit.setEditable(true);
        this.textTerm.setEditable(true);
        //
        this.choiceSegment.setDisable(false);
        //
        this.choiceMark.setDisable(false);
        if (Launcher.getUser().getUserStatus().equals(UserStatus.USER)) {
            this.choiceMark.setDisable(true);
        }
        this.choiceMark.setValue(STATUS_NAMES[3]);
        //
    }


    //
    private ObservableList<Segment> getSegments() {
        Message response = getMessage(Coms.GET_ALL_SEGMENT);
        return (ObservableList<Segment>) response.getByKey("segments");
    }


    private Message getMessage(Coms action) {
        Com command = CommandFactory.getInstance().createCommand(action);
        Message message = new Message();
        return command.execute(message);
    }


    private static final String CLEAR_TEXT = "";
    private static final String FOR_MATCH = "(?=.)([+-]?(?=[\\d\\.])(\\d*)(\\.(\\d+))?)";
    private static final String FOR_REPLACE = "[^(?=.)([+-]?(?=[\\d\\.])(\\d*)(\\.(\\d+))?)]";





}
