package com.common.basecomponent.recorder;

import android.media.MediaRecorder;
import android.os.Handler;

import java.io.IOException;

public class AudioRecorder implements Runnable {
    private static final int MAX_TIME = 60 * 1000;
    private static final int START_COUNT_DOWN = 50 * 1000;
    private MediaRecorder mMediaRecorder;
    private Handler mHandler;
    private CallBack<Integer> mAmplitudeUpdate;
    private int mMaxLevel = 5;
    private CallBack<Long> mCountDown;
    private long mDuration;
    private String mSaveFile;
    private boolean isRecording;


    public AudioRecorder() {
        mMediaRecorder = new MediaRecorder();
        mHandler = new Handler();
    }

    public void startRecord(String fileDir, CallBack<Integer> amplitudeUpdate, CallBack<Long> countDown) {
        mAmplitudeUpdate = amplitudeUpdate;
        mCountDown = countDown;
        mSaveFile = String.format("%s/temp.m4a", fileDir);

        try {
            mMediaRecorder.reset();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mMediaRecorder.setAudioEncodingBitRate(16);
            mMediaRecorder.setAudioSamplingRate(44100);
            mMediaRecorder.setAudioChannels(1);
            mMediaRecorder.setOutputFile(mSaveFile);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            mDuration = System.currentTimeMillis();
            isRecording = true;
            updateMicStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        if (!isRecording)
            return;
        isRecording = false;
        mDuration = System.currentTimeMillis() - mDuration;
        mMediaRecorder.stop();
    }

    public boolean isRecording() {
        return isRecording;
    }

    public int getDuration() {
        return (int) mDuration;
    }

    public String getSaveFile() {
        return mSaveFile;
    }

    @Override
    public void run() {
        if (isRecording && mAmplitudeUpdate != null && mMediaRecorder != null) {
            mHandler.removeCallbacks(this);
            updateMicStatus();
        }
    }

    private void updateMicStatus() {
        int maxAmplitude = mMediaRecorder.getMaxAmplitude();
        int level = mMaxLevel * maxAmplitude / 32768 + 1;//32768 = 2 ^ 16 / 2; 位深度16
        mAmplitudeUpdate.doCallBack(level);

        long duration = System.currentTimeMillis() - mDuration;
        if (duration > START_COUNT_DOWN && mCountDown != null) {
            long leftTime = MAX_TIME - duration;
            if (leftTime > 0) {
                mCountDown.doCallBack(leftTime);
            } else {
                stopRecord();
            }
        }

        mHandler.postDelayed(this, 200);
    }

    public void onDestroy() {
        if (isRecording)
            stopRecord();
        if (mMediaRecorder != null)
            mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public int getMaxLevel() {
        return mMaxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.mMaxLevel = maxLevel;
    }
}
