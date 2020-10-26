package com.mvp.util;

import javazoom.jl.player.Player;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.Lock;

public class MusicPlayer {
    //	private AudioInputStream audioIn;
//	private SourceDataLine sourceDataLine;
    // wav文件的路径
    private File file;
    // 是否循环播放
    private boolean isLoop = false;
    // 是否正在播放
    private boolean isPlaying;

    private PlayWavThread playWavThread;
    private PlayMp3Thread playMp3Thread;

    public MusicPlayer(String srcPath) {
        file = new File(srcPath);
    }

    /**
     * 播放音乐
     */
    public void play_wav() {
        playWavThread = new PlayWavThread();
        playWavThread.start();
    }

    public void play_mp3() {
        playMp3Thread = new PlayMp3Thread();
        playMp3Thread.start();
    }

    /**
     * 结束音乐（并非暂停）
     */
    public void closeMp3() {
        playMp3Thread.close();
    }

    /**
     * Applet播放 只能播放一次
     */
    private class PlayWavThread extends Thread {

        @Override
        public void run() {
            AudioClip audioClip = null;
                try {
                    audioClip = Applet.newAudioClip(file.toURI().toURL());
                    audioClip.play();
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
        /**
         * 使用第三方包播放音乐
         * 只包含简单的播放暂停。默认循环播放，无法单曲播放
         */
        public class PlayMp3Thread extends Thread{
            Player p = null;
            @Override
            public void run() {
                try{
                    do{
                        FileInputStream fis = new FileInputStream(file);
                        p = new Player(fis);
                        p.play();
                    } while (isLoop && isPlaying);

                }catch(Exception e){
                    System.out.println("加载文件失败");
                }
            }
            public void close(){
                p.close();
                isPlaying = false;
            }
        }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }
}