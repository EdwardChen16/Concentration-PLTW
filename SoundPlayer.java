import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;

public class SoundPlayer extends Application {

    @Override
    public void start(Stage primaryStage) {
        playSound("audio/sound.mp3"); // Ensure the file is in src/main/resources/audio/
    }

    private void playSound(String sound) {
        URL file = getClass().getResource(sound);
        if (file == null) {
            System.err.println("Error: Sound file not found!");
            return;
        }

        Media media = new Media(file.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args); // Ensures JavaFX thread runs
    }
}
