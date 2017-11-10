package sample.BookList;


import javafx.beans.property.SimpleBooleanProperty;
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

public class BookListController implements Initializable {

    private ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, String> publisherCol;

    @FXML
    private TableColumn<Book, Boolean> availabillity;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        inicCol();
        loadData();
    }


    private void inicCol()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabillity.setCellValueFactory(new PropertyValueFactory<>("avil"));
    }

    /*
    + " id VARCHAR (200) PRIMARY KEY , \n"
                        + " title VARCHAR (200),\n"
                        + " author VARCHAR (200),\n"
                        + " publisher VARCHAR (100),\n"
                        + " isAvial BOOLEAN DEFAULT TRUE "
                        + ")"
     */

    private void loadData()
    {
        DatabaseHandler databaseHandler = DatabaseHandler.getInctance();
        String qu = "SELECT * FROM BOOK";
        ResultSet resultSet = databaseHandler.execQuery(qu);

        try {
            while (resultSet.next())
            {
                String titlex = resultSet.getString("title");
                String idx = resultSet.getString("id");
                String authorx = resultSet.getString("author");
                String publisherx = resultSet.getString("publisher");
                Boolean avialx = resultSet.getBoolean("isAvial");

                list.add(new Book(titlex, idx, authorx, publisherx, avialx));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.getItems().setAll(list);

    }


    public class Book {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty avil;

        public Book(String name, String id, String author, String publisher, Boolean avil)
        {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.avil = new SimpleBooleanProperty(avil);
        }

        public String getName()
        {
            return name.get();
        }

        public String getId()
        {
            return id.get();
        }

        public String getAuthor()
        {
            return author.get();
        }

        public String getPublisher()
        {
            return publisher.get();
        }

        public boolean isAvil()
        {
            return avil.get();
        }

    }










}
