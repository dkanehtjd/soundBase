package com.defaultcompany.soundbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MusicProfile> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    MediaPlayer mediaPlayer;
    Button btn_play;
    Button btn_stop;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        recyclerView = findViewById(R.id.recyclerView); //아디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //MusicProfile 객체를 담을 Array List(어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); //firebase database 연동

        databaseReference = database.getReference("/soundBase/MusicProfile"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //firebase database 데이터 받아오는 곳
                arrayList.clear(); //기존 배열리스트 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MusicProfile musicProfile = snapshot.getValue((MusicProfile.class)); //만들어뒀던 객체에 데이터 담는다.
                    arrayList.add(musicProfile); //담은 데이터들은 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던 중 에러 발생 시
                Log.e("HomeActivity", String.valueOf(databaseError.toException())); //에러문 출력

            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

        Button btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });


    }
}