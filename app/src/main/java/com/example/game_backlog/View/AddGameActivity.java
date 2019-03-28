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

    private TextView gameTitle;
    private TextView platformTitle;
    private Spinner status;
    private Button addGameButton;
    public static final String GAME = "game";
    private Boolean createMode = new Boolean(true);
    private Game editGame;
    private ArrayAdapter<CharSequence> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        gameTitle = findViewById(R.id.editTitle);
        platformTitle = findViewById(R.id.editPlatform);
        status = findViewById(R.id.status);
        addGameButton = findViewById(R.id.addGameButton);

        mAdapter = ArrayAdapter.createFromResource(this,R.array.statusArray,android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(mAdapter);
        if(getIntent().getParcelableExtra(MainActivity.EDIT_ITEM) != null){
            editGame = getIntent().getParcelableExtra(MainActivity.EDIT_ITEM);
        }
        initComponents();
        initActions();

    }
    private void initComponents(){
        gameTitle = findViewById(R.id.editTitle);
        platformTitle = findViewById(R.id.editPlatform);
        status = findViewById(R.id.status);
        addGameButton = findViewById(R.id.addGameButton);
        mAdapter = ArrayAdapter
                .createFromResource(this,
                        R.array.statusArray, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(mAdapter);
    }
    private void initActions() {
        Game game = new Game();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        game.setDate(dateFormat.format(date));

        if (!createMode) {
            //If activity is in edit mode set id of object for update
            game.setId(editGame.getId());
            gameTitle.setText(editGame.getTitle());
            platformTitle.setText(editGame.getPlatform());
            status.setSelection(mAdapter.getPosition(editGame.getStatus()));
        }

        addGameButton.setOnClickListener(c -> {
            if(checkAllFields()){
                game.setTitle(gameTitle.getText().toString());
                game.setPlatform(platformTitle.getText().toString());
                game.setStatus(status.getSelectedItem().toString());
                Intent intent = new Intent();
                intent.putExtra(GAME, game);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
    private boolean checkAllFields(){
        if (!gameTitle.getText().toString().isEmpty() && !platformTitle.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, "Fill in all fields please", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
