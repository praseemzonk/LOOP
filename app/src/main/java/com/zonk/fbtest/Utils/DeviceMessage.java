package com.zonk.fbtest.Utils;

import android.os.Build;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.zonk.fbtest.Fbtest;

import java.nio.charset.Charset;

public class DeviceMessage {
    private static final Gson gson = new Gson();

    private final String mUUID;
    private final String mMessageBody;

    public static Message newNearbyMessage(String instanceId) {
        DeviceMessage deviceMessage = new DeviceMessage(instanceId);
        return new Message(gson.toJson(deviceMessage).getBytes(Charset.forName("UTF-8")));
    }

    /**
     * Creates a {@code DeviceMessage} object from the string used to construct the payload to a
     * {@code Nearby} {@code Message}.
     */
    public static DeviceMessage fromNearbyMessage(Message message) {
        String nearbyMessageString = new String(message.getContent()).trim();
        return gson.fromJson(
                (new String(nearbyMessageString.getBytes(Charset.forName("UTF-8")))),
                DeviceMessage.class);
    }

    private DeviceMessage(String uuid) {
        mUUID = uuid;
        mMessageBody ="hello";

        // TODO(developer): add other fields that must be included in the Nearby Message payload.
    }

    public String getMessageBody() {
        return mMessageBody;
    }
}