package com.vinay.phonepequiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vinay.phonepequiz.adapters.GridViewAnswerAdapter;
import com.vinay.phonepequiz.adapters.GridViewSuggestAdapter;
import com.vinay.phonepequiz.util.Utility;
import com.squareup.picasso.Picasso;
//import com.vinay.phonepequiz.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;

    public GridView gridViewAnswer,gridViewSuggest;
    private Button btnSubmit;
    private ImageView imgLogo;


//    private ActivityMainBinding binding;

    public char[] answer;
    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        gridViewAnswer = findViewById(R.id.gridViewAnswer);
        gridViewSuggest = findViewById(R.id.gridViewSuggest);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgLogo = findViewById(R.id.imgLogo);


        setupList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result="";
                for (int i = 0; i< Utility.user_submit_answer.length; i++)
                    result+=String.valueOf(Utility.user_submit_answer[i]);
                if (result.equals(correct_answer))
                {
                    Toast.makeText(MainActivity.this, "Correct.  Answer is : "+result, Toast.LENGTH_SHORT).show();
                    Utility.count = 0;
                    Utility.user_submit_answer = new char[correct_answer.length()];

                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource,getApplicationContext(),MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Incorrect!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        Random random = new Random();

        try {
            String json = Utility.getJsonConfig(this);
            JSONArray jsonArray = new JSONArray(json);

//            int imageSelected = image_list[random.nextInt(image_list.length)];
//            imgViewQuestion.setImageResource(imageSelected);

            JSONObject jsonObject =  jsonArray.getJSONObject(random.nextInt(jsonArray.length()));
            Picasso.get().load(jsonObject.getString("imgUrl")).into(imgLogo);


            correct_answer = jsonObject.getString("name");
            answer = jsonObject.getString("name").toCharArray();
            Utility.user_submit_answer = new char[answer.length];
            suggestSource.clear();

            for (char item : answer) {
                suggestSource.add(String.valueOf(item));
            }

            for (int i = answer.length; i < answer.length * 2; i++)
                suggestSource.add(Utility.alphabest_character[random.nextInt(Utility.alphabest_character.length)]);

            Collections.shuffle(suggestSource);

            answerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
            suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);
            answerAdapter.notifyDataSetChanged();
            suggestAdapter.notifyDataSetChanged();

            gridViewSuggest.setAdapter(suggestAdapter);
            gridViewAnswer.setAdapter(answerAdapter);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for (int i=0; i<answer.length;i++)
            result[i]=' ';
        return result;
    }
}
