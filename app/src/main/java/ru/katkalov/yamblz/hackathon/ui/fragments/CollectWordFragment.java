package ru.katkalov.yamblz.hackathon.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ru.katkalov.yamblz.hackathon.R;
import ru.katkalov.yamblz.hackathon.ui.activities.MainActivity;
import ru.katkalov.yamblz.hackathon.ui.logic.YaTranslator;

public class CollectWordFragment extends Fragment {
    List<String> collectTaskWords = new ArrayList<>();
    int collectTaskWordsAmount = 10;
    int currStep = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_collect_word, container, false);
        generateWordsForTask();
        fillTheWord(view, 0);
        ImageView skipNext = (ImageView) view.findViewById(R.id.skip_next);
        skipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clieckedView) {
                if (currStep < collectTaskWordsAmount - 1) {
                    currStep++;
                    fillTheWord(view, currStep);
                }
            }
        });
        return view;
    }

    private void fillTheWord(View view, int step) {
        TextView wordView = (TextView) view.findViewById(R.id.word);
        String word = collectTaskWords.get(step);
        wordView.setText(word);
    }


    private void generateWordsForTask() {
        List<String> wordsToLearn = MainActivity.wordsToLearn;
        int wordsAmount = wordsToLearn.size();
        if (wordsAmount < collectTaskWordsAmount) {
            return;
        }
        int i = 0;
        int random;
        while (i < collectTaskWordsAmount) {
            random = (int) (Math.random() * wordsAmount);
            if (!collectTaskWords.contains(wordsToLearn.get(random))) {
                collectTaskWords.add(wordsToLearn.get(random));
                i++;
            }
        }
        return;
    }

    private String translateWord(String direction, String word) {
        YaTranslator yaTranslator = new YaTranslator();
        String[] params = {getString(R.string.key_ya_dictionary), direction, word};
        yaTranslator.execute(params);

        String wordTranslated = "";
        try {
            Map<String, String> result = yaTranslator.get();
            wordTranslated = result.get("translated");
        } catch (InterruptedException ex) {

        } catch (ExecutionException ex) {

        }
        Log.e("HACATHON", wordTranslated);

        return "";
    }
}
