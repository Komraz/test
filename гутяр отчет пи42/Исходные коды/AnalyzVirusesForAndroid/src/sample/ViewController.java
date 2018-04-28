package sample;

import decompile.DecompileExecutor;
import decompile.IDecompile;
import download.DownloadApk;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import proxy.ProxyServer;
import utils.AlertDialogUtils;
import utils.DialogMessage;
import virus.IVirusFileController;
import virus.VirusFileController;
import virus.VirusFileModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ClassAnalizier.AnalizierObserver;
import ClassAnalizier.ProxyFileAnalizier;

/**
 * Класс для обработки пользовательских действий
 */
public class ViewController extends AnalizierObserver implements Initializable, EventHandler<WindowEvent> {

    private static final String VIRUS = "Выберите зараженную apk";
    private static final String ADB = "Выберите файл adb";
    private static final String AAPT = "Выберите aapt файл";
    private static final String EMULATOR = "Выберите файл emulator";
    private static final String PATH_TO_DOWNLOAD = "Укажите папку для сохранения";

    private FileChooser fileChooser = new FileChooser();
    private Alert alert;
    private TreeItem<File> rootItem;
    private double maxProgress,tempProgress;
    private ProxyFileAnalizier analizier=new ProxyFileAnalizier(this);
    private Map<String, String> allMethods=new HashMap<String,String>();
    private String methods;
    private String methodsBegin="digraph G_component_0 {\n" + 
    		"graph [ranksep=1, root=\"main(0)\"];\n" + 
    		"node [fontname=\"Verdana\", size=\"1\"];\n" + 
    		"node [color=grey, style=filled];\n";

    private Stage stage;
    
    @FXML
    private ProgressBar progressAnalize;
    @FXML
    private ImageView graphView;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button showTreeBtn;
    @FXML
    private Button exportGenBtn;

    @FXML
    private TextField pathToFile;

    @FXML
    private TextArea permissionFile;

    @FXML
    private TextField pathEmulator;

    @FXML
    private TextField nameEmulator;

    @FXML
    private TextField pathADB;

    @FXML
    private TextField pathAAPT;

    @FXML
    private TextArea logsArea;

    @FXML
    private Tab decompileTab;

    @FXML
    private Tab fileSystemTab;

    @FXML
    private TreeView<File> decompileTree;

    @FXML
    private TextArea decompileCode;

    @FXML
    private TextArea logProxy;

    @FXML
    private ListView<String> listApk;

    @FXML
    private TextField downloadPath;

    private IVirusFileController virusFileController;

    private IDecompile decompileExecutor;

    private DownloadApk listApkToDownload;

    /*!
     метод для инициализации экземпляра класса VirusFileController и начальной конфигурации приложения
     \param[in] location экземпляр класса URL
     \param[in] resources экземпляр класса ResourceBundle
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        virusFileController = new VirusFileController(new VirusFileModel());
        init();
    }
    @FXML
    public void GraphViewerKeyPressed(KeyEvent event) {
		KeyCode code=event.getCode();
		if(code==KeyCode.ADD)
		{
			graphView.setScaleX(graphView.scaleXProperty().doubleValue()+0.1);
			graphView.setScaleY(graphView.scaleYProperty().doubleValue()+0.1);
		}
		else if(code==KeyCode.SUBTRACT)
		{
			graphView.setScaleX(graphView.scaleXProperty().doubleValue()-0.1);
			graphView.setScaleY(graphView.scaleYProperty().doubleValue()-0.1);
		}
		event.consume();
	}
    /*!
     метод для инициализации начальной конфигурации приложения
    */
    private void init() {
        String pathTo = null;
        pathTo = virusFileController.getPathEmulator();
        if (pathTo != null) {
            pathEmulator.setText(pathTo);
        }
        pathTo = virusFileController.getPathAdb();
        if (pathTo != null) {
            pathADB.setText(pathTo);
        }
        pathTo = virusFileController.getPathAapt();
        if (pathTo != null) {
            pathAAPT.setText(pathTo);
        }
        pathToFile.setText("C:\\Users\\1\\Downloads\\droidbox-master\\droidbox-master\\APIMonitor\\examples\\apkiltests.apk");
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(this);
    }

