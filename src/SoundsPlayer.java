import java.io.*;
import javax.sound.sampled.*;

/* Enum que auxilia na execução
nos sons do game:
    Para adicionar um som basta um campo no ENUM com o parâmetro da localização do .wav do som
    Para executar o som basta chamar SoundsPlayer.NOMEDOSOM.play();
 */

public enum SoundsPlayer {
    DEAL("sounds/deal.wav"),
    WIN("sounds/win.wav"),
    LOSE("sounds/lose.wav"),
    SHUFFLE("sounds/shuffle.wav"),
    CLICK("sounds/click.wav");

    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    // Cada som é guardado em seu próprio clip.
    private Clip clip;

    // Construtor que coloca cada elemento do enum em seu próprio arquivo de som;
    SoundsPlayer(String soundFileName) {
        try {
            // Lê o arquivo do HD.
            InputStream url = this.getClass().getClassLoader().getResourceAsStream(soundFileName);
            // Seta o AudioInputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Pega os recursos do clip
            clip = AudioSystem.getClip();
            // Abre o clip e carrega os exemplos do AudioInputStream
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Executa o som
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();   // Para o som caso ele esteja tocando.
            clip.setFramePosition(0); // Volta para o início
            clip.start();     // Começa a tocar
        }
    }

    public static void init() {
        values(); // chama o construtor de todos os elementos
    }
}
