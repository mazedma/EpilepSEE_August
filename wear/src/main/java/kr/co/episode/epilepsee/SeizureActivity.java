package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.episode.epilepsee.databinding.ActivitySeizureBinding;

public class SeizureActivity extends Activity {

    private ActivitySeizureBinding binding;

    private Handler handler;
    private Runnable runnable;

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updateTime = 0L;

    private Date seizureTime = null;
    private long seizureDuration = 0L;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeizureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new Handler();

        binding.startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    // Start button action
                    if (startTime == 0L) {
                        startTime = System.currentTimeMillis();
                        seizureTime = new Date(startTime);
                    } else {
                        timeSwapBuff = 0L;
                        startTime = System.currentTimeMillis();
                        seizureTime = new Date(startTime);
                    }
                    handler.postDelayed(runnable, 0);
                    binding.startStopButton.setText("멈춤");
                } else {
                    // Stop button action
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(runnable);
                    binding.startStopButton.setText("발작 시작");

                    if (seizureTime != null) {
                        // Calculate seizure duration
                        seizureDuration = System.currentTimeMillis() - seizureTime.getTime();

                        // Open new activity to display seizure start and duration
                        Intent intent = new Intent(SeizureActivity.this, SeizureCompleteActivity.class);
                        intent.putExtra("seizureTime", dateFormatter.format(seizureTime));
                        intent.putExtra("seizureDuration", formatTime(seizureDuration));
                        startActivity(intent);
                        finish();
                    }
                }
                isRunning = !isRunning;
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                timeInMilliseconds = System.currentTimeMillis() - startTime;
                updateTime = timeSwapBuff + timeInMilliseconds;
                binding.timerTextview.setText(formatTime(updateTime));
                handler.postDelayed(this, 0);
            }
        };
    }

    private String formatTime(long time) {
        int milliseconds = (int) (time % 1000);
        int seconds = (int) ((time / 1000) % 60);
        int minutes = (int) ((time / (1000 * 60)) % 60);

        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
