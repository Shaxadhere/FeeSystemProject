package com.muxad.app.feesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LinkSentActivity extends AppCompatActivity {

    TextView LoginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_sent);

        LoginUser = findViewById(R.id.btnLoginUser);

        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LinkSentActivity.this, LoginActivity.class);
                LinkSentActivity.this.startActivity(intent);
            }
        });
    }
}