    @Override
    public void handle(WindowEvent event) {
        System.exit(0);
    }

    /*!
     метод для инициализации экземпляра класса FileChooser
     \param[in] title заголовок окна выбора файла
    */
    private void initFileChooser(String title) {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().clear();
    }

    /*!
     обработчик события по кнопке для выбора вредоноского .apk
    */
    public void chooseFile() throws IOException {
        initFileChooser(VIRUS);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("APK files", "*.apk"));
        File virusFile = fileChooser.showOpenDialog(pathToFile.getScene().getWindow());
        if (virusFile != null) {
            pathToFile.setText(virusFile.getAbsolutePath());
            virusFileController.updateFile(virusFile);
        }
    }

    /*!
     обработчик события по кнопке для выбора файла emulator из Android SDK
    */
    public void chooseEmulator() {
        initFileChooser(EMULATOR);
        String pathEmulator = fileChooser.showOpenDialog(pathToFile.getScene().getWindow()).getAbsolutePath();
        if (pathEmulator != null) {
            this.pathEmulator.setText(pathEmulator);
            virusFileController.updatePathEmulator(pathEmulator);
        }
    }

    /*!
     обработчик события по кнопке для запуска эмулятора
    */
    public void runEmulator() throws IOException {
        String nameEmulator = this.nameEmulator.getText();
        if (!nameEmulator.isEmpty()) {
            virusFileController.updateNameEmulator(nameEmulator);
        }
        virusFileController.runEmulator();
    }

    /*!
     обработчик события по кнопке для выбора файла adb из Android SDK
    */
    public void chooseADB() {
        initFileChooser(ADB);
        String pathADB = fileChooser.showOpenDialog(pathToFile.getScene().getWindow()).getAbsolutePath();
        if (pathADB != null) {
            this.pathADB.setText(pathADB);
            virusFileController.updatePathADB(pathADB);
        }
    }

    /*!
     обработчик события по кнопке для выбора файла aapt из Android SDK
    */
    public void chooseAAPT() {
        initFileChooser(AAPT);
        String pathAAPT = fileChooser.showOpenDialog(pathToFile.getScene().getWindow()).getAbsolutePath();
        if (pathAAPT != null) {
            this.pathAAPT.setText(pathAAPT);
            virusFileController.updatePathAAPT(pathAAPT);
        }
    }

    /*!
     обработчик события по кнопке для установки .apk в эмулятор
    */
    public void installAPK() throws IOException, InterruptedException {
        if (!pathToFile.getText().isEmpty()) {
            virusFileController.installApp(pathToFile.getText());
        } else {
            AlertDialogUtils.showDialog(DialogMessage.ERROR_INSTALL_APK, DialogMessage.NOT_EXIST_PATH_TO_FILE);
        }
    }

    /*!
     обработчик события по кнопке для вывода списка разрешений вредоносного .apk
    */
    public void getPermissions() {
        Collection<String> permissions = virusFileController.scanVirusFileManifest();
        String allPermissions = "";
        for (String permission : permissions) {
            allPermissions += permission + "\n";
        }
        permissionFile.setText(allPermissions);
    }

    /*!
     обработчик события по кнопке для запуска приложения в эмуляторе
    */
    public void runApp() throws IOException, InterruptedException {
        virusFileController.runApp();
    }

    /*!
     обработчик события по кнопке для сбора логов
    */
    public void saveLogs() throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        logsArea.setText(virusFileController.saveLogs());
    }

    /*!
     метод для создания дерева файлов декомпилированного .apk
     \param[in] nameFile имя файла
     \param[in] treeItem экземпляр класса TreeItem
     \return экземпляр класса TreeItem
    */
    private TreeItem<File> createTree(String nameFile, TreeItem<File> treeItem) {
        File file = new File(nameFile);
        treeItem = new TreeItem<>(file);
        File files[]=file.listFiles();
        for (File file1 : files) {
            TreeItem<File> treeItem1 = null;
            if (file1.isDirectory()) {
                treeItem1 = createTree(file1.getPath(), treeItem1);
            } else {
                treeItem1 = new TreeItem<>(file1);
            }
            treeItem.getChildren().add(treeItem1);
	        waitCompleted();
	        allMethods.put(file1.getAbsolutePath(),analizier.analizeFile(file1, ""));
        }
        return treeItem;
    }
    protected void completedAnalize()
    {
    	progressAnalize.setProgress(tempProgress++/maxProgress);
    }
    
    /*!
     обработчик события по кнопке для показа дерева файлов и пакетов декомпилированного .apk
    */
    public void showTree() {
    	progressAnalize.setVisible(true);
    	tempProgress=0;
        alert = AlertDialogUtils.showProcessDialog("Идет анализ структуры файлов\n");
        alert.setOnShown(event -> {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
			        decompileExecutor.jarToJava();
			        if (!pathToFile.getText().isEmpty()) {
			            String[] path = pathToFile.getText().split(Pattern.quote("\\"));
			            String nameFile = path[path.length - 1].split(".apk")[0];
			            nameFile += "-dex";
			            maxProgress=filesCount(nameFile);
			            rootItem = null;
			            rootItem = createTree(nameFile, rootItem);
		                Platform.runLater(new Runnable() {
		                    @Override
		                    public void run() {
		                        alert.close();
		                        progressAnalize.setVisible(false);
	                        	decompileTree.setRoot(rootItem);
		                    }
		                });
			        }
                    return null;
                }
            };
            new Thread(task).start();
        });
        alert.show();
    }

    public int filesCount(String nameFile)
    {
    	int count;
        File file = new File(nameFile);
        File files[]=file.listFiles();
    	count=0;
        for (File file1 : files) {
            if (file1.isDirectory()) {
                count+=filesCount(file1.getPath());
            } else {
                count++;
            }
        }
        return count;
    }
    
    public String getFullGraph(String nameFile)
    {
        File file = new File(nameFile);
        File files[]=file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                getFullGraph(file1.getPath());
            } else {
                waitCompleted();
                methods=analizier.analizeFile(file1, methods);
            }
        }
        return methods;
    }
    
    public void exportGraph() {
        String filename="output.png";
        try(FileWriter writer = new FileWriter("output.dot", false))
        {
        	String[] path = pathToFile.getText().split(Pattern.quote("\\"));
            String nameFile = path[path.length - 1].split(".apk")[0];
            nameFile += "-dex";
            methods="";
            progressAnalize.setVisible(true);
        	writer.write(methodsBegin+getFullGraph(nameFile)+"}");
            progressAnalize.setVisible(false);
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        JFrame parentFrame = new JFrame();
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Сохранить общий граф состояний");
        fc.setCurrentDirectory(new File("."));
        int userSelection = fc.showSaveDialog(parentFrame);
         
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fc.getSelectedFile();
            filename=fileToSave.getAbsolutePath();
	        try(FileWriter writer = new FileWriter("gen.bat", false))
	        {
	            writer.write("sfdp -Gsize=0! -Goverlap=prism -Tpng output.dot > "+filename);
	            writer.flush();
	        }
	        catch(IOException e){
	
	            e.printStackTrace();
	        }
	        Alert alert = AlertDialogUtils.showProcessDialog("Идет построение графа");
	        alert.setOnShown(event -> {
	            Task<Void> task = new Task<Void>() {
	                @Override
	                protected Void call() throws Exception {
				        try {
				            Process process = Runtime.getRuntime().exec("gen.bat");
				            try {
				                process.waitFor();
				                Platform.runLater(new Runnable() {
				                    @Override
				                    public void run() {
				                        alert.close();
	                                    AlertDialogUtils.showInformationDialog("Построение выполнено",
	                                            "Файл успешно создан");
				                    }
				                });
				            } catch (InterruptedException e) {
				                e.printStackTrace();
				            }
				        } catch (IOException e) {
				            AlertDialogUtils.showDialog("Не удалось отобразить граф состояний", e.getMessage());
				            e.printStackTrace();
				        }
	                    return null;
	                }
	            };
	            new Thread(task).start();
	        });
	        alert.show();
        }
    }
    
    /*!
     обработчик события по клику на декомпилированный файл в дереве
    */
    public void showDecompileCode() {
    	MultipleSelectionModel<TreeItem<File>> model=decompileTree.getSelectionModel();
        TreeItem<File> treeItem = model.getSelectedItem();
        if (treeItem!=null&&!treeItem.getValue().isDirectory()) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                Scanner scanner = new Scanner(treeItem.getValue());
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine()).append("\n");
                }
                decompileCode.setText(stringBuilder.toString());
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            showGraphOfFileNum(treeItem.getValue().getAbsolutePath());
            graphView.setScaleX(1);
            graphView.setScaleY(1);
        }
    }
    
    public void showGraphOfFileNum(String path)
    {
        String methods=methodsBegin+allMethods.get(path);
        methods+="}";
        try(FileWriter writer = new FileWriter("output.dot", false))
        {
            writer.write(methods);
            writer.flush();
        }
        catch(IOException e){

            e.printStackTrace();
        }
        Alert alert = AlertDialogUtils.showProcessDialog("Идет построение графа");
        alert.setOnShown(event -> {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
			        try {
			            Process process = Runtime.getRuntime().exec("output.bat");
			            try {
			                process.waitFor();
			                Platform.runLater(new Runnable() {
			                    @Override
			                    public void run() {
			                        File file = new File("output.png");
			                	    String localUrl = file.toURI().toString();
			                	    Image image = new Image(localUrl);
			                	    graphView.setImage(image);
			                        alert.close();
			                    }
			                });
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            }
			        } catch (IOException e) {
			            AlertDialogUtils.showDialog("Не удалось отобразить граф состояний", e.getMessage());
			            e.printStackTrace();
			        }
                    return null;
                }
            };
            new Thread(task).start();
        });
        alert.show();
    }

    /*!
     обработчик события по переходу на вкладку декомпиляции
    */
    public void selectDecompile() {
        if (decompileTab.isSelected()) {
            decompileExecutor = new DecompileExecutor(pathToFile.getText());
            decompileExecutor.apkToJar();
        }
    }

    /*!
     обработчик события по переходу на вкладку файловой системы
    */
    public void selectFileSystem() {
        if (fileSystemTab.isSelected()) {
            listApkToDownload = new DownloadApk(pathADB.getText());
            listApk.setItems(listApkToDownload.getListApk());
        }
    }

    /*!
     обработчик события по кнопке для скачивания .apk файлов из эмулятора
    */
    public void download() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(PATH_TO_DOWNLOAD);
        listApkToDownload = new DownloadApk(pathADB.getText());
        File file = directoryChooser.showDialog(stage);
        downloadPath.setText(file.getAbsolutePath());
        listApkToDownload.download(listApk.getSelectionModel().getSelectedItem(), file.getAbsolutePath());
    }

    /*!
     обработчик события по кнопке для запуска прокси-сервера
    */
    public void startProxy() {
        ProxyServer.getInstance().runServer(logProxy);
    }

    /*!
     обработчик события по кнопке для остановки прокси-сервера
    */
    public void stopProxy() {
        ProxyServer.getInstance().stopServer();
    }
}
