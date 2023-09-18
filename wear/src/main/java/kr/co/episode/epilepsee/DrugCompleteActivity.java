package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.co.episode.epilepsee.databinding.ActivityDrugCompleteBinding;

public class DrugCompleteActivity extends Activity {

    private ActivityDrugCompleteBinding binding;

    // data layer
    int receivedMessageNumber = 1;
    int sentMessageNumber=1;

    // 시간 출력
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    Date mDate = ((DrugActivity) DrugActivity.context_drug).recordedTimeDrug;
    String getTime = simpleDate.format(mDate);

    // 복용 시기
    private String checkedRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrugCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // DrugActivity에서 선택된 라디오버튼의 text값 가져오기
        checkedRadio = getIntent().getStringExtra("checkedRadioDrug"); // Intent로 전달된 값 추출
        Log.d("TAG", "checkedRadio: " + checkedRadio);

        binding.textView6.setText(checkedRadio); //text값 출력

        // 기록된 시간 출력
        binding.textView8.setText(getTime);

        binding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String onClickMessage = "<약물 복용>\n"+checkedRadio + "\n복용 시간: " + getTime;
                String path = "/my_path";

                Task<List<Node>> nodeListTask = Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
                nodeListTask.addOnSuccessListener(new OnSuccessListener<List<Node>>() {
                    @Override
                    public void onSuccess(List<Node> nodes) {
                        for (Node node : nodes) {
                            Task<Integer> sendMessageTask = Wearable.getMessageClient(DrugCompleteActivity.this).sendMessage(node.getId(), path, onClickMessage.getBytes());
                            sendMessageTask.addOnSuccessListener(new OnSuccessListener<Integer>() {
                                @Override
                                public void onSuccess(Integer result) {
                                    // 메시지 전송 성공 시 수행할 작업
                                }
                            });
                            sendMessageTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    // 메시지 전송 실패 시 수행할 작업
                                }
                            });
                        }
                    }
                });
            }
        });

//Register to receive local broadcasts, which we'll be creating in the next step//
        IntentFilter newFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);
    }
    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//Display the following when a new message is received//
            String onMessageReceived = "I just received a message from the handheld " + receivedMessageNumber++;
            binding.textView9.setText(onMessageReceived);

        }
    }
    class SendMessage extends Thread {
        String path;
        String message;
        //Constructor for sending information to the Data Layer//
        SendMessage(String p, String m) {
            path = p;
            message = m;
        }
        public void run() {
//Retrieve the connected devices//
            Task<List<Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {
//Block on a task and get the result synchronously//
                List<Node> nodes = Tasks.await(nodeListTask);
                for (Node node : nodes) {
//Send the message///
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(DrugCompleteActivity.this).sendMessage(node.getId(), path, message.getBytes());
                    try {
                        Integer result = Tasks.await(sendMessageTask);
//Handle the errors//
                    } catch (ExecutionException exception) {
//TO DO//
                    } catch (InterruptedException exception) {
//TO DO//
                    }
                }
            } catch (ExecutionException exception) {
//TO DO//
            } catch (InterruptedException exception) {
//TO DO//
            }
        }
    }
}