package com.woni.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Highscore {
    private SharedPreferences preferences;
    private String names[];
    private long score[];
    private final static int highscores = 5;

    public Highscore(Context context)
    {
            preferences = context.getSharedPreferences("Highscore", 0);
            names = new String[highscores];
            score = new long[highscores];

            for (int x=0; x<highscores; x++)
            {
                    names[x] = preferences.getString("name"+x, "-");
                    score[x] = preferences.getLong("score"+x, 0);
            }

    }

    public String getName(int x)
    {
            return names[x];
    }

    public long getScore(int x)
    {
            return score[x];
    }
    
    public long[] getScores(){
    	return score;
    }
    public String[] getNames(){
    	return names;
    }

    public boolean inHighscore(long score)
    {
            int position;
            for (position=0; position<highscores&&this.score[position]>score; position++);

            if (position==highscores) return false;
            return true;
    }

    public boolean addScore(String name, long score)
    {
            int position;
            for (position=0; position<highscores&&this.score[position]>score; position++);

            if (position==highscores) return false;

            for (int x=highscores-1; x>position; x--)
            {
                    names[x]=names[x-1];
                    this.score[x]=this.score[x-1];
            }

            this.names[position] = new String(name);
            this.score[position] = score;

            SharedPreferences.Editor editor = preferences.edit();
            for (int x=0; x<highscores; x++)
            {
                    editor.putString("name"+x, this.names[x]);
                    editor.putLong("score"+x, this.score[x]);
            }
            editor.commit();
            return true;

    }

}