package kr.co.episode.epilepsee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import kr.co.episode.epilepsee.databinding.ActivitySummaryBinding;

public class SummaryActivity extends AppCompatActivity {

    private ActivitySummaryBinding binding;
    private Handler myHandler;
    private int receivedMessageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                String messageText = stuff.getString("messageText");
                messageText(messageText);
                return true;
            }
        });

        // Register to receive local broadcasts
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    public void messageText(String newInfo) {
        if (newInfo != null && !newInfo.isEmpty()) {
            binding.textView18.append("\n" + newInfo);
        }
    }

    // Define a nested class that extends BroadcastReceiver
    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Upon receiving each message from the wearable, display the following text
            String receivedMessage = intent.getStringExtra("message");
            messageText(receivedMessage);

        }
    }
}
