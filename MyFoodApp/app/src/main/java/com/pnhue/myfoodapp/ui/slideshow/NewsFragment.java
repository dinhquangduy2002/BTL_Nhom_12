package com.pnhue.myfoodapp.ui.slideshow;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pnhue.myfoodapp.R;
import com.pnhue.myfoodapp.adapters.NewsAdapter;
import com.pnhue.myfoodapp.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    FirebaseFirestore db;
    RecyclerView newsRec;
    List<NewsModel> newsModelList;
    NewsAdapter newsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        db = FirebaseFirestore.getInstance();

        //Category
        newsRec = root.findViewById(R.id.new_rec);
        newsRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        newsModelList = new ArrayList<>();
        db.collection("News")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewsModel newsModel = document.toObject(NewsModel.class);
                                newsModelList.add(newsModel);
                                //Log.e("Tag", document.getId() + "=>" + document.getData());
                            }
                            newsAdapter = new NewsAdapter(getActivity(), newsModelList);
                            newsRec.setAdapter(newsAdapter);
                        } else {
                            Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }
}