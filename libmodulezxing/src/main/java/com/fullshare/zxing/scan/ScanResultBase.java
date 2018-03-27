package com.fullshare.zxing.scan;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.fullshare.zxing.CaptureActivity;
import com.fullshare.zxing.R;
import com.fullshare.zxing.decoding.DecodeCallback;
import com.google.zxing.Result;

import java.io.IOException;

public class ScanResultBase {
    private static final long VIBRATE_DURATION = 200L;

    protected Activity mActivity;
    private MediaPlayer mPlayer;
    private AudioManager mAudioManager;
    private DecodeCallback decodeCallback;

    public ScanResultBase(Activity activity, DecodeCallback decodeCallback) {
        mActivity = activity;
        this.decodeCallback = decodeCallback;
    }

    public void doResult(final Result result) {
        playBeepSoundAndVibrate();
        decodeCallback.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onResult(result);
            }
        }, 500);
    }

    public void onResult(Result result) {

    }

    protected void playBeepSoundAndVibrate() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }

        try {
            mPlayer.reset();
            AssetFileDescriptor afd = mActivity.getResources().openRawResourceFd(R.raw.scan);
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepare();
            mPlayer.setOnCompletionListener(null);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            float volumeScale = getVolumeScale();
            mPlayer.setVolume(volumeScale, volumeScale);
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vibrator vibrator = (Vibrator) mActivity.getSystemService(CaptureActivity.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_DURATION);
    }

    private float getVolumeScale() {
        if (mAudioManager == null)
            mAudioManager = (AudioManager) mActivity.getSystemService(CaptureActivity.AUDIO_SERVICE);

        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumeScale = 1;
        if (currVolume > maxVolume / 3) {
            volumeScale = (maxVolume / 3.f) / currVolume;
        }
        return volumeScale;
    }

    protected void playAudio(int rawRes) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }

        try {
            mPlayer.reset();
            AssetFileDescriptor afd = mActivity.getResources().openRawResourceFd(rawRes);
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepare();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                }
            });
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setVolume(1, 1);
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (mPlayer != null)
            mPlayer.release();
        mPlayer = null;
        mAudioManager = null;
    }
}
