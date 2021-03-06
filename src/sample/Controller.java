package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {

    public HTMLEditor htmledit;
    public Button saveHTMLfile;
    public Button loadToPaneWeb;
    public Button getCodeWeb;
    public Button loadFileEditor;
    public Button webViewPanelLoadFile;
    public TextArea textAreaPAnel;
    public WebView webView;
    public Pane pane;

    public WebEngine TEXT_TEST;

    public void initialize() throws IOException {
        String fileName = "./src/"+ this.getClass().getPackage().getName().replaceAll(".","/") + "/htmlTEST/htmlTEST.html";
        TEXT_TEST = webView.getEngine();
    String INITIAL_TEXT = readUsingFiles(fileName);
        htmledit.setHtmlText(INITIAL_TEXT);
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void WebEng(ActionEvent event) {
        TEXT_TEST.loadContent(htmledit.getHtmlText());
    }

    public void getCodeWeb(ActionEvent event){
        textAreaPAnel.setWrapText(true);
        textAreaPAnel.setText(htmledit.getHtmlText());
    }

    public void SaveHtml(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());

        if(file != null){
            SaveFile(htmledit.getHtmlText(), file);
        }
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadHtml(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html", "*.html"));
        File loadImageFile = fileChooser.showOpenDialog(pane.getScene().getWindow());
        URL url = loadImageFile.toURI().toURL();
        TEXT_TEST.load(url.toString());
    }

    public void LoadFileText(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html", "*.html"));
        File loadImageFile = fileChooser.showOpenDialog(pane.getScene().getWindow());
        try (BufferedReader reader = new BufferedReader(new FileReader(loadImageFile))) {
            String line;
            while ((line = reader.readLine()) != null)
            htmledit.setHtmlText(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}