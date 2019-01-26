
package movieapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ViewController implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    SplitPane mainSplit;
    @FXML
    StackPane menuPane;
    @FXML
    Pane moviePane;
    @FXML
    Pane exportPane;
    @FXML
    TableView table;
    @FXML
    TextField inputTitle;
    @FXML
    TextField inputLength;
    @FXML
    TextField inputLanguage;
    @FXML
    TextField inputDate;
    @FXML
    TextField inputExportName;
    @FXML
    Button addMovieButton;
    @FXML
    Button exportButton;
    
    DB db = new DB();
    
    private final String MENU_MOVIES = "Filmek";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";

    
    private final ObservableList<Movie> data =
            FXCollections.observableArrayList();
    
    @FXML
    private void addMovie(ActionEvent event) {
        String length = inputLength.getText(); 
        String date = inputDate.getText();
        if ((length.length() < 9 && length.contains(":") && date.length() < 11 && date.contains("-"))
                && inputTitle.getText().length() > 0 && length.length()> 0 && inputLanguage.getText().length() > 0 && date.length() > 0) {
            Movie newMovie = new Movie (inputTitle.getText(), length, inputLanguage.getText(), date);
            data.add(newMovie);
            db.addMovie(newMovie);
            inputTitle.clear();
            inputLength.clear();
            inputLanguage.clear();
            inputDate.clear();
        } else alert("A megadott hossz vagy dátum formátuma nem megfelelő!");
    }
    
    public void setTableData() {
        TableColumn titleCol = new TableColumn("Cím");
        titleCol.setMinWidth(200);
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());          
        titleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        
        titleCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Movie, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Movie, String> t) {
                    Movie actMovie = (Movie) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actMovie.setTitle(t.getNewValue());
                    db.updateMovie(actMovie);
                }
            });
        
        
        TableColumn lengthCol = new TableColumn("Hossz");
        lengthCol.setMinWidth(55);
        lengthCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lengthCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("length"));
        
        lengthCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Movie, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Movie, String> t) {
                    Movie actMovie = (Movie) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actMovie.setLength(t.getNewValue());
                    db.updateMovie(actMovie);
                }
            });
        
        
        TableColumn languageCol = new TableColumn("Nyelv");
        languageCol.setMinWidth(50);
        languageCol.setCellFactory(TextFieldTableCell.forTableColumn());
        languageCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("language"));
        
        languageCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Movie, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Movie, String> t) {
                    Movie actMovie = (Movie) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actMovie.setLanguage(t.getNewValue());
                    db.updateMovie(actMovie);
                }
            });
        
        TableColumn dateCol = new TableColumn("Dátum");
        dateCol.setMinWidth(75);
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("date"));
        
        dateCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Movie, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Movie, String> t) {
                    Movie actMovie = (Movie) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actMovie.setDate(t.getNewValue());
                    db.updateMovie(actMovie);
                }
            });
        
        
        TableColumn removeCol = new TableColumn( "Törlés" );
            removeCol.setMinWidth(100);

            Callback<TableColumn<Movie, String>, TableCell<Movie, String>> cellFactory = 
                    new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>()
                    {
                        @Override
                        public TableCell call( final TableColumn<Movie, String> param )
                        {
                            final TableCell<Movie, String> cell = new TableCell<Movie, String>()
                            {   
                                final Button btn = new Button( "Törlés" );

                                @Override
                                public void updateItem( String item, boolean empty )
                                {
                                    super.updateItem( item, empty );
                                    if ( empty )
                                    {
                                        setGraphic( null );
                                        setText( null );
                                    }
                                    else
                                    {
                                        btn.setOnAction( ( ActionEvent event ) ->
                                                {
                                                    Movie movie = getTableView().getItems().get( getIndex() );
                                                    data.remove(movie);
                                                    db.removeMovie(movie);
                                           } );
                                        setGraphic( btn );
                                        setText( null );
                                    }
                                }
                            };
                            return cell;
                        }
                    };

        removeCol.setCellFactory( cellFactory );
        
        table.getColumns().addAll(titleCol, lengthCol, languageCol, dateCol, removeCol);
        
        
        data.addAll(db.getAllMovies());
        for (int i = 0;i<data.size()-1;i++) {
            for (int j = i+1;j<data.size();j++) {
                if (Integer.parseInt(data.get(i).getDate().substring(0,4)) < Integer.parseInt(data.get(j).getDate().substring(0,4))) {
                    Movie s = data.get(i);
                    data.set(i, data.get(j));
                    data.set(j, s);
                }
            }
        }
        table.setItems(data);
    }
    
    
    public void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_MOVIES);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        
        nodeItemA.setExpanded(true);
        
        Node movieNode = new ImageView(new Image(getClass().getResourceAsStream("/movie2.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, movieNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);
        
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);
        
        menuPane.getChildren().add(treeView);
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                
                if(selectedMenu != null) {
                    switch (selectedMenu) {
                        case MENU_MOVIES:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception e) {
                            }
                        case MENU_LIST:
                            moviePane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            moviePane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }
            }
        });
    }
    
    @FXML
    private void exportList(ActionEvent event) {
        PdfGeneration pdfCreator = new PdfGeneration();
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            pdfCreator.pdfGeneration(fileName, data);
        } else {
            alert("Adj meg egy fájlnevet!");
        }
    }

    public void alert(String msg) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(msg);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
      
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 250.0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
    }    
    
}
