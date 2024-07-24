package MusicPlayer;

public abstract class MusicPlayer implements Runnable {
    private String fileName;
    private boolean running = true;

    public abstract float getVolume();
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MusicPlayer() {
    }
    public MusicPlayer(String fileName) {
        this.fileName = fileName;
    }

    public boolean isRunning() {
        return running;
    }

    public void changeState() {
        if(!running){
            running = true;
        }
        else{
            running = false;
        }
    }
    public abstract void changeMusic();
}
