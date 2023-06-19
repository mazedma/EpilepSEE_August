package kr.co.episode.epilepsee;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

    // 시간 출력
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    Date mDate = ((DrugActivity) DrugActivity.context_drug).recordedTimeDrug;
    String getTime = simpleDate.format(mDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrugCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SideEffectActivity에서 선택된 라디오버튼의 text값 가져오기
        String checkedRadio = ((DrugActivity) DrugActivity.context_drug).checkedRadioDrug;
        binding.textView6.setText(checkedRadio); //text값 출력

        // 기록된 시간 출력
        binding.textView8.setText(getTime);

        //Use the same path//
        String datapath = "/my_path";
        new SendMessage(datapath, checkedRadio, getTime).start();
    }

    class SendMessage extends Thread {
        String path;
        String message;
        String checkedRadio;
        String getTime;

        //Constructor for sending information to the Data Layer//
        SendMessage(String p, String checkedRadio, String getTime) {
            path = p;
            this.checkedRadio = checkedRadio;
            this.getTime = getTime;
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
                    String fullMessage = checkedRadio + "\n복용 시간: " + getTime;
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(DrugCompleteActivity.this).sendMessage(node.getId(), path, fullMessage.getBytes());
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
