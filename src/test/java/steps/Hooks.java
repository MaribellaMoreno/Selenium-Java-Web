package steps;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	String so = this.evalSo();
	//SELECT: "chrome" - "firefox" - "edge"
	public static String browser = "chrome";
    public static WebDriver driver;
	public static String dateTime;
	private static final String snapshotPath = "target/Evidencias/";

	//****INICIO Configuracion de lectura/escritura de datos ****//

	//*****Rutas*****
	//Lectura
	public static final String jsonEnvironmentFilePath = "src/test/resources/data/";
	public static final String excelFileReaderPath = "src/test/resources/data/";
	public static final String jsonFileReaderPath = "src/test/resources/data/";
	//Escritura
	public static final String jsonFileWriterPath = "target/generated-test-sources/files/";
	public static final String excelFileWriterPath = "target/generated-test-sources/files/";
	//*****Fin Rutas*****

	//*****Nombres*****
	//Lectura
	public static String environmentFile = "environment.json";
	public static String jsonFileReaderName = "data.json";
	public static String excelFileReaderName = "data.xlsx";
	//Los nombre de los archivos de escritura se otorgan en los parametros de cada funcion
	//*****Fin Nombres*****

	//*****Estructuras*****

	//JSON para creacion de archivo
	public static JSONObject jsonWriterObject = new JSONObject();
	//ArrayList para creacion de archivo .xlsx
	public static ArrayList<String[]> excelWriterObject = new ArrayList<>();

	//*****Fin Estructuras*****

	//****FIN Configuracion de lectura/escritura de datos ****//

	@Before
	public void setUp() {
		System.out.println("Plataforma so:	" + so);
		System.out.println("Browser select:	" + browser);
		switch(so){
			case "Windows":
				setUpOnWindows();
				break;
			case "Mac OS X":
				setUpOnMacOS();
				break;
			case "Linux":
				setUpOnLinux();
				break;
			default:
				System.out.println("SO not supported. Set config on: src/test/java/steps/hooks.java");
		}
	}
    @After
	public void tearDown() {
		driver.quit();
	}
	@AfterStep
	public void AfterStep(Scenario scenario) throws IOException {
		dateTime = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss").format(new Date());
		File fileScr = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		byte[] byteScr = Files.readAllBytes(fileScr.toPath());
		scenario.attach(byteScr, "image/png", String.valueOf(scenario.getName()) + "-" + dateTime);
	
		File destFile = new File(snapshotPath + dateTime +".png");
		System.out.println(destFile);
		FileUtils.copyFile(fileScr, destFile);
	}
	//**************Inicio funciones ambiente**************
	public void setUpOnMacOS(){
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/MacOS/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	public void setUpOnWindows(){
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/Windows/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	public void setUpOnLinux(){
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/Linux/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	//Funcion evalua el sistema operativo en el que se esta ejecutando.
	public String evalSo(){
		String so = System.getProperty("os.name");
		if(so.toUpperCase().contains("WINDOWS")){
			so = "Windows";
		}
		return so;
	}
	//**************Fin funciones ambiente**************

	//**************Inicio funciones custom**************

	//*****Lectura*****

	//Obtiene entorno de ejecucion
	public static String getEnvironment(String set, String key) {
		try {
			FileReader reader = new FileReader(jsonEnvironmentFilePath + environmentFile);
			Object dataParsed = null;
			JSONObject entity = null;
			//Leyendo archivo
			JSONParser jsonParser = new JSONParser();
			dataParsed = jsonParser.parse(reader);
			//Obteniendo set de elementos
			JSONObject jsonObject = (JSONObject) dataParsed;
			entity = (JSONObject) jsonObject.get(set);
			//Obteniendo valor
			return entity.get(key).toString();
		}catch (Exception e){
			System.out.println("Error al leer variables de entorno: " + e);
			return null;
		}
	}
	//Obtiene un dato desde un archivo json
	public static String getDataFromJson(String set, String key) {
		try {
			FileReader reader = new FileReader(jsonFileReaderPath + jsonFileReaderName);
			Object dataParsed = null;
			JSONObject entity = null;
			//Leyendo archivo
			JSONParser jsonParser = new JSONParser();
			dataParsed = jsonParser.parse(reader);
			//Obteniendo set de elementos
			JSONObject jsonObject = (JSONObject) dataParsed;
			entity = (JSONObject) jsonObject.get(set);
			//Obteniendo valor
			return entity.get(key).toString();
		}catch (Exception e){
			System.out.println("No se pudo leer un dato del archivo Json: " + e);
			return null;
		}
	}

	//Obtiene un set de datos desde un archivo json
	public static JSONObject getJsonSet(String element) {
		try {
			FileReader reader = new FileReader(jsonFileReaderPath + jsonFileReaderName);
			Object dataParsed = null;
			JSONObject entity = null;
			//Leyendo archivo
			JSONParser jsonParser = new JSONParser();
			dataParsed = jsonParser.parse(reader);
			//Obteniendo elemento
			JSONObject jsonObject = (JSONObject) dataParsed;
			entity = (JSONObject) jsonObject.get(element);
			return entity;
		}catch (Exception e){
			System.out.println("No se pudo leer un set de datos del archivo Json: " + e);
			return null;
		}
	}

	//Obtiene un dato desde un archivo excel
	public static String getDataFromExcel(String name){
		try (FileInputStream file = new FileInputStream(new File(excelFileReaderPath + excelFileReaderName))){
			//leer archivo excel
			XSSFWorkbook worbook = new XSSFWorkbook(file);
			//obtener la hoja que se va leer
			XSSFSheet sheet = worbook.getSheetAt(0);
			//obtener todas las filas de la hoja excel
			Iterator<Row> rowIterator = sheet.iterator();
			Row row;
			// se recorre cada fila hasta el final
			DataFormatter df = new DataFormatter();
			String value = "";
			while (rowIterator.hasNext()) {
				//se obtiene la columna A por fila
				row = rowIterator.next();
				Cell cellValue = row.getCell(0);
				String dato = df.formatCellValue(cellValue);
				//Si dato corresponde al nombre buscado, Retorna el dato de la fila B
				if (dato.equalsIgnoreCase(name)) {
					cellValue = row.getCell(1);
					value = df.formatCellValue(cellValue);
				}
			}
			return value;
		} catch (Exception e) {
			System.out.println("No se pudo leer el archivo .xlsx: " + e);
			return null;
		}
	}
	//*****Escritura*****

	//Crea un archivo Json simple con los datos entregados
	public static void createJsonSimpleFromData(String newJsonFileName){
		try{
			FileWriter newJsonFile = new FileWriter(jsonFileWriterPath + newJsonFileName + ".json");
			newJsonFile.write(jsonWriterObject.toJSONString());
			newJsonFile.flush();
			newJsonFile.close();
			System.out.println("Se ha creado el archivo: " + jsonFileWriterPath + newJsonFileName + ".json");
		} catch (Exception e){
			System.out.println("No se pudo crear el archivo Json: " + e);
		}
	}
	//Agrega registro a un Json para luego generar un archivo.json
	public static void addKeyValueToJson(String key, String value) {
		try{
			jsonWriterObject.put(key, value);
		}catch(Exception e){
			System.out.println("No se pudo agregar los datos al Json: " + e);
		}
	}

	//Agrega registro a un ArrayList para luego generar un archivo.excel
	public static void addDataToExcel(String nombre, String valor){
		try{
			String[] array = new String[2];
			array[0] = nombre;
			array[1] = valor;
			excelWriterObject.add(array);
		}catch (Exception e){
			System.out.println("No se pudo agregar los datos al archivo .xlsx: " + e);
		}

	}
	//Crea un archivo .xlsx con los datos entregados
	public static void createExcelFromData(String newExcelFileName){
		File file;
		file = new File(excelFileWriterPath + newExcelFileName + ".xlsx");
		try (FileOutputStream fileOuS = new FileOutputStream(file)){
			//Generndo nuevo archivo
			XSSFWorkbook libro= new XSSFWorkbook();
			//Se crea la hoja de excel
			XSSFSheet hoja1 = libro.createSheet("Datos");
			//Creando cabecera
			String [] header= new String[]{"Nombre", "Dato"};
			//poner negrita a la cabecera
			CellStyle style = libro.createCellStyle();
			Font font = libro.createFont();
			font.setBold(true);
			style.setFont(font);
			//Escribiendo cabecera
			XSSFRow rowCabecera=hoja1.createRow(0);
			for (int c = 0; c < 2; c++) {
				//se crea las celdas para la cabecera, junto con la posición
				XSSFCell cell= rowCabecera.createCell(c);
				// se añade el style crea anteriormente
				cell.setCellStyle(style);
				//se añade el contenido
				cell.setCellValue(header[c]);
			}
			for (String[] registro : excelWriterObject) {
				XSSFRow row=hoja1.createRow(excelWriterObject.indexOf(registro)+1);//se crea las filas
				//Contenido
				for (int j = 0; j < 2; j++) {
					//se crea las celdas para la contenido, junto con la posición
					XSSFCell cell= row.createCell(j);
					//se añade el contenido
					cell.setCellValue(registro[j]);
				}
			}
			//Se crea el archivo
			libro.write(fileOuS);
			//Se limpia el buffer y se cierra
			fileOuS.flush();
			fileOuS.close();
			//Se limpia el ArrayList
			excelWriterObject.clear();
			System.out.println("Se ha creado el archivo: " + excelFileWriterPath + newExcelFileName + ".xlsx");
		}catch (IOException e) {
			System.out.println("No se pudo crear el archivo .xlsx: " + e);
		}
	}
	//**************Fin funciones custom**************
}
