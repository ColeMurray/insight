package com.ubiqlog.ubiqlogwear.utils;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Cole Murray
 */

/* This class sends a message to the handheld to sync history
    Handheld will receive message and send data information back
 */
public class WearableSendSync  {
    private static final String TAG = WearableSendSync.class.getSimpleName();
    public static final String START_HIST_SYNC = "/start/HeartSync";
    public static final String START_ACTV_SYNC = "/start/ActvSync";

    public static void sendSyncToDevice(GoogleApiClient mGoogleApiClient, String key){
        Log.d(TAG, "Sending sync message");
        Collection<String> nodes = getNodes(mGoogleApiClient);
        for (String n : nodes){
            sendSyncMessage(mGoogleApiClient, n, key);
        }
    }

    private static void sendSyncMessage (GoogleApiClient mGoogleApiClient,String nodeId, String key){
        Wearable.MessageApi.sendMessage(
                mGoogleApiClient, nodeId, key, new byte[0]).setResultCallback(
                new ResultCallback<MessageApi.SendMessageResult>() {
                    @Override
                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                        if (!sendMessageResult.getStatus().isSuccess()) {
                            Log.e(TAG, "Failed to send message with status code: "
                                    + sendMessageResult.getStatus().getStatusCode());
                        }
                    }
                }
        );
    }

    private static Collection<String> getNodes(GoogleApiClient mGoogleApiClient) {
        HashSet<String> results = new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }
}

