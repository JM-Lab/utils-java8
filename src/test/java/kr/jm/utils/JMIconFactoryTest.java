package kr.jm.utils;

import static java.util.stream.Collectors.toList;

import java.nio.file.Path;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import kr.jm.utils.helper.JMPath;

public class JMIconFactoryTest extends Application {

	private static JMIconFactory iconFactory = new JMIconFactory();

	@Override
	public void start(Stage stage) {

		ObservableList<String> data = FXCollections.observableArrayList("/dev",
				"", "/etc", "lkajslkdjfl", "?", "/Users/1001969/.ssh", ".ssh",
				"alksjd.htm", "cj.htm", "zzz.app", "zzz.app", "/home", "/",
				"user", "/bin", "a.msg", "a1.msg", "a1a.msg", "b" + ".txt",
				"c.pdf", "d.html", "a.html", "e.png", "f.zip", "g.docx",
				"h" + ".xlsx", "i.pptx", ".i.pptx");
		HBox hbox = new HBox();
		VBox vbox1 = new VBox();
		ListView<String> list = new ListView<String>();
		vbox1.getChildren().addAll(list);
		list.setItems(data);
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new AttachmentListCell();
			}
		});
		VBox.setVgrow(list, Priority.ALWAYS);

		VBox vbox2 = new VBox();
		ListView<String> list2 = new ListView<String>();
		vbox2.getChildren().addAll(list2);
		list2.setItems(data);
		list2.setCellFactory(
				new Callback<ListView<String>, ListCell<String>>() {
					@Override
					public ListCell<String> call(ListView<String> list) {
						return new AttachmentListCell2();
					}
				});
		VBox.setVgrow(list2, Priority.ALWAYS);

		VBox vbox3 = new VBox();
		ListView<String> list3 = new ListView<String>();
		vbox3.getChildren().addAll(list3);
		list3.setItems(data);
		list3.setCellFactory(
				new Callback<ListView<String>, ListCell<String>>() {
					@Override
					public ListCell<String> call(ListView<String> list) {
						return new AttachmentListCell3();
					}
				});
		VBox.setVgrow(list3, Priority.ALWAYS);

		VBox vbox4 = new VBox();
		ListView<String> list4 = new ListView<String>();
		vbox4.getChildren().addAll(list4);
		list4.setItems(data);
		list4.setCellFactory(
				new Callback<ListView<String>, ListCell<String>>() {
					@Override
					public ListCell<String> call(ListView<String> list) {
						return new AttachmentListCell4();
					}
				});
		VBox.setVgrow(list4, Priority.ALWAYS);

		VBox vbox5 = new VBox();
		ListView<Path> list5 = new ListView<>();
		vbox5.getChildren().addAll(list5);
		list5.setItems(FXCollections.observableArrayList(JMPath
				.getChildrenPathStream(JMPath.getPath("/")).collect(toList())));
		list5.setCellFactory(new Callback<ListView<Path>, ListCell<Path>>() {

			@Override
			public ListCell<Path> call(ListView<Path> param) {
				return new ListCell<Path>() {
					@Override
					protected void updateItem(Path item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							ImageView imageView = iconFactory
									.getImageViewOfIconInOS(item);
							setGraphic(imageView);
							setText(JMPath.getPathNameInOS(item));
						}
					}
				};
			}
		});
		VBox.setVgrow(list5, Priority.ALWAYS);

		VBox vbox6 = new VBox();
		ListView<Path> list6 = new ListView<>();
		vbox6.getChildren().addAll(list6);
		list6.setItems(FXCollections.observableArrayList(JMPath
				.getChildrenPathStream(JMPath.getPath("/")).collect(toList())));
		list6.setCellFactory(new Callback<ListView<Path>, ListCell<Path>>() {

			@Override
			public ListCell<Path> call(ListView<Path> param) {
				return new ListCell<Path>() {
					@Override
					protected void updateItem(Path item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							ImageView imageView = iconFactory
									.getImageViewOfIconInOS(item);
							setGraphic(imageView);
							setText(JMPath.getLastName(item));
						}
					}
				};
			}
		});
		VBox.setVgrow(list6, Priority.ALWAYS);

		hbox.getChildren().add(vbox1);
		hbox.getChildren().add(vbox2);
		hbox.getChildren().add(vbox3);
		hbox.getChildren().add(vbox4);
		hbox.getChildren().add(vbox5);
		hbox.getChildren().add(vbox6);

		Scene scene = new Scene(hbox, 1200, 800);
		stage.setScene(scene);
		stage.setTitle("ListViewSample");

		stage.show();
	}

	private static class AttachmentListCell extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
				setText(null);
			} else {
				Path path = JMPath.getPath(item);
				Image fxImage = SwingFXUtils.toFXImage(
						iconFactory.buildBufferedImageOfIconInOS(path), null);
				ImageView imageView = new ImageView(fxImage);
				if (JMPath.HiddenFilter.test(path))
					imageView.setOpacity(0.5);
				setGraphic(imageView);
				setText(item);
			}
		}
	}

	private static class AttachmentListCell2 extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
				setText(null);
			} else {
				Image fxImage = iconFactory
						.getFxImageOfIconInOS(JMPath.getPath(item));
				ImageView imageView = new ImageView(fxImage);
				setGraphic(imageView);
				setText(item);
			}
		}
	}

	private static class AttachmentListCell3 extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
				setText(null);
			} else {
				Image fxImage = SwingFXUtils.toFXImage(iconFactory
						.getCachedBufferedImageOfIconInOS(JMPath.getPath(item)),
						null);
				ImageView imageView = new ImageView(fxImage);
				setGraphic(imageView);
				setText(item);
			}
		}
	}

	private static class AttachmentListCell4 extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
				setText(null);
			} else {
				ImageView imageView = iconFactory
						.getImageViewOfIconInOS(JMPath.getPath(item));
				setGraphic(imageView);
				setText(item);
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
