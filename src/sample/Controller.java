package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private HBox bookInfo;

    @FXML
    private JFXTextField bookIdInput;

    @FXML
    private Text bookName;

    @FXML
    private Text author;

    @FXML
    private Text avial;

    @FXML
    private HBox memberInfo;

    @FXML
    private JFXTextField meberIdInput;

    @FXML
    private Text memberName;

    @FXML
    private Text memberContact;


    @FXML
    private ListView<String> infoList;

    @FXML
    private JFXTextField bookID;

    DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(memberInfo, 1);

        databaseHandler = DatabaseHandler.getInctance();

    }


    @FXML
    void loadAddBook(ActionEvent event)
    {
        loadWindow("AddBook/AddBookUI.fxml", "Add Book");
    }

    @FXML
    void loadAddMember(ActionEvent event)
    {
        loadWindow("AddMember/AddMemberUI.fxml", "Add Members");
    }

    @FXML
    void loadBookList(ActionEvent event)
    {
        loadWindow("BookList/BookListUI.fxml", "Books");
    }

    @FXML
    void loadMemberList(ActionEvent event)
    {
        loadWindow("MemberList/MemberListUI.fxml", "Members");
    }

    void loadWindow(String loc, String title)
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




    @FXML
    void loadMemberInfo(ActionEvent event)
    {
        clearMemberCache();

        String id = meberIdInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id ='" + id + "'";

        ResultSet rs = databaseHandler.execQuery(qu);

        Boolean flag = false;

        try
        {
            while (rs.next())
            {
                String mName = rs.getString("name");
                String mContact = rs.getString("phone");

                memberName.setText(mName);
                memberContact.setText(mContact);

                flag = true;

            }

            if (!flag)
            {
                memberName.setText("There  is No Such Member");
            }



        } catch (SQLException e) {
        e.printStackTrace();
    }


    }

    @FXML
    void loadBookInfo(ActionEvent event)
    {
        clearBookCache();

        String id = bookIdInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id ='" + id + "'";

        ResultSet rs = databaseHandler.execQuery(qu);

        Boolean flag = false;

        try
        {
            while (rs.next())
            {
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvial");

                bookName.setText(bName);
                author.setText(bAuthor);
                String status = (bStatus)?"Avialable" : "Not Avialable";
                avial.setText(status);

                flag = true;

            }

            if (!flag)
            {
                bookName.setText("There  is No Such Book");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void clearBookCache()
    {
        bookName.setText("");
        author.setText("");
        avial.setText("");

    }

    void clearMemberCache()
    {
        memberName.setText("");
        memberContact.setText("");
    }

    @FXML
    void loadIssueOpration(ActionEvent event)
    {
        String mID = meberIdInput.getText();
        String bID = bookIdInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Opration");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure Wont To Issue " + bookName.getText() + "\n To " + memberName.getText() + "?");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK)
        {
            String str = "INSERT INTO ISSUE(bookID,memberID) VALUES ( "
                    + "'" + bID + "',"
                    + "'" + mID + "')";
            String str2 = "UPDATE BOOK SET isAvial = false WHERE id = '" + bID + "'";

            if (databaseHandler.execAction(str) && databaseHandler.execAction(str2))
            {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Succses");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issue Complete");
                alert1.showAndWait();
            }
            else
            {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Fiald");
                alert2.setHeaderText(null);
                alert2.setContentText("Issue Operation Faild");
                alert2.showAndWait();
            }
        }
        else
        {
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Cancelled");
            alert3.setHeaderText(null);
            alert3.setContentText("Issue Operation Cancelled");
            alert3.showAndWait();
        }

    }

    @FXML
    void loadBookInfo2(ActionEvent event)
    {

        ObservableList<String> list = FXCollections.observableArrayList();

        String id = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = '" + id +"'";
        ResultSet rs = databaseHandler.execQuery(qu);

        try {
            while (rs.next())
            {
                String mBookID = id;
                String mMemeberID = rs.getString("memberID");
                Timestamp mTime = rs.getTimestamp("issueTime");
                int renew = rs.getInt("renewCount");

                list.add("Issue date and time : " + mTime.toGMTString());
                list.add("Renew count : " + renew);

                list.add("Book Informtion : ");
                qu = "SELECT * FROM BOOK WHERE id = '" + mBookID + "'";
                ResultSet resultSet = databaseHandler.execQuery(qu);

                while (resultSet.next())
                {
                    list.add("Book Name : " + resultSet.getString("title"));
                    list.add("Book ID : " + resultSet.getString("id"));
                    list.add("Book Author : " + resultSet.getString("author"));
                    list.add("Book Publisher : " + resultSet.getString("publisher"));


                }

                qu = "SELECT * FROM MEMBER WHERE id = '" + mMemeberID + "'";
                resultSet = databaseHandler.execQuery(qu);

                while (resultSet.next())
                {
                    list.add("Member Name : " + resultSet.getString("name"));
                    list.add("Member ID : " + resultSet.getString("id"));
                    list.add("Member Phone : " + resultSet.getString("phone"));
                    list.add("Member E-mail : " + resultSet.getString("email"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        infoList.getItems().setAll(list);

    }


}
