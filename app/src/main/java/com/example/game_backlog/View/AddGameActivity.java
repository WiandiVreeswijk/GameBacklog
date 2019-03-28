package com.example.game_backlog.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game_backlog.Model.Game;
import com.example.game_backlog.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGameActivity extends AppCompatActivity {

    /**Declare variables**/
    private TextView gameTitle;
    private TextView platformTitle;
    private Spinner status;
    private Button addGameButton;
    public static final String GAME = "game";
    private Boolean create = Boolean.TRUE;
    private Game editGame;
    private ArrayAdapter<CharSequence> mAdapter;
    Game game = new Game();
    Date date = new Date();

    /**Instantiate different UI elements **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        initialize();

    }
    private void initialize(){
        /**initialize UI elements**/
        gameTitle = findViewById(R.id.editTitle);
        platformTitle = findViewById(R.id.editPlatform);
        status = findViewById(R.id.status);
        addGameButton = findViewById(R.id.addGameButton);


        mAdapter = ArrayAdapter
                .createFromResource(this,
                        R.array.statusArray, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(mAdapter);

        /**check if EDIT_GAME in MainActivity is not null
         * If that is the case. editGame  will receive the data from MainActivity.EDIT_GAME **/
        if(getIntent().getParcelableExtra(MainActivity.EDIT_GAME) != null){
            editGame = getIntent().getParcelableExtra(MainActivity.EDIT_GAME);
        }
        /**format and pare date in a local manner
         * set date of game to this format : "dd/MM/yyyy"**/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        game.setDate(dateFormat.format(date));
        /**set id,title,platform and status text from the data in editGame**/
        if (!create) {
            game.setId(editGame.getId());
            gameTitle.setText(editGame.getTitle());
            platformTitle.setText(editGame.getPlatform());
            status.setSelection(mAdapter.getPosition(editGame.getStatus()));
        }

        addGameButton.setOnClickListener(c -> {
            /**if title and platform TextEdits are filled**/
            if(!gameTitle.getText().toString().isEmpty() && !platformTitle.getText().toString().isEmpty()){
                /**fill in entity with string values**/
                game.setTitle(gameTitle.getText().toString());
                game.setPlatform(platformTitle.getText().toString());
                game.setStatus(status.getSelectedItem().toString());
                /**Send an object from this activity to the mainActivity**/
                Intent intent = new Intent();
                intent.putExtra(GAME, game);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }else{
                Toast.makeText(this, "Fill in a title and a description", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
