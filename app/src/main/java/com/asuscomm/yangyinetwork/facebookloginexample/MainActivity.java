package com.asuscomm.yangyinetwork.facebookloginexample;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends FragmentActivity {
    LoginButton btnRegisterFacebook;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());

        btnRegisterFacebook = (LoginButton)findViewById(R.id.btnRegisterFacebook);

        callbackManager = CallbackManager.Factory.create();
        btnRegisterFacebook.setReadPermissions(Arrays.asList("public_profile, email"));
        btnRegisterFacebook.registerCallback(callbackManager, callback);
        btnRegisterFacebook.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onCancel() {
            Log.d("jyp","로그인을 취소 하였습니다!");
        }
        @Override
        public void onError(FacebookException e) {
            Log.d("jyp","소셜 로그인 오류입니다.");
        }
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            String socialId = null, name = null;
                            try {
                                socialId = object.getString("id");
                                name = object.getString("name");
                            }catch (Exception e){}

                            register(socialId, name);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name");
            request.setParameters(parameters);
            request.executeAsync();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void register(String USER_ID, String USER_NAME){
        Log.d("jyp",USER_ID);
        Log.d("jyp",USER_NAME);
    }
}
