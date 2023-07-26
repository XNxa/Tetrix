package fr.xnxa.tetrix;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SettingsControler implements Initializable {

	@FXML
	private Button btnApplyClose;

	@FXML
	private CheckBox cb_music;

	@FXML
	private CheckBox cb_speedincrease;

	@FXML
	private CheckBox cb_color;

	@FXML
	private ColorPicker cp_L;

	@FXML
	private ColorPicker cp_J;

	@FXML
	private ColorPicker cp_O;

	@FXML
	private ColorPicker cp_S;

	@FXML
	private ColorPicker cp_Z;

	@FXML
	private ColorPicker cp_T;

	@FXML
	private ColorPicker cp_I;

	@FXML
	private ImageView iv_I;

	@FXML
	private ImageView iv_J;

	@FXML
	private ImageView iv_L;

	@FXML
	private ImageView iv_O;

	@FXML
	private ImageView iv_S;

	@FXML
	private ImageView iv_T;

	@FXML
	private ImageView iv_Z;

	private SettingsRecorder mySettings;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			mySettings = new SettingsRecorder();
			String isMusicSelected = mySettings.getMusicSetting();
			String isSpeedSelected = mySettings.getSpeedIncreaseSetting();

			cb_music.setSelected(Boolean.valueOf(isMusicSelected));
			cb_speedincrease.setSelected(Boolean.valueOf(isSpeedSelected));

			// init color pickers
			initColorPickersFromProperties();
			initImageColors();
			activateColorCheckBox();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void closeWithoutSaving(ActionEvent event) {
		// System.out.println("Closing settings window without saving properties");
		Stage stage = (Stage) btnApplyClose.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onApplyClose(ActionEvent event) {
		// System.out.println("Closing settings window and saving properties");
		try {
			mySettings.SaveSettings();
			// System.out.println("Settings properties saved !");
		} catch (IOException e) {
			System.out.println("IOException when saving settings properties");
			e.printStackTrace();
		}
		Stage stage = (Stage) btnApplyClose.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onCBMusic(ActionEvent event) {
		// System.out.println("CB Music changed");
		boolean new_value = cb_music.isSelected();
		try {
			mySettings.changeSoundSetting(new_value);
		} catch (Exception e) {
			// if mySettings is not correctly initialised ... does not happen !!
			e.printStackTrace();
		}
	}

	@FXML
	void onCBSpeedIncrease(ActionEvent event) {
		// System.out.println("CB Speed changed");
		boolean new_value = cb_speedincrease.isSelected();
		try {
			mySettings.changeSpeedIncreaseSetting(new_value);
		} catch (Exception e) {
			// if mySettings is not correctly initialised ... does not happen !!
			e.printStackTrace();
		}

	}

	@FXML
	void onCBColor(ActionEvent event) {
		// System.out.println("Reset colors to default colors");
		// if checkbox is checked set all colors to standard.
		if (cb_color.isSelected()) {
			initColorPickers_StandardColors();
			initImageColors();
		}

	}

	@FXML
	void onColorPickerAction(ActionEvent event) {
		ColorPicker cp = (ColorPicker) event.getSource();
		if (cp != null) {
			// System.out.println("color picker changed " + cp.getId());
			Color color = cp.getValue();
			String id = cp.getId();
			String couleur = ColoredShapeGenerator.getWebColor(color, false);

			Image nv_img;
			switch (id) {
			case "cp_L":
				nv_img = ColoredShapeGenerator.getColoredImage("L", color);
				iv_L.setImage(nv_img);
				break;
			case "cp_J":
				nv_img = ColoredShapeGenerator.getColoredImage("J", color);
				iv_J.setImage(nv_img);
				break;
			case "cp_I":
				nv_img = ColoredShapeGenerator.getColoredImage("I", color);
				iv_I.setImage(nv_img);
				break;
			case "cp_T":
				nv_img = ColoredShapeGenerator.getColoredImage("T", color);
				iv_T.setImage(nv_img);
				break;
			case "cp_S":
				nv_img = ColoredShapeGenerator.getColoredImage("S", color);
				iv_S.setImage(nv_img);
				break;
			case "cp_Z":
				nv_img = ColoredShapeGenerator.getColoredImage("Z", color);
				iv_Z.setImage(nv_img);
				break;
			case "cp_O":
				nv_img = ColoredShapeGenerator.getColoredImage("O", color);
				iv_O.setImage(nv_img);
				break;
			default:
				System.out.println("default ????");
			}
			// save the new color value in props
			mySettings.saveNewColor(id, couleur);

			// color check-box must be unchecked if the new color values aren't standard.
			// If it isn't checked and the new color values are standard, there is also
			// somthing to do !
			activateColorCheckBox();
		}
	}

	private void activateColorCheckBox() {
		boolean standard = mySettings.isStandardColors();
		if (cb_color.isSelected() && !standard) {
			cb_color.setSelected(false);
		} else if (!cb_color.isSelected() && standard) {
			cb_color.setSelected(true);
		}
	}

	private void initImageColors() {
		Color col;
		col = mySettings.getColorForShape("I");
		iv_I.setImage(ColoredShapeGenerator.getColoredImage("I", col));
		col = mySettings.getColorForShape("O");
		iv_O.setImage(ColoredShapeGenerator.getColoredImage("O", col));
		col = mySettings.getColorForShape("J");
		iv_J.setImage(ColoredShapeGenerator.getColoredImage("J", col));
		col = mySettings.getColorForShape("T");
		iv_T.setImage(ColoredShapeGenerator.getColoredImage("T", col));
		col = mySettings.getColorForShape("S");
		iv_S.setImage(ColoredShapeGenerator.getColoredImage("S", col));
		col = mySettings.getColorForShape("Z");
		iv_Z.setImage(ColoredShapeGenerator.getColoredImage("S", col));
		col = mySettings.getColorForShape("L");
		iv_L.setImage(ColoredShapeGenerator.getColoredImage("L", col));
	}

	private void initColorPickersFromProperties() {
		cp_I.setValue(Color.valueOf(mySettings.getColor("cp_I")));
		cp_J.setValue(Color.valueOf(mySettings.getColor("cp_J")));
		cp_L.setValue(Color.valueOf(mySettings.getColor("cp_L")));
		cp_T.setValue(Color.valueOf(mySettings.getColor("cp_T")));
		cp_S.setValue(Color.valueOf(mySettings.getColor("cp_S")));
		cp_Z.setValue(Color.valueOf(mySettings.getColor("cp_Z")));
		cp_O.setValue(Color.valueOf(mySettings.getColor("cp_O")));
	}

	private void initColorPickers_StandardColors() {
		String c_I = mySettings.getStdColor("cp_I");
		String c_J = mySettings.getStdColor("cp_J");
		String c_L = mySettings.getStdColor("cp_L");
		String c_T = mySettings.getStdColor("cp_T");
		String c_S = mySettings.getStdColor("cp_S");
		String c_Z = mySettings.getStdColor("cp_Z");
		String c_O = mySettings.getStdColor("cp_O");

		cp_I.setValue(Color.valueOf(c_I));
		cp_J.setValue(Color.valueOf(c_J));
		cp_L.setValue(Color.valueOf(c_L));
		cp_T.setValue(Color.valueOf(c_T));
		cp_S.setValue(Color.valueOf(c_S));
		cp_Z.setValue(Color.valueOf(c_Z));
		cp_O.setValue(Color.valueOf(c_O));
		mySettings.saveNewColor("cp_I", c_I);
		mySettings.saveNewColor("cp_J", c_J);
		mySettings.saveNewColor("cp_L", c_L);
		mySettings.saveNewColor("cp_O", c_O);
		mySettings.saveNewColor("cp_T", c_T);
		mySettings.saveNewColor("cp_S", c_S);
		mySettings.saveNewColor("cp_Z", c_Z);
	}

}
