package jac.com.notetoself;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import jac.com.notetoself.Note;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class JSONSerializer {
    private String mFilename;
    private Context mContext;
    public JSONSerializer(String fn,Context con){
        mFilename = fn;
        mContext = con;


        }
    /*public void save(List<Note> notes) throws IOException,JSONException{
        JSONArray jArray = new JSONArray();
        for(Note n : notes)
            jArray.put(n.convertToJSON());
        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename,mContext.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(jArray.toString());


        } finally {
            if (writer != null){
                writer.close();
            }
        }

    }*/
    public void save(List <Note> notes) throws IOException,JSONException {
        JSONArray jArray = new JSONArray();
        for(Note n : notes)
            jArray.put(n.convertToJSON());
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename,mContext.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(jArray.toString());
        }
            finally{
            if(writer != null){

                writer.close();
            }
        }

    }

    public ArrayList<Note> load() throws IOException,JSONException{
            ArrayList <Note> noteList = new ArrayList<>();
            BufferedReader reader = null;
            try {
                InputStream in = mContext.openFileInput(mFilename);
                reader = new BufferedReader (new InputStreamReader(in));
                StringBuilder jsonString = new StringBuilder();
                String line = null;
                while ((line = reader.readLine())!= null){
                     jsonString.append(line);
                }
                JSONArray jArray = (JSONArray)  new JSONTokener(jsonString.toString()).nextValue();
                for(int i = 0; i<jArray.length();i++){
                    noteList.add(new Note(jArray.getJSONObject(i)));
                }


            }  catch (FileNotFoundException e){
                Log.i("Starting fresh?","have a good start.");
            } finally{
                if (reader != null){
                    reader.close();
                }
            }
           return noteList;
    }
}
