package com.ivan.game.managers;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class SoundManager implements Runnable
{
    Thread runner;
    String name = null;
    Sequencer sound;
    Sequence soundname;
    File file;
    void play(String n)
    {
        name = n;
        if (runner == null)
        {
            runner = new Thread(this);
            runner.start();
        }
    }
    void stop()
    {
        if (runner != null)
            sound.close();
        runner = null;
    }
    
    public void run()
    {
        try
        {
            sound = MidiSystem.getSequencer();
            file = new File(name);
            soundname = MidiSystem.getSequence(file);
            sound.open();
            sound.setSequence(soundname);
            sound.setLoopCount(sound.LOOP_CONTINUOUSLY);
            sound.start();
            while (true)
            {
                while (sound.isRunning())
                {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                        System.out
                                .println("some problems happended in this programe");
                    }
                } 
            }
        }
        catch (Exception e)
        {
        }
    }
}
