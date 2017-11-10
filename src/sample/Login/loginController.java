package sample.Login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandler;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Mosab on 2/24/2017.
 */
public class loginController implements Initializable {


    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        databaseHandler = DatabaseHandler.getInctance() ;
        fillUser();
    }


    @FXML
    void exit(ActionEvent event)
    {
        System.exit(1);
    }

    @FXML
    void login(ActionEvent event)
    {
        checkUserInfo();
    }


    void checkUserInfo()
    {
        String name = username.getText().toString();
        String pass = password.getText().toString();

        String qu = "SELECT * FROM USERS";

        ResultSet resultSet = databaseHandler.execQuery(qu);

        try {

                while (resultSet.next())
                {
                    if (name.equals(resultSet.getString("userID")))
                    if (pass.equals(resultSet.getString("userPassword")))
                    {
                        System.out.print("Login!");
                        load("/sample/sample2.fxml", "Main");
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong username or password");
                        alert.showAndWait();
                    }
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }

    }





    void load(String loc, String title)
    {

        try {
            Parent root= FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void fillUser()
    {
        String qu = "INSERT INTO USERS VALUES ("
                + "'reel','123')";

        databaseHandler.execQuery(qu);
    }


}
