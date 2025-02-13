package example.controller;

import example.dao.BookDAO;
import example.mybatis.MybatisSessionFactory;
import example.vo.BookVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.apache.ibatis.session.SqlSessionFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    //DAO
    SqlSessionFactory factory = MybatisSessionFactory.getSqlSessionFactory();

    BookDAO dao = new BookDAO(factory);

    @FXML private TableView<BookVO> tableView;
    @FXML private TableColumn<BookVO, String> isbnCol;
    @FXML private TableColumn<BookVO, String> titleCol;
    @FXML private TableColumn<BookVO, Integer> priceCol;
    @FXML private TableColumn<BookVO, String> authorCol;
    @FXML private TextField searchField;
    @FXML private Button searchBtn, insertBtn, deleteBtn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 테이블 컬럼과 Book 객체의 속성을 매핑
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("btitle"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("bprice"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("bauthor"));

        searchBtn.setOnAction(event -> loadBooks());
        insertBtn.setOnAction(event -> handleInsert());
        deleteBtn.setOnAction(event -> handleDelete());

        // 테이블에서 더블클릭 시 수정 기능 호출
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // 더블클릭
                handleEditButtonAction();
            }
        });

        loadBooks();

    }

    private void loadBooks() {
        List<BookVO> bookList = dao.selectByKeyword(searchField.getText());  // ArrayList<BookVO> 반환
        ObservableList<BookVO> books = FXCollections.observableArrayList(bookList);  // 변환
        tableView.setItems(books);
    }

    @FXML
    private void handleInsert() {
        Dialog<BookVO> dialog = new Dialog<>();
        dialog.setTitle("새 책 추가");
        dialog.setHeaderText("새 책 정보를 입력하세요.");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField isbnField = new TextField();
        TextField titleField = new TextField();
        TextField priceField = new TextField();
        TextField authorField = new TextField();
        grid.add(new Label("ISBN:"), 0, 0); grid.add(isbnField, 1, 0);
        grid.add(new Label("책 제목:"), 0, 1); grid.add(titleField, 1, 1);
        grid.add(new Label("가격:"), 0, 2); grid.add(priceField, 1, 2);
        grid.add(new Label("저자:"), 0, 3); grid.add(authorField, 1, 3);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    return new BookVO(
                            isbnField.getText(),
                            titleField.getText(),
                            Integer.parseInt(priceField.getText()),
                            authorField.getText()
                    );
                } catch (NumberFormatException e) {
                    showAlert("입력 오류", "가격은 숫자로 입력해야 합니다.");
                }
            }
            return null;
        });

        Optional<BookVO> result = dialog.showAndWait();
        result.ifPresent(book -> {
            dao.insertBook(book);
            loadBooks();
        });
    }

    // 수정 버튼 클릭 시 책 수정 다이얼로그 열기
    @FXML
    private void handleEditButtonAction() {
        BookVO selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            // 책 정보를 수정하는 다이얼로그 생성
            Dialog<BookVO> dialog = new Dialog<>();
            dialog.setTitle("책 정보 수정");
            dialog.setHeaderText("책 정보를 수정하세요.");

            // 다이얼로그 버튼 설정
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // 다이얼로그 내용 설정
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField isbnField = new TextField(selectedBook.getBisbn());
            TextField titleField = new TextField(selectedBook.getBtitle());
            TextField priceField = new TextField(String.valueOf(selectedBook.getBprice()));
            TextField authorField = new TextField(selectedBook.getBauthor());

            grid.add(new Label("ISBN:"), 0, 0);
            grid.add(isbnField, 1, 0);
            grid.add(new Label("책 제목:"), 0, 1);
            grid.add(titleField, 1, 1);
            grid.add(new Label("책 가격:"), 0, 2);
            grid.add(priceField, 1, 2);
            grid.add(new Label("책 저자:"), 0, 3);
            grid.add(authorField, 1, 3);

            dialog.getDialogPane().setContent(grid);

            // 다이얼로그 결과 처리
            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    return new BookVO(
                            isbnField.getText(),
                            titleField.getText(),
                            Integer.parseInt(priceField.getText()),
                            authorField.getText()
                    );
                }
                return null;
            });

            Optional<BookVO> result = dialog.showAndWait();
            result.ifPresent(updatedBook -> {
                dao.updateBook(updatedBook);
                loadBooks();  // 수정 후 테이블 갱신
            });
        }
    }

    @FXML
    private void handleDelete() {
        // 선택된 항목 가져오기
        BookVO selectedBook = tableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // 삭제 확인 대화상자
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제 확인");
            alert.setHeaderText("선택한 책을 삭제하시겠습니까?");
            alert.setContentText("책 제목: " + selectedBook.getBtitle());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // 삭제 처리
                dao.deleteBook(selectedBook.getBisbn());  // ISBN을 이용하여 삭제
                loadBooks();  // 테이블 갱신
            }
        } else {
            showAlert("선택 오류", "삭제할 책을 선택하세요.");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
