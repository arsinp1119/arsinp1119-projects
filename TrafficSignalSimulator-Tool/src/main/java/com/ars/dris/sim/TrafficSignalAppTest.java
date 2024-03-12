
package com.ars.dris.sim;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import javafx.scene.control.ComboBox;
import com.ars.drismcm.rabbit.RabitOBCSConnectMessageBuffer;
import com.ars.obcs.OBCSConnectMessageBuffer;
import com.ars.obcs.messages.ANPRBusDetectionReq;
import com.ars.obcs.messages.ANPROccupancyDetection;
import com.ars.obcs.messages.ANPRPassageDetectionReq;
import com.ars.obcs.messages.DBusUpdateTrafficSignalAck;
import com.ars.obcs.messages.DBusUpdateTrafficSignalReq;
import com.ars.rabbit.RabbitClient.RabbitMessage;
import com.rabbitmq.client.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TrafficSignalAppTest extends Application {
	private GridPane gridPane;
	
	
	private Map<String, ImageView> imageViewMap = new HashMap<>();
	private static final AtomicLong seqNo = new AtomicLong(1);
	private static final AtomicLong detectionId = new AtomicLong(1);
	private String directionField = null;
	private String platformField = null;
	private String occuppancyField = null;
	Color red = Color.rgb(240, 40, 40, 0.2);
	Color activeRed = Color.rgb(240, 40, 40, 1);
	Color green = Color.rgb(10, 196, 18, 0.2);
	Color activeGreen = Color.rgb(10, 196, 18, 1);
	
	Properties property = new Properties();
	String exchange = null;
	String url = null;
	int port;
	String pwd = null;
	String user = null;
	String busDetectionRoutingkey = null;
	String pasageDetectionRoutingkey = null;
	String occupancyDetectionRoutingkey = null;
	String displayID = null;
	String filePath = "";
	String message = "";
	String busDetectionMessageType = "";
	String passageDetectionMessageType = "";
	String occupancyDetectionMessageType = "";

	String leftEntrySignalRoutingkey = "";
	String rightEntrySignalRoutingkey = "";
	String platformSignal1Routingkey = "";
	String platformSignal2Routingkey = "";
	String platformSignal3Routingkey = "";
	String platformSignal4Routingkey = "";
	String platformSignal5Routingkey = "";
	String platformSignal6Routingkey = "";
	String platformSignal7Routingkey = "";
	String platformSignal8Routingkey = "";
	String platformSignal9Routingkey = "";
	String platformSignal10Routingkey = "";
	String platformSignal11Routingkey = "";
	String platformSignal12Routingkey = "";
	String platformSignal13Routingkey = "";
	String platformSignal14Routingkey = "";
	String platformSignal15Routingkey = "";

	String leftEntrySignalQueue="";
	String rightEntrySignalQueue="";
	String platformSignal1Queue="";
	String platformSignal2Queue="";
	String platformSignal3Queue="";
	String platformSignal4Queue="";
	String platformSignal5Queue="";
	String platformSignal6Queue="";
	String platformSignal7Queue="";
	String platformSignal8Queue="";
	String platformSignal9Queue="";
	String platformSignal10Queue="";
	String platformSignal11Queue="";
	String platformSignal12Queue="";
	String platformSignal13Queue="";
	String platformSignal14Queue="";
	String platformSignal15Queue="";

	String interval = "";

	Pane entryTrafficSignals1;
	Pane entryTrafficSignals2;
	Pane platformSignal1;
	Pane platformSignal2;
	Pane platformSignal3;
	Pane platformSignal4;
	Pane platformSignal5;
	Pane platformSignal6;
	Pane platformSignal7;
	Pane platformSignal8;
	Pane platformSignal9;
	Pane platformSignal10;
	Pane platformSignal11;
	Pane platformSignal12;
	Pane platformSignal13;
	Pane platformSignal14;
	Pane platformSignal15;
	
	private ImageView imageView;
	private ImageView LEB;
	private ImageView REB;
	private ImageView LPB;
	private ImageView RPB;
	private ImageView P1B;
	private ImageView P2B;
	private ImageView P3B;
	private ImageView P4B;
	private ImageView P5B;
	private ImageView P6B;
	private ImageView P7B;
	private ImageView P8B;
	private ImageView P9B;
	private ImageView P10B;
	private ImageView P11B;
	private ImageView P12B;
	private ImageView P13B;
	private ImageView P14B;
	private ImageView P15B;
	private ImageView EEB;

	private Channel channel;

	@Override
	public void start(Stage primaryStage) throws IOException {
		gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(20);
		gridPane.setPadding(new Insets(5));

		//RabbitMQBroker rmqBroker = new RabbitMQBroker(null);
		//rmqBroker.init();
		
		//gridPane.setGridLinesVisible(true);
		FileInputStream input = new FileInputStream("Configuration/stubConfiguration.properties");
		property.load(input);
		url = property.getProperty("url");
		port = Integer.parseInt(property.getProperty("port"));
		user = property.getProperty("username");
		pwd = property.getProperty("password");
		exchange = property.getProperty("exchange");
		busDetectionRoutingkey = property.getProperty("busDetectionRoutingkey");
		pasageDetectionRoutingkey = property.getProperty("pasageDetectionRoutingkey");
		occupancyDetectionRoutingkey = property.getProperty("occupancyDetectionRoutingkey");
		displayID = property.getProperty("displayID");
		filePath = property.getProperty("filePath");
		interval = property.getProperty("sendIntervalInSeconds");
		leftEntrySignalRoutingkey = property.getProperty("leftEntrySignalRoutingkey");
		rightEntrySignalRoutingkey = property.getProperty("rightEntrySignalRoutingkey");
		platformSignal1Routingkey = property.getProperty("platformSignal1Routingkey");
		platformSignal2Routingkey = property.getProperty("platformSignal2Routingkey");
		platformSignal3Routingkey = property.getProperty("platformSignal3Routingkey");
		platformSignal4Routingkey = property.getProperty("platformSignal4Routingkey");
		platformSignal5Routingkey = property.getProperty("platformSignal5Routingkey");
		platformSignal6Routingkey = property.getProperty("platformSignal6Routingkey");
		platformSignal7Routingkey = property.getProperty("platformSignal7Routingkey");
		platformSignal8Routingkey = property.getProperty("platformSignal8Routingkey");
		platformSignal9Routingkey = property.getProperty("platformSignal9Routingkey");
		platformSignal10Routingkey = property.getProperty("platformSignal10Routingkey");
		platformSignal11Routingkey = property.getProperty("platformSignal11Routingkey");
		platformSignal12Routingkey = property.getProperty("platformSignal12Routingkey");
		platformSignal13Routingkey = property.getProperty("platformSignal13Routingkey");
		platformSignal14Routingkey = property.getProperty("platformSignal14Routingkey");
		platformSignal15Routingkey = property.getProperty("platformSignal15Routingkey");
		
		leftEntrySignalQueue = property.getProperty("leftEntrySignalQueue");
		rightEntrySignalQueue = property.getProperty("rightEntrySignalQueue");
		platformSignal1Queue = property.getProperty("platformSignal1Queue");
		platformSignal2Queue = property.getProperty("platformSignal2Queue");
		platformSignal3Queue = property.getProperty("platformSignal3Queue");
		platformSignal4Queue = property.getProperty("platformSignal4Queue");
		platformSignal5Queue = property.getProperty("platformSignal5Queue");
		platformSignal6Queue = property.getProperty("platformSignal6Queue");
		platformSignal7Queue = property.getProperty("platformSignal7Queue");
		platformSignal8Queue = property.getProperty("platformSignal8Queue");
		platformSignal9Queue = property.getProperty("platformSignal9Queue");
		platformSignal10Queue = property.getProperty("platformSignal10Queue");
		platformSignal11Queue = property.getProperty("platformSignal11Queue");
		platformSignal12Queue = property.getProperty("platformSignal12Queue");
		platformSignal13Queue = property.getProperty("platformSignal13Queue");
		platformSignal14Queue = property.getProperty("platformSignal14Queue");
		platformSignal15Queue = property.getProperty("platformSignal15Queue");

		VBox entryCamera1 = createIconWithLabel("/images/icon-video.png", new Label(), 1, 1);
		VBox entryCamera2 = createIconWithLabel("/images/icon-video.png", new Label(), 2, 2);
		VBox passageDetectionCamera1 = createIconWithLabel("/images/icon-video.png", new Label(), 3, 3);
		VBox passageDetectionCamera2 = createIconWithLabel("/images/icon-video.png", new Label(), 4, 4);
		VBox platformCamera1 = createIconWithLabel("/images/icon-video.png", new Label(), 5, 5);
		VBox platformCamera2 = createIconWithLabel("/images/icon-video.png", new Label(), 6, 6);
		VBox platformCamera3 = createIconWithLabel("/images/icon-video.png", new Label(), 7, 7);
		VBox platformCamera4 = createIconWithLabel("/images/icon-video.png", new Label(), 8, 8);
		VBox platformCamera5 = createIconWithLabel("/images/icon-video.png", new Label(), 9, 9);
		VBox platformCamera6 = createIconWithLabel("/images/icon-video.png", new Label(), 10, 10);
		VBox platformCamera7 = createIconWithLabel("/images/icon-video.png", new Label(), 11, 11);
		VBox platformCamera8 = createIconWithLabel("/images/icon-video.png", new Label(), 12, 12);
		VBox platformCamera9 = createIconWithLabel("/images/icon-video.png", new Label(), 13, 13);
		VBox platformCamera10 = createIconWithLabel("/images/icon-video.png", new Label(), 14, 14);
		VBox platformCamera11 = createIconWithLabel("/images/icon-video.png", new Label(), 15, 15);
		VBox platformCamera12 = createIconWithLabel("/images/icon-video.png", new Label(), 16, 16);
		VBox platformCamera13 = createIconWithLabel("/images/icon-video.png", new Label(), 17, 17);
		VBox platformCamera14 = createIconWithLabel("/images/icon-video.png", new Label(), 18, 18);
		VBox platformCamera15 = createIconWithLabel("/images/icon-video.png", new Label(), 19, 19);
		
		VBox exitCamera1 = createIconWithLabel("/images/icon-video.png", new Label(), 20, 20);

		initializeRabbitMQ();
		// Start receiving messages in a separate thread
		new Thread(() -> {
			try {
				receiveMessages();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		primaryStage.setTitle("Traffic Signals");
		entryTrafficSignals1 = createEntryTrafficSignal1("Left Entry", 0);
		entryTrafficSignals2 = createEntryTrafficSignal2("Right Entry", 0);
		platformSignal1 = createTrafficSignalplatform1("PS 1", 0);
		platformSignal2 = createTrafficSignalplatform2("PS 2", 0);
		platformSignal3 = createTrafficSignalplatform3("PS 3", 0);
		platformSignal4 = createTrafficSignalplatform4("PS 4", 0);
		platformSignal5 = createTrafficSignalplatform5("PS 5", 0);
		platformSignal6 = createTrafficSignalplatform6("PS 6", 0);
		platformSignal7 = createTrafficSignalplatform7("PS 7", 0);
		platformSignal8 = createTrafficSignalplatform8("PS 8", 0);
		platformSignal9 = createTrafficSignalplatform9("PS 9", 0);
		platformSignal10 = createTrafficSignalplatform10("PS 10", 0);
		platformSignal11 = createTrafficSignalplatform11("PS 11", 0);
		platformSignal12 = createTrafficSignalplatform12("PS 12", 0);
		platformSignal13 = createTrafficSignalplatform13("PS 13", 0);
		platformSignal14 = createTrafficSignalplatform14("PS 14", 0);
		platformSignal15 = createTrafficSignalplatform15("PS 15", 0);
		
		
		LEB= BusView("/images/EntryExitBus.png");
		REB= BusView("/images/EntryExitBus.png");
		LPB= BusView("/images/EntryExitBus.png");
		RPB= BusView("/images/EntryExitBus.png");
		P1B= BusView("/images/Bus.png");
		P1B= BusView("/images/Bus.png");
		P2B= BusView("/images/Bus.png");
		P3B= BusView("/images/Bus.png");
		P4B= BusView("/images/Bus.png");
		P5B= BusView("/images/Bus.png");
		P6B= BusView("/images/Bus.png");
		P7B= BusView("/images/Bus.png");
		P8B= BusView("/images/Bus.png");
		P9B= BusView("/images/Bus.png");
		P10B= BusView("/images/Bus.png");
		P11B= BusView("/images/Bus.png");
		P12B= BusView("/images/Bus.png");
		P13B= BusView("/images/Bus.png");
		P14B= BusView("/images/Bus.png");
		P15B= BusView("/images/Bus.png");
		EEB= BusView("/images/EntryExitBus.png");
		
		imageViewMap.put("LEB", LEB);
		imageViewMap.put("REB", REB);
		imageViewMap.put("LPB", LPB);
		imageViewMap.put("RPB", RPB);
		imageViewMap.put("P1B", P1B);
		imageViewMap.put("P2B", P2B);
		imageViewMap.put("P3B", P3B);
		imageViewMap.put("P4B", P4B);
		imageViewMap.put("P5B", P5B);
		imageViewMap.put("P6B", P6B);
		imageViewMap.put("P7B", P7B);
		imageViewMap.put("P8B", P8B);
		imageViewMap.put("P9B", P9B);
		imageViewMap.put("P10B", P10B);
		imageViewMap.put("P11B", P11B);
		imageViewMap.put("P12B", P12B);
		imageViewMap.put("P13B", P13B);
		imageViewMap.put("P14B", P14B);
		imageViewMap.put("P15B", P15B);
		imageViewMap.put("EEB", EEB);
		
		Line LEHLine = new Line(0, 0, 70, 0);
		Line REHLine = new Line(0, 0, 70, 0);
		Line P1HLine = new Line(0, 0, 70, 0);
		Line P1VLine = new Line(0, 0, 0, 80);
		Line P2HLine = new Line(0, 0, 70, 0);
		Line P2VLine = new Line(0, 0, 0, 80);
		Line P3HLine = new Line(0, 0, 70, 0);
		Line P3VLine = new Line(0, 0, 0, 80);
		Line P4HLine = new Line(0, 0, 70, 0);
		Line P4VLine = new Line(0, 0, 0, 80);
		Line P5HLine = new Line(0, 0, 70, 0);
		Line P5VLine = new Line(0, 0, 0, 80);
		Line P6HLine = new Line(0, 0, 70, 0);
		Line P6VLine = new Line(0, 0, 0, 80);
		Line P7HLine = new Line(0, 0, 70, 0);
		Line P7VLine = new Line(0, 0, 0, 80);
		Line P8HLine = new Line(0, 0, 70, 0);
		Line P8VLine = new Line(0, 0, 0, 80);
		Line P9HLine = new Line(0, 0, 70, 0);
		Line P9VLine = new Line(0, 0, 0, 80);
		Line P10HLine = new Line(0, 0, 70, 0);
		Line P10VLine = new Line(0, 0, 0, 80);
		Line P11HLine = new Line(0, 0, 70, 0);
		Line P11VLine = new Line(0, 0, 0, 80);
		Line P12HLine = new Line(0, 0, 70, 0);
		Line P12VLine = new Line(0, 0, 0, 80);
		Line P13HLine = new Line(0, 0, 70, 0);
		Line P13VLine = new Line(0, 0, 0, 80);
		Line P14HLine = new Line(0, 0, 70, 0);
		Line P14VLine = new Line(0, 0, 0, 80);
		Line P15HLine = new Line(0, 0, 70, 0);
		Line EEHLine = new Line(0, 0, 70, 0);
		
		
		gridPane.add(entryTrafficSignals1, 0, 0);
		gridPane.add(entryCamera1, 1, 0);
        gridPane.add(LEB, 0, 1);
		Label signalLabel1 = new Label("LEFT ENTRY");
		gridPane.add(signalLabel1, 0, 2);
		gridPane.add(LEHLine, 0, 3);
		
		gridPane.add(entryTrafficSignals2, 0, 4);
		gridPane.add(entryCamera2, 1, 4);
        gridPane.add(REB, 0, 5);
		Label signalLabel2 = new Label("RIGHT ENTRY");
		gridPane.add(signalLabel2, 0, 6);
		gridPane.add(REHLine, 0, 7);
		
		gridPane.add(passageDetectionCamera1, 3, 0);
		gridPane.add(LPB, 3, 1);
		gridPane.add(passageDetectionCamera2, 3, 4);
		gridPane.add(RPB, 3, 5);
		
		gridPane.add(platformSignal1, 6, 8);
		gridPane.add(platformCamera1, 7, 8);
        gridPane.add(P1B, 6, 9);
		Label ps1 = new Label("PS 1");
		gridPane.add(ps1, 6, 10);
		gridPane.add(P1HLine, 6, 11);
		gridPane.add(P1VLine, 8, 9);
		
		gridPane.add(platformSignal2, 9, 8);
		gridPane.add(platformCamera2, 10, 8);
        gridPane.add(P2B, 9, 9);
		Label ps2 = new Label("PS 2");
		gridPane.add(ps2, 9, 10);
		gridPane.add(P2HLine, 9, 11);
		gridPane.add(P2VLine, 11, 9);
		
		gridPane.add(platformSignal3, 12, 8);
		gridPane.add(platformCamera3, 13, 8);
        gridPane.add(P3B, 12, 9);
		Label ps3 = new Label("PS 3");
		gridPane.add(ps3, 12, 10);
		gridPane.add(P3HLine, 12, 11);
		gridPane.add(P3VLine, 14, 9);
		
		gridPane.add(platformSignal4, 0, 12);
		gridPane.add(platformCamera4, 1, 12);
        gridPane.add(P4B, 0, 13);
		Label ps4 = new Label("PS 4");
		gridPane.add(ps4, 0, 14);
		gridPane.add(P4HLine, 0, 15);
		gridPane.add(P4VLine, 2, 13);
		
		gridPane.add(platformSignal5, 3, 12);
		gridPane.add(platformCamera5, 4, 12);
        gridPane.add(P5B, 3, 13);
		Label ps5 = new Label("PS 5");
		gridPane.add(ps5, 3, 14);
		gridPane.add(P5HLine, 3, 15);
		gridPane.add(P5VLine, 5, 13);
		
		gridPane.add(platformSignal6, 6, 12);
		gridPane.add(platformCamera6, 7, 12);
        gridPane.add(P6B, 6, 13);
		Label ps6 = new Label("PS 6");
		gridPane.add(ps6, 6, 14);
		gridPane.add(P6HLine, 6, 15);
		gridPane.add(P6VLine, 8, 13);
		
		gridPane.add(platformSignal7, 9, 12);
		gridPane.add(platformCamera7, 10, 12);
        gridPane.add(P7B, 9, 13);
		Label ps7 = new Label("PS 7");
		gridPane.add(ps7, 9, 14);
		gridPane.add(P7HLine, 9, 15);
		gridPane.add(P7VLine, 11, 13);
		
		gridPane.add(platformSignal8, 12, 12);
		gridPane.add(platformCamera8, 13, 12);
        gridPane.add(P8B, 12, 13);
		Label ps8 = new Label("PS 8");
		gridPane.add(ps8, 12, 14);
		gridPane.add(P8HLine, 12, 15);
		gridPane.add(P8VLine, 14, 13);
		
		gridPane.add(platformSignal9, 15, 12);
		gridPane.add(platformCamera9, 16, 12);
        gridPane.add(P9B, 15, 13);
		Label ps9 = new Label("PS 9");
		gridPane.add(ps9, 15, 14);
		gridPane.add(P9HLine, 15, 15);
		gridPane.add(P9VLine, 17, 13);
		
		gridPane.add(platformSignal10, 18, 12);
		gridPane.add(platformCamera10, 19, 12);
        gridPane.add(P10B, 18, 13);
		Label ps10 = new Label("PS 10");
		gridPane.add(ps10, 18, 14);
		gridPane.add(P10HLine, 18, 15);
		gridPane.add(P10VLine, 20, 13);
		
		gridPane.add(platformSignal11, 21, 12);
		gridPane.add(platformCamera11, 22, 12);
        gridPane.add(P11B, 21, 13);
		Label ps11 = new Label("PS 11");
		gridPane.add(ps11, 21, 14);
		gridPane.add(P11HLine, 21, 15);
		gridPane.add(P11VLine, 23, 13);
		
		gridPane.add(platformSignal12, 24, 12);
		gridPane.add(platformCamera12, 25, 12);
        gridPane.add(P12B, 24, 13);
		Label ps12 = new Label("PS 12");
		gridPane.add(ps12, 24, 14);
		gridPane.add(P12HLine, 24, 15);
		gridPane.add(P12VLine, 26, 13);
		
		gridPane.add(platformSignal13, 27, 12);
		gridPane.add(platformCamera13, 28, 12);
        gridPane.add(P13B, 27, 13);
		Label ps13 = new Label("PS 13");
		gridPane.add(ps13, 27, 14);
		gridPane.add(P13HLine, 27, 15);
		gridPane.add(P13VLine, 29, 13);
		
		gridPane.add(platformSignal14, 30, 12);
		gridPane.add(platformCamera14, 31, 12);
        gridPane.add(P14B, 30, 13);
		Label ps14 = new Label("PS 14");
		gridPane.add(ps14, 30, 14);
		gridPane.add(P14HLine, 30, 15);
		gridPane.add(P14VLine, 32, 13);
		
		gridPane.add(platformSignal15, 33, 12);
		gridPane.add(platformCamera15, 34, 12);
        gridPane.add(P15B, 33, 13);
		Label ps15 = new Label("PS 15");
		gridPane.add(ps15, 33, 14);
		gridPane.add(P15HLine, 33, 15);
		
		gridPane.add(exitCamera1, 33, 0);
		gridPane.add(EEB, 33, 1);
		Label EE = new Label("EXIT ENTRY");
		gridPane.add(EE, 33, 2);
		gridPane.add(EEHLine, 33, 3);
		

		Scene scene = new Scene(gridPane, 300, 100);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ImageView BusView(String filePath) {
		 imageView = new ImageView(filePath);
	        imageView.setFitWidth(70);
			imageView.setFitHeight(70);
			imageView.setVisible(false);
		return imageView;
	}

	private Pane createEntryTrafficSignal1(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));
		
		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createEntryTrafficSignal2(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform1(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));


		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform2(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform3(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform4(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));


		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform5(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));


		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform6(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));
		
		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform7(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));
		
		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform8(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform9(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));
		

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform10(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform11(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform12(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform13(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform14(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private Pane createTrafficSignalplatform15(String label, int requestedLightState) {
		Pane lightPane = new Pane();
		Rectangle tLight = new Rectangle(0, 0, 50, 50 / 3.0);
		Circle redLight = new Circle(100 / 3.0 / 2, 100 / 4.0, 5);
		Circle greenLight = new Circle(100 / 3.0 / 2, 100 / 4.0 * 3, 5);
		tLight.setFill(Color.WHITE);
		tLight.setStroke(Color.GREY);
		tLight.xProperty().bind(lightPane.widthProperty().divide(2).subtract(tLight.getWidth() / 2));
		tLight.yProperty().bind(lightPane.heightProperty().divide(2).subtract(tLight.getHeight() / 2));
		redLight.setFill(Color.WHITE);
		redLight.setStroke(Color.GREY);
		redLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		redLight.centerXProperty().bind(tLight.xProperty().add(10));
		greenLight.setFill(Color.WHITE);
		greenLight.setStroke(Color.GREY);
		greenLight.centerYProperty().bind(lightPane.heightProperty().divide(2));
		greenLight.centerXProperty().bind(tLight.xProperty().add(30));

		lightPane.getChildren().addAll(tLight, redLight, greenLight);
		if (requestedLightState == 0) {
			redLight.setFill(activeRed);
			greenLight.setFill(green);
		} else if (requestedLightState == 1) {
			redLight.setFill(red);
			greenLight.setFill(activeGreen);
		} else if (requestedLightState == 2) {
			redLight.setFill(red);
			greenLight.setFill(green);
		}
		return lightPane;
	}

	private void initializeRabbitMQ() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(url);
		factory.setPort(port);
		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(leftEntrySignalQueue, false, false, false, null);
			channel.queueBind(leftEntrySignalQueue, exchange, leftEntrySignalRoutingkey);
			channel.queueDeclare(rightEntrySignalQueue, false, false, false, null);
			channel.queueBind(rightEntrySignalQueue, exchange, rightEntrySignalRoutingkey);
			channel.queueDeclare(platformSignal1Queue, false, false, false, null);
			channel.queueBind(platformSignal1Queue, exchange, platformSignal1Routingkey);
			channel.queueDeclare(platformSignal2Queue, false, false, false, null);
			channel.queueBind(platformSignal2Queue, exchange, platformSignal2Routingkey);
			channel.queueDeclare(platformSignal3Queue, false, false, false, null);
			channel.queueBind(platformSignal3Queue, exchange, platformSignal3Routingkey);
			channel.queueDeclare(platformSignal4Queue, false, false, false, null);
			channel.queueBind(platformSignal4Queue, exchange, platformSignal4Routingkey);
			channel.queueDeclare(platformSignal5Queue, false, false, false, null);
			channel.queueBind(platformSignal5Queue, exchange, platformSignal5Routingkey);
			channel.queueDeclare(platformSignal6Queue, false, false, false, null);
			channel.queueBind(platformSignal6Queue, exchange, platformSignal6Routingkey);
			channel.queueDeclare(platformSignal7Queue, false, false, false, null);
			channel.queueBind(platformSignal7Queue, exchange, platformSignal7Routingkey);
			channel.queueDeclare(platformSignal8Queue, false, false, false, null);
			channel.queueBind(platformSignal8Queue, exchange, platformSignal8Routingkey);
			channel.queueDeclare(platformSignal9Queue, false, false, false, null);
			channel.queueBind(platformSignal9Queue, exchange, platformSignal9Routingkey);
			channel.queueDeclare(platformSignal10Queue, false, false, false, null);
			channel.queueBind(platformSignal10Queue, exchange, platformSignal10Routingkey);
			channel.queueDeclare(platformSignal11Queue, false, false, false, null);
			channel.queueBind(platformSignal11Queue, exchange, platformSignal11Routingkey);
			channel.queueDeclare(platformSignal12Queue, false, false, false, null);
			channel.queueBind(platformSignal12Queue, exchange, platformSignal12Routingkey);
			channel.queueDeclare(platformSignal13Queue, false, false, false, null);
			channel.queueBind(platformSignal13Queue, exchange, platformSignal13Routingkey);
			channel.queueDeclare(platformSignal14Queue, false, false, false, null);
			channel.queueBind(platformSignal14Queue, exchange, platformSignal14Routingkey);
			channel.queueDeclare(platformSignal15Queue, false, false, false, null);
			channel.queueBind(platformSignal15Queue, exchange, platformSignal15Routingkey);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	private void receiveMessages() throws Exception {

		Consumer leftEntrySignalConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updateEntryTrafficSignal1(requestedLightState));
					
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)120,(byte)requestedLightState,(byte)0 );
					  //channel.basicPublish("", leftEntrySignalQueue, null,messageBytes);
					 

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Consumer rightEntrySignalConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updateEntryTrafficSignal2(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)121,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS1Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal1(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)101,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS2Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal2(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)102,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS3Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal3(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)103,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS4Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal4(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)104,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS5Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal5(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)105,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS6Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal6(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)106,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS7Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal7(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)107,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS8Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal8(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)108,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS9Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal9(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)109,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS10Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal10(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)110,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS11Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal11(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)111,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS12Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal12(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)112,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS13Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal13(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)113,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS14Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal14(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)114,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Consumer PS15Consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				byte[] receivedBytes;
				receivedBytes = body;
				ByteArrayInputStream receiveBuffer = new ByteArrayInputStream(receivedBytes);
				OBCSConnectMessageBuffer buffer = new RabitOBCSConnectMessageBuffer(null, receiveBuffer);
				DBusUpdateTrafficSignalReq anprMsg = new DBusUpdateTrafficSignalReq();
				try {
					anprMsg.processNewMessage(buffer);
					int requestedLightState = anprMsg.getrequestedLightState();
					Platform.runLater(() -> updatePlatformTrafficSignal15(requestedLightState));
					sendAckMessage(anprMsg.getMsgSequenceNumber(),(byte)115,(byte)requestedLightState,(byte)0 );

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// Start consuming messages from queue
		channel.basicConsume(leftEntrySignalQueue, true, leftEntrySignalConsumer);
		channel.basicConsume(rightEntrySignalQueue, true, rightEntrySignalConsumer);
		channel.basicConsume(platformSignal1Queue, true, PS1Consumer);
		channel.basicConsume(platformSignal2Queue, true, PS2Consumer);
		channel.basicConsume(platformSignal3Queue, true, PS3Consumer);
		channel.basicConsume(platformSignal4Queue, true, PS4Consumer);
		channel.basicConsume(platformSignal5Queue, true, PS5Consumer);
		channel.basicConsume(platformSignal6Queue, true, PS6Consumer);
		channel.basicConsume(platformSignal7Queue, true, PS7Consumer);
		channel.basicConsume(platformSignal8Queue, true, PS8Consumer);
		channel.basicConsume(platformSignal9Queue, true, PS9Consumer);
		channel.basicConsume(platformSignal10Queue, true, PS10Consumer);
		channel.basicConsume(platformSignal11Queue, true, PS11Consumer);
		channel.basicConsume(platformSignal12Queue, true, PS12Consumer);
		channel.basicConsume(platformSignal13Queue, true, PS13Consumer);
		channel.basicConsume(platformSignal14Queue, true, PS14Consumer);
		channel.basicConsume(platformSignal15Queue, true, PS15Consumer);

	}

	private void updateEntryTrafficSignal1(int requestedLightState) {
		entryTrafficSignals1 = createEntryTrafficSignal1("Entry Signal 1", requestedLightState);
		gridPane.add(entryTrafficSignals1, 0, 0);
	}

	private void updateEntryTrafficSignal2(int requestedLightState) {
		entryTrafficSignals2 = createEntryTrafficSignal2("Entry Signal 2", requestedLightState);
		
		gridPane.add(entryTrafficSignals2, 0, 4);
	}

	private void updatePlatformTrafficSignal1(int requestedLightState) {
		platformSignal1 = createTrafficSignalplatform1("platformSignal 1", requestedLightState);
		
		gridPane.add(platformSignal1, 6, 8);
	}

	private void updatePlatformTrafficSignal2(int requestedLightState) {
		platformSignal2 = createTrafficSignalplatform2("platformSignal 2", requestedLightState);
 		
 		gridPane.add(platformSignal2, 9, 8);
	}

	private void updatePlatformTrafficSignal3(int requestedLightState) {
		platformSignal3 = createTrafficSignalplatform3("platformSignal 3", requestedLightState);
		
		gridPane.add(platformSignal3, 12, 8);
	}

	private void updatePlatformTrafficSignal4(int requestedLightState) {
		platformSignal4 = createTrafficSignalplatform4("platformSignal 4", requestedLightState);
		
		gridPane.add(platformSignal4, 0, 12);
	}

	private void updatePlatformTrafficSignal5(int requestedLightState) {
		platformSignal5 = createTrafficSignalplatform5("platformSignal 5", requestedLightState);
		
		gridPane.add(platformSignal5, 3, 12);
	}

	private void updatePlatformTrafficSignal6(int requestedLightState) {
		platformSignal6 = createTrafficSignalplatform6("platformSignal 6", requestedLightState);
		
		gridPane.add(platformSignal6, 6, 12);
	}

	private void updatePlatformTrafficSignal7(int requestedLightState) {
		platformSignal7 = createTrafficSignalplatform7("platformSignal 7", requestedLightState);
		
		gridPane.add(platformSignal7, 9, 12);
	}

	private void updatePlatformTrafficSignal8(int requestedLightState) {
		platformSignal8 = createTrafficSignalplatform8("platformSignal 8", requestedLightState);
		
		gridPane.add(platformSignal8, 12, 12);
	}

	private void updatePlatformTrafficSignal9(int requestedLightState) {
		platformSignal9 = createTrafficSignalplatform9("platformSignal 9", requestedLightState);
		
		gridPane.add(platformSignal9, 15, 12);
	}

	private void updatePlatformTrafficSignal10(int requestedLightState) {
		platformSignal10 = createTrafficSignalplatform10("platformSignal 10", requestedLightState);
		
		gridPane.add(platformSignal10, 18, 12);
	}

	private void updatePlatformTrafficSignal11(int requestedLightState) {
		platformSignal11 = createTrafficSignalplatform11("platformSignal 11", requestedLightState);
		
		gridPane.add(platformSignal11, 21, 12);
	}

	private void updatePlatformTrafficSignal12(int requestedLightState) {
		platformSignal12 = createTrafficSignalplatform12("platformSignal 12", requestedLightState);
		
		gridPane.add(platformSignal12, 24, 12);
	}

	private void updatePlatformTrafficSignal13(int requestedLightState) {
		platformSignal13 = createTrafficSignalplatform13("platformSignal 13", requestedLightState);
		gridPane.add(platformSignal13, 27, 12);
	}

	private void updatePlatformTrafficSignal14(int requestedLightState) {
		platformSignal14 = createTrafficSignalplatform14("platformSignal 14", requestedLightState);
		
		gridPane.add(platformSignal14, 30, 12);
	}

	private void updatePlatformTrafficSignal15(int requestedLightState) {
		platformSignal15 = createTrafficSignalplatform15("platformSignal 15", requestedLightState);
		
		gridPane.add(platformSignal15, 33, 12);
	}

	private VBox createIconWithLabel(String imagePath, Label label, int cameraId, int locationId) {
		try {
			InputStream imageStream = getClass().getResourceAsStream(imagePath);

			if (imageStream == null) {
				System.out.println("Image not found: " + imagePath);
				return new VBox();
			}

			Image image = new Image(imageStream);
			ImageView imageView = new ImageView(image);

			imageView.setFitWidth(15);
			imageView.setFitHeight(15);
			VBox iconWithLabel = new VBox(5, imageView);
			iconWithLabel.setAlignment(javafx.geometry.Pos.CENTER);
			
			iconWithLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				openInputDialog(label.getText(), cameraId, locationId); 
			});
		
			return iconWithLabel;
		} catch (Exception e) {
			e.printStackTrace();
			return new VBox();
		}
	}

	
	
	private void openInputDialog(String cameraLabel, int cameraId, int locationId) {
		Stage inputDialog = new Stage();
		inputDialog.initModality(Modality.APPLICATION_MODAL);
		inputDialog.setTitle("Enter Vehicle Information");
		GridPane inputLayout = new GridPane();
		inputLayout.setHgap(10);
		inputLayout.setVgap(10);
		inputLayout.setPadding(new Insets(20));
		ComboBox<String> directionComboBox = new ComboBox<>();
		ComboBox<String> platformComboBox = new ComboBox<>();
		ComboBox<String> OccuppancyComboBox = new ComboBox<>();
		Label cameraLabelInfo = new Label("Camera ID: " + cameraId);
		Label locationLabelInfo = new Label("Location ID: " + locationId);
		cameraLabelInfo.setStyle("-fx-font-weight: bold");
		locationLabelInfo.setStyle("-fx-font-weight: bold");

		Label directionLabel = new Label("Direction:");

		ObservableList<String> items = FXCollections.observableArrayList("0", "1", "2");

		directionComboBox.setItems(items);
		directionComboBox.setValue("--Select--");
		directionComboBox.setOnAction(event -> {
			directionField = directionComboBox.getValue();
		});
		
		Label occuppancyLabel = new Label("Occuppancy:");

		ObservableList<String> occuppancy = FXCollections.observableArrayList("0", "1");

		OccuppancyComboBox.setItems(occuppancy);
		OccuppancyComboBox.setValue("--Select--");
		OccuppancyComboBox.setOnAction(event -> {
			occuppancyField = OccuppancyComboBox.getValue();
		});
		
		
		Label platformLabel = new Label("Bus from which platform:");

		ObservableList<String> platforms = FXCollections.observableArrayList("LEB","REB","LPB","RPB", "P1B","P2B","P3B","P4B","P5B","P6B","P7B","P8B","P9B","P10B","P11B","P12B","P13B","P14B","P15B","EEB");

		platformComboBox.setItems(platforms);
		platformComboBox.setValue("--Select--");
		platformComboBox.setOnAction(event -> {
			platformField = platformComboBox.getValue();
		});

		Label licensePlateCharactersLabel = new Label("License Plate Characters:");
		TextField licensePlateCharactersField = new TextField();
		
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> {
			String licensePlateCharacters = licensePlateCharactersField.getText();
			if(locationId==1) {
				LEB.setVisible(true);
				EEB.setVisible(false);
			}
			if(locationId==2) {
				REB.setVisible(true);
				EEB.setVisible(false);
			}
			if(locationId==3) {
				LPB.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==4) {
				RPB.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==5) {
				P1B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==6) {
				P2B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==7) {
				P3B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==8) {
				P4B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==9) {
				P5B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==10) {
				P6B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==11) {
				P7B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==12) {
				P8B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==13) {
				P9B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
			}
			if(locationId==14) {
				P10B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==15) {
				P11B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==16) {
				P12B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==17) {
				P13B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==18) {
				P14B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			if(locationId==19) {
				P15B.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
				EEB.setVisible(false);
			}
			
			if(locationId==20) {
				EEB.setVisible(true);
				imageViewMap.get(platformField).setVisible(false);
			}

			if (cameraId == 1 || cameraId == 2 || cameraId==20) {
				sendToBusDetectionQueue(cameraId, directionField, licensePlateCharacters);
			} else if (cameraId == 3 || cameraId == 4) {
				sendToPassageDetectionQueue(cameraId, directionField, licensePlateCharacters);
			} else {
				sendToOccupancyDetectionQueue(cameraId, directionField,occuppancyField, licensePlateCharacters);
			}
			inputDialog.close();

		});

		// Add components to the layout
		inputLayout.add(cameraLabelInfo, 0, 0, 2, 1);
		inputLayout.add(locationLabelInfo, 1, 0, 2, 1);
		inputLayout.addRow(2, licensePlateCharactersLabel, licensePlateCharactersField);
		inputLayout.addRow(4, directionLabel, directionComboBox);
		if(cameraId!=1 && cameraId!=2 && cameraId!=20 && cameraId != 3 && cameraId != 4) {
			inputLayout.addRow(5, occuppancyLabel, OccuppancyComboBox);
		}
		if(cameraId!=1 && cameraId!=2) {
		inputLayout.addRow(6, platformLabel, platformComboBox);
		}
		inputLayout.add(submitButton, 1, 7);
		Scene inputScene = new Scene(inputLayout, 400, 250);
		inputDialog.setScene(inputScene);
		inputDialog.show();
	}
	

	private String sendToBusDetectionQueue(int cameraId, String direction, String licensePlateCharacters) {

		byte[] messageBytes;

		try {
			ANPRBusDetectionReq anprMsg = new ANPRBusDetectionReq();

			anprMsg.setMsgSequenceNumber((short) seqNo.get());
			anprMsg.setCameraID((byte) cameraId);
			anprMsg.setLocationID((byte) cameraId);
			anprMsg.setTimeStamp((int) (System.currentTimeMillis() / 1000));
			anprMsg.setLicensePlateCharacters(licensePlateCharacters.getBytes());
			anprMsg.setLicensePlateLength((byte) anprMsg.getLicensePlateCharacters().length);
			anprMsg.setDirection((byte) Integer.parseInt(direction));
			anprMsg.setDetectionID((int) detectionId.getAndIncrement());

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(url);
			factory.setPort(port);
			factory.setUsername(user);
			factory.setPassword(pwd);

			Connection connection;
			Channel channel;
			connection = factory.newConnection();
			channel = connection.createChannel();

			RabbitMessage rm = new RabbitMessage();
			rm.exchange = exchange;
			rm.routingKey = busDetectionRoutingkey;
			rm.msgType = busDetectionMessageType;

			OBCSConnectMessageBuffer buffer = new OBCSConnectMessageBuffer(null);

			try {
				anprMsg.loadStreamData(buffer);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			messageBytes = buffer.getDataBytes();
			ByteBuffer messageBuf = ByteBuffer.wrap(messageBytes);
			rm.messageBytes = messageBuf;
			channel.basicPublish(rm.exchange, rm.routingKey, null, messageBytes);

		} catch (IOException | TimeoutException e1) {
			e1.printStackTrace();
		}
		return "Sent message to Bus detection queue";
	}

	private String sendToPassageDetectionQueue(int cameraId, String direction, String licensePlateCharacters) {

		byte[] messageBytes;

		try {
			ANPRPassageDetectionReq anprMsg = new ANPRPassageDetectionReq();

			anprMsg.setMsgSequenceNumber((short) seqNo.get());
			anprMsg.setCameraID((byte) cameraId);
			anprMsg.setLocationID((byte) cameraId);
			anprMsg.setTimeStamp((int) (System.currentTimeMillis() / 1000));
			anprMsg.setLicensePlateCharacters(licensePlateCharacters.getBytes());
			anprMsg.setLicensePlateLength((byte) anprMsg.getLicensePlateCharacters().length);
			anprMsg.setDirection((byte) Integer.parseInt(direction));
			anprMsg.setDetectionID((int) detectionId.getAndIncrement());

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(url);
			factory.setPort(port);
			factory.setUsername(user);
			factory.setPassword(pwd);

			Connection connection;
			Channel channel;
			connection = factory.newConnection();
			channel = connection.createChannel();

			RabbitMessage rm = new RabbitMessage();
			rm.exchange = exchange;
			rm.routingKey = pasageDetectionRoutingkey;
			rm.msgType = passageDetectionMessageType;

			OBCSConnectMessageBuffer buffer = new OBCSConnectMessageBuffer(null);

			try {
				anprMsg.loadStreamData(buffer);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			messageBytes = buffer.getDataBytes();
			ByteBuffer messageBuf = ByteBuffer.wrap(messageBytes);
			rm.messageBytes = messageBuf;
			channel.basicPublish(rm.exchange, rm.routingKey, null, messageBytes);

		} catch (IOException | TimeoutException e1) {
			e1.printStackTrace();
		}
		return "Sent message to Passage detection queue";
	}

	private String sendToOccupancyDetectionQueue(int cameraId, String direction,String occuppancyField, String licensePlateCharacters) {

		byte[] messageBytes;

		try {
			ANPROccupancyDetection anprMsg = new ANPROccupancyDetection();

			anprMsg.setMsgSequenceNumber((short) seqNo.get());
			anprMsg.setCameraID((byte) cameraId);
			anprMsg.setLocationID((byte) cameraId);
			anprMsg.setTimeStamp((int) (System.currentTimeMillis() / 1000));
			anprMsg.setLicensePlateCharacters(licensePlateCharacters.getBytes());
			anprMsg.setLicensePlateLength((byte) anprMsg.getLicensePlateCharacters().length);
			anprMsg.setDirection((byte) Integer.parseInt(direction));
			anprMsg.setDetectionID((int) detectionId.getAndIncrement());
			anprMsg.setOccuppancy((byte) Integer.parseInt(occuppancyField));

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(url);
			factory.setPort(port);
			factory.setUsername(user);
			factory.setPassword(pwd);

			Connection connection;
			Channel channel;
			connection = factory.newConnection();
			channel = connection.createChannel();

			RabbitMessage rm = new RabbitMessage();
			rm.exchange = exchange;
			rm.routingKey = occupancyDetectionRoutingkey;
			rm.msgType = occupancyDetectionMessageType;

			OBCSConnectMessageBuffer buffer = new OBCSConnectMessageBuffer(null);

			try {
				anprMsg.loadStreamData(buffer);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			messageBytes = buffer.getDataBytes();
			ByteBuffer messageBuf = ByteBuffer.wrap(messageBytes);
			rm.messageBytes = messageBuf;
			channel.basicPublish(rm.exchange, rm.routingKey, null, messageBytes);

		} catch (IOException | TimeoutException e1) {
			e1.printStackTrace();
		}
		return "Sent message to Occuppancy detection queue";
	}
	
	private String sendAckMessage(short seqNo, byte signalId, byte currentLightState, byte statusCode) throws Exception {

		byte[] messageBytes;

		try {
			 DBusUpdateTrafficSignalAck ack = new DBusUpdateTrafficSignalAck();
			  OBCSConnectMessageBuffer bufferAck = new OBCSConnectMessageBuffer(null);
			  ack.loadStreamData(bufferAck);
			  ack.setMsgSequenceNumber(seqNo);
			  ack.setsignalID(signalId);
			  ack.setCurrentLightState(currentLightState);
			  ack.setStatusCode(statusCode);
			  

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(url);
			factory.setPort(port);
			factory.setUsername(user);
			factory.setPassword(pwd);

			Connection connection;
			Channel channel;
			connection = factory.newConnection();
			channel = connection.createChannel();

			RabbitMessage rm = new RabbitMessage();
			rm.msgType = String.valueOf(ack.getMessageID());
			//rm.sourceAppID = String.valueOf(message.getSourceAppID());
			rm.sourceAppID = Byte.toString(signalId);	
			rm.destAppID = null;
			rm.destAppID = "17";
			rm.routingKey = rm.sourceAppID+"."+rm.destAppID+"."+rm.msgType;
			

			OBCSConnectMessageBuffer buffer = new OBCSConnectMessageBuffer(null);

			try {
				ack.loadStreamData(buffer);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			messageBytes = buffer.getDataBytes();
			ByteBuffer messageBuf = ByteBuffer.wrap(messageBytes);
			rm.messageBytes = messageBuf;
			channel.basicPublish(exchange, rm.routingKey, null, messageBytes);
			 //RabbitMQBroker rmqBroker = new RabbitMQBroker(null); 
			 //rmqBroker.sendMessageWithRoutingKey(rm);
			 //channel.basicPublish(rm.exchange, rm.routingKey, null, messageBytes);

		} catch (IOException | TimeoutException e1) {
			e1.printStackTrace();
		}
		return "Sent message to Occuppancy detection queue";
	}

	public static void main(String[] args) {
		launch(args);
	}

}
