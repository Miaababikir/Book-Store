package sample.MemberList;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MemberListController implements Initializable {

    private ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Member> table;

    @FXML
    private TableColumn<Member, String> idCol;

    @FXML
    private TableColumn<Member, String> nameCol;

    @FXML
    private TableColumn<Member, String> phoneCol;

    @FXML
    private TableColumn<Member, String> emailCol;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        inicCol();
        loadData();
    }

    private void inicCol()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }



    private void loadData()
    {
        DatabaseHandler databaseHandler = DatabaseHandler.getInctance();
        String qu = "SELECT * FROM MEMBER";
        ResultSet resultSet = databaseHandler.execQuery(qu);

        try {
            while (resultSet.next())
            {
                String namex = resultSet.getString("name");
                String idx = resultSet.getString("id");
                String phonex = resultSet.getString("phone");
                String emailx = resultSet.getString("email");

                list.add(new Member(namex, idx, phonex, emailx));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.getItems().setAll(list);

    }


    public class Member {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;

        public Member(String name, String id, String phone, String email)
        {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);

        }

        public String getName()
        {
            return name.get();
        }

        public String getId()
        {
            return id.get();
        }

        public String getPhone()
        {
            return phone.get();
        }

        public String getEmail()
        {
            return email.get();
        }


    }

}
