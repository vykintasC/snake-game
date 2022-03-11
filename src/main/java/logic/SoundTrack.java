package logic;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundTrack {

    private Long currentFrame;
    private Clip clip;
    private AudioInputStream audioInputStream;

    /**
     * Sets the current soundtrack to another one.
     *
     * @param file input wav file which contains the soundtrack audio.
     */
    public void setFilePath(String file) throws Exception {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    /**
     * Loops the soundtrack repeatedly.
     */
    public void loopAudio() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the soundtrack from playing.
     */
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    /**
     * Method to play the audio.
     */
    public void play() {
        clip.start();
    }

    /**
     * Method to pause the audio.
     */
    public void pause() {
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
    }

    /**
     * Method to resume the audio.
     */
    public void resumeAudio(String file) throws Exception {
        clip.close();
        resetAudio(file);
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    /**
     * Method which resets the audio stream.
     */
    public void resetAudio(String file) throws Exception {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(file).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    /**
     * Sets the soundtrack to a given level.
     * @param volume float between 0f and 1f, depending on the volume.
     */
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

} 