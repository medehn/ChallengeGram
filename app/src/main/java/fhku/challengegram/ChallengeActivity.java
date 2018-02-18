package fhku.challengegram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChallengeActivity extends AppCompatActivity {

    protected Challenge challenge;
    protected EditText challengeTitle;
    protected EditText challengeDescription;
    public ImageButton getPic;
    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        challengeTitle = findViewById(R.id.title);
        challengeDescription = findViewById(R.id.description);

        int id = getIntent().getIntExtra("challenges", -1);
        if (id > 0) {
            challenge = ChallengeRepository.getNoteRepository(this).getChallenge(id);
            challengeTitle.setText(challenge.getTitle());
            challengeDescription.setText(challenge.getDescription());
        } else {
            challenge = new Challenge();
        }
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_save) {
            save();
            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void save() {
        challenge.setTitle(challengeTitle.getText().toString());
        challenge.setDescription(challengeDescription.getText().toString());

        if (challenge.getId() > 0) {
            ChallengeRepository.getNoteRepository(this).updateChallenges(challenge, challenge.getId());
        } else {
            ChallengeRepository.getNoteRepository(this).addNote(challenge);
        }
    }

    public void init() {

        getPic = findViewById(R.id.img1);
        getPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagePickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //URI
                Uri data = Uri.parse(pictureDirectoryPath);
                imagePickerIntent.setDataAndType(data, "image/*");
                startActivityForResult(imagePickerIntent, IMAGE_GALLERY_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST);
            Uri imageUri = data.getData();
            this.loadImage(imageUri);

        }
    }
    public void loadImage(Uri imageUri) {
        //input stream for imagepicker
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            //get bitmap from stream
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            //image is assigned to imagebutton
            getPic.setImageBitmap(image);
            //if image not found exception handling
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
