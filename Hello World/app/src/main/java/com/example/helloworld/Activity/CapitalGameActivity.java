package com.example.helloworld.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.Model.Country;
import com.example.helloworld.QuizDbHelper;
import com.example.helloworld.R;
import com.example.helloworld.Model.User;
import com.example.helloworld.UserPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CapitalGameActivity extends AppCompatActivity {
    private User user;
    private TextView tvLevel, tvSoal, tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvLife, tvCoin;
    private int currentLevel;
    private ImageView back;

    private Set<Integer> completedLevels = new HashSet<>();
    private static final int TOTAL_LEVELS = 199;
    private QuizDbHelper dbHelper;
    private Set<QuizQuestion> askedQuestions = new HashSet<>();

    private UserPreferences userPreferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "GamePreferences";
    private static final String KEY_QUESTION = "capital_question";
    private static final String KEY_CORRECT_ANSWER = "capital_correct_answer";
    private static final String KEY_WRONG_ANSWER_1 = "capital_wrong_answer_1";
    private static final String KEY_WRONG_ANSWER_2 = "capital_wrong_answer_2";
    private static final String KEY_WRONG_ANSWER_3 = "capital_wrong_answer_3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        userPreferences = new UserPreferences(this);
        dbHelper = new QuizDbHelper(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvLevel = findViewById(R.id.tv_level);
        tvSoal = findViewById(R.id.tv_soal);
        tvAnswer1 = findViewById(R.id.tv_answer1);
        tvAnswer2 = findViewById(R.id.tv_answer2);
        tvAnswer3 = findViewById(R.id.tv_answer3);
        tvAnswer4 = findViewById(R.id.tv_answer4);
        tvLife = findViewById(R.id.tv_life);
        tvCoin = findViewById(R.id.tv_coin);
        back = findViewById(R.id.back);

        user = userPreferences.getUser();
        currentLevel = user.getLevelCapital();

        tvLife.setText(String.valueOf(user.getLife()));
        tvCoin.setText(String.valueOf(user.getCoin()));
        tvLevel.setText("Level " + user.getLevelCapital() + "/" + TOTAL_LEVELS);

        if (sharedPreferences.contains(KEY_QUESTION)) {
            restoreQuestionAndAnswers();
        } else {
            setQuestionAndAnswers();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setQuestionAndAnswers() {
        if (completedLevels.size() >= TOTAL_LEVELS) {
            Toast.makeText(this, "Congratulations!! You have completed all levels", Toast.LENGTH_SHORT).show();
            return;
        }

        Country country;
        do {
            country = dbHelper.getRandomCountry();
        } while (completedLevels.contains(country.getId()));

        completedLevels.add(country.getId());

        String question = "What is the capital city of " + country.getName() + " ?";
        String correctAnswer = country.getCapital();
        String[] wrongAnswers = new String[3];

        wrongAnswers[0] = dbHelper.getRandomCountryExcept(new ArrayList<>(completedLevels)).getCapital();
        wrongAnswers[1] = dbHelper.getRandomCountryExcept(new ArrayList<>(completedLevels)).getCapital();
        wrongAnswers[2] = dbHelper.getRandomCountryExcept(new ArrayList<>(completedLevels)).getCapital();

        QuizQuestion quizQuestion = new QuizQuestion(question, correctAnswer, wrongAnswers);

        if (!askedQuestions.contains(quizQuestion)) {
            editor.putString(KEY_QUESTION, question);
            editor.putString(KEY_CORRECT_ANSWER, correctAnswer);
            editor.putString(KEY_WRONG_ANSWER_1, wrongAnswers[0]);
            editor.putString(KEY_WRONG_ANSWER_2, wrongAnswers[1]);
            editor.putString(KEY_WRONG_ANSWER_3, wrongAnswers[2]);
            editor.apply();

            askedQuestions.add(quizQuestion);

            tvSoal.setText(question);

            ArrayList<String> answerList = new ArrayList<>();
            answerList.add(wrongAnswers[0]);
            answerList.add(wrongAnswers[1]);
            answerList.add(wrongAnswers[2]);
            answerList.add(correctAnswer);
            Collections.shuffle(answerList);

            tvAnswer1.setText(answerList.get(0));
            tvAnswer2.setText(answerList.get(1));
            tvAnswer3.setText(answerList.get(2));
            tvAnswer4.setText(answerList.get(3));

            final String finalCorrectAnswer = correctAnswer;
            tvAnswer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(tvAnswer1, tvAnswer1.getText().toString(), finalCorrectAnswer);
                }
            });

            tvAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(tvAnswer2, tvAnswer2.getText().toString(), finalCorrectAnswer);
                }
            });

            tvAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(tvAnswer3, tvAnswer3.getText().toString(), finalCorrectAnswer);
                }
            });

            tvAnswer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(tvAnswer4, tvAnswer4.getText().toString(), finalCorrectAnswer);
                }
            });
        } else {
            setQuestionAndAnswers();
        }
    }

    private void restoreQuestionAndAnswers() {
        String question = sharedPreferences.getString(KEY_QUESTION, "");
        String correctAnswer = sharedPreferences.getString(KEY_CORRECT_ANSWER, "");
        String wrongAnswer1 = sharedPreferences.getString(KEY_WRONG_ANSWER_1, "");
        String wrongAnswer2 = sharedPreferences.getString(KEY_WRONG_ANSWER_2, "");
        String wrongAnswer3 = sharedPreferences.getString(KEY_WRONG_ANSWER_3, "");

        tvSoal.setText(question);

        tvAnswer1.setText(wrongAnswer1);
        tvAnswer2.setText(wrongAnswer2);
        tvAnswer3.setText(wrongAnswer3);
        tvAnswer4.setText(correctAnswer);

        final String finalCorrectAnswer = correctAnswer;
        tvAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer1, tvAnswer1.getText().toString(), finalCorrectAnswer);
            }
        });

        tvAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer2, tvAnswer2.getText().toString(), finalCorrectAnswer);
            }
        });

        tvAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer3, tvAnswer3.getText().toString(), finalCorrectAnswer);
            }
        });

        tvAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer4, tvAnswer4.getText().toString(), finalCorrectAnswer);
            }
        });
    }

    private void checkAnswer(final TextView selectedAnswerView, String chosenAnswer, String correctAnswer) {
        if (user.getLife() <= 0) {
            showLimitLifeDialog();
            return;
        }

        if (chosenAnswer.equals(correctAnswer)) {
            selectedAnswerView.setBackgroundResource(R.drawable.answer_correct_bg);
            showRewardDialog();

            int userLevel = user.getLevelCapital() + 1;
            int currentCoin = user.getCoin() + 3;
            user.setCoin(currentCoin);
            user.setLevelCapital(userLevel);
            userPreferences.saveUser(user);

            tvCoin.setText(String.valueOf(user.getCoin()));

            resetAnswerBackgrounds();

            clearSharedPreferences();
        } else {
            selectedAnswerView.setBackgroundResource(R.drawable.answer_wrong_bg);

            int currentLife = user.getLife();
            if (currentLife > 0) {
                currentLife = currentLife - 1;
                user.setLife(currentLife);
                tvLife.setText(String.valueOf(currentLife));
                userPreferences.saveUser(user);
            } else {
                showLimitLifeDialog();
            }
        }
    }

    private void clearSharedPreferences() {
        editor.remove(KEY_QUESTION);
        editor.remove(KEY_CORRECT_ANSWER);
        editor.remove(KEY_WRONG_ANSWER_1);
        editor.remove(KEY_WRONG_ANSWER_2);
        editor.remove(KEY_WRONG_ANSWER_3);
        editor.apply();
    }

    private void showLimitLifeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.limit_life);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView okBtn = dialog.findViewById(R.id.ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void showRewardDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_reward);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivHome = dialog.findViewById(R.id.iv_home);
        ImageView ivNext = dialog.findViewById(R.id.iv_next);

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                refreshActivity();
            }
        });
        dialog.show();
    }

    private void refreshActivity() {
        finish();
        startActivity(getIntent());
    }

    private void resetAnswerBackgrounds() {
        tvAnswer1.setBackgroundResource(R.drawable.answer_bg);
        tvAnswer2.setBackgroundResource(R.drawable.answer_bg);
        tvAnswer3.setBackgroundResource(R.drawable.answer_bg);
        tvAnswer4.setBackgroundResource(R.drawable.answer_bg);
    }

    private class QuizQuestion {
        private String question;
        private String correctAnswer;
        private String[] wrongAnswers;

        public QuizQuestion(String question, String correctAnswer, String[] wrongAnswers) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.wrongAnswers = wrongAnswers;
        }

        @Override
        public int hashCode() {
            return question.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            QuizQuestion that = (QuizQuestion) obj;
            return question.equals(that.question);
        }
    }
}
