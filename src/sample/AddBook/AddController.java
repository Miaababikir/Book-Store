package sample.AddBook;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.net.httpserver.Authenticator;
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

public class AddController implements Initializable {

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField bookID;

    @FXML
    private JFXTextField aouther;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;

    @FXML
    private AnchorPane rootPane;


    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        databaseHandler = DatabaseHandler.getInctance();
        checkData();

    }

    @FXML
    void addBook(ActionEvent event)
    {

        String BookTitle = title.getText();
        String BookID = bookID.getText();
        String Bookaouther = aouther.getText();
        String Bookpublisher = publisher.getText();

        if(BookID.isEmpty() || Bookaouther.isEmpty() || Bookpublisher.isEmpty() || BookTitle.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Enter All Fialds");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO BOOK VALUES ("
                + "'" + BookID + "',"
                + "'" + BookTitle + "',"
                + "'" + Bookaouther + "',"
                + "'" + Bookpublisher + "',"
                + "'" + true + "'"
                + ")";

        if(databaseHandler.execAction(qu))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
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
        String qu = "SELECT title FROM BOOK";
        ResultSet resultSet = databaseHandler.execQuery(qu);

        try {
            while (resultSet.next())
            {
                String titlex = resultSet.getString("title");
                System.out.print(titlex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








}
