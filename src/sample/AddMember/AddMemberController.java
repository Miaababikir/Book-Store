package sample.AddMember;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;

    DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        databaseHandler = DatabaseHandler.getInctance();
        checkData();
    }

    @FXML
    void addMember(ActionEvent event)
    {

        String memberName = name.getText();
        String memberId = id.getText();
        String memberPhone = phone.getText();
        String memberEmail = email.getText();

        if(memberName.isEmpty() || memberId.isEmpty() || memberPhone.isEmpty() || memberEmail.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Try Enter All feilds");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO MEMBER VALUES ("
                + "'" + memberId + "',"
                + "'" + memberName + "',"
                + "'" + memberPhone + "',"
                + "'" + memberEmail + "'"
                + ")";

        if(databaseHandler.execAction(qu))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Add Success");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Feild");
            alert.showAndWait();
        }

    }

    @FXML
    void cancel(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void checkData()
    {
        String qu = "SELECT name FROM MEMBER";
        ResultSet resultSet = databaseHandler.execQuery(qu);

        try {
            while (resultSet.next())
            {
                String titlex = resultSet.getString("name");
                System.out.print(titlex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
