package fhku.challengegram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ChallengeRepository {

    protected DBHelper dbHelper;

    public ContentValues toContentValues(Challenge challenge) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", challenge.getTitle());
        contentValues.put("description", challenge.getDescription());
        contentValues.put("hosts", challenge.getHosts());
        contentValues.put("sponsors", challenge.getSponsors());
        contentValues.put("hashtags", challenge.getHasthags());
        contentValues.put("last_edit", challenge.getLastEdited());

        return contentValues;
    }

    public Challenge toChallenge(Cursor cursor) {
        Challenge challenge = new Challenge();
        challenge.setId(cursor.getInt(cursor.getColumnIndex("id")));
        challenge.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        challenge.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        challenge.setHosts(cursor.getString(cursor.getColumnIndex("hosts")));
        challenge.setSponsors(cursor.getString(cursor.getColumnIndex("sponsors")));
        challenge.setHasthags(cursor.getString(cursor.getColumnIndex("hashtags")));
        challenge.setLastEdited(cursor.getInt(cursor.getColumnIndex("last_edit")));
        return challenge;
    }

    public Challenge getChallenge(int id) {
        String selection = "id = ?";
        String[] selectionArgs = {id + ""};

        Cursor cursor = dbHelper.getReadableDatabase().query("challenges", null, selection, selectionArgs, null, null, "");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return toChallenge(cursor);
        } else {
            return null;
        }
    }

    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();

        Cursor cursor = dbHelper.getReadableDatabase().query("challenges", null, null, null, null, null, "last_edit ASC");

        while (cursor.moveToNext()) {
            Challenge challenge = toChallenge(cursor);
            challenges.add(challenge);
        }

        return challenges;
    }

    public void addNote(Challenge challenge) {
        challenge.setLastEditedToNow();
        ContentValues contentValues = toContentValues(challenge);
        dbHelper.getWritableDatabase().insert("challenges", null, contentValues);
    }

    public void updateChallenges(Challenge challenge, int id){
        challenge.setLastEditedToNow();
        ContentValues contentValues = toContentValues(challenge);
        String selection = "id = ?";
        String[] selectionArgs = {id + ""};
        dbHelper.getWritableDatabase().update("challenges",contentValues, selection, selectionArgs);
    }


    public static ChallengeRepository getNoteRepository(Context context) {
        ChallengeRepository challengeRepository = new ChallengeRepository();
        challengeRepository.dbHelper = new DBHelper(context);

        return challengeRepository;
    }

}
