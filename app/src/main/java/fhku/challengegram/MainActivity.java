package fhku.challengegram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    protected LinearLayout formNoteList;
    protected Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formNoteList = findViewById(R.id.form_note_list);
        loadList();
    }

    public void loadList() {
        List<Challenge> challenges = ChallengeRepository.getNoteRepository(this).getChallenges();

        for (Challenge challenge : challenges) {
            Button button = new Button(this);
            button.setText(challenge.getTitle());
            button.setTag(challenge.getId());
            button.setOnClickListener(this);
            button.setOnLongClickListener(this);

            formNoteList.addView(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_new) {
            startActivity(new Intent(this, ChallengeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = (int) view.getTag();

        Intent intent = new Intent(this, ChallengeActivity.class);
        intent.putExtra("challenge", id);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(this,"LongClick",Toast.LENGTH_LONG).show();
        //ChallengeRepository.getNoteRepository(this).deleteNote(challenge,challenge.getId());
        return true;
    }
}

