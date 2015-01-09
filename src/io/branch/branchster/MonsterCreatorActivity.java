package io.branch.branchster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MonsterCreatorActivity extends Activity {

	EditText editName;
	ImageButton cmdUp;
	ImageButton cmdRight;
	ImageButton cmdDown;
	ImageButton cmdLeft;
	View botLayerOneColor;
	ImageView botLayerTwoBody;
	ImageView botLayerThreeFace;
	Button[] cmdColors;
	Button cmdDone;
	
	MonsterPreferences prefs;
	MonsterPartsFactory factory;
	int faceIndex;
	int bodyIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monster_creator);
		
		editName = (EditText) findViewById(R.id.editName);
		botLayerOneColor = (View) findViewById(R.id.botLayerOneColor);
		botLayerTwoBody = (ImageView) findViewById(R.id.botLayerTwoBody);
		botLayerThreeFace = (ImageView) findViewById(R.id.botLayerThreeFace);
		cmdDone = (Button) findViewById(R.id.cmdDone);
		cmdUp = (ImageButton) findViewById(R.id.cmdUp);
		cmdRight = (ImageButton) findViewById(R.id.cmdRight);
		cmdDown = (ImageButton) findViewById(R.id.cmdDown);
		cmdLeft = (ImageButton) findViewById(R.id.cmdLeft);
		cmdColors = new Button[]{
				(Button) findViewById(R.id.cmdColor0),
				(Button) findViewById(R.id.cmdColor1),
				(Button) findViewById(R.id.cmdColor2),
				(Button) findViewById(R.id.cmdColor3),
				(Button) findViewById(R.id.cmdColor4),
				(Button) findViewById(R.id.cmdColor5),
				(Button) findViewById(R.id.cmdColor6),
				(Button) findViewById(R.id.cmdColor7)
		};
		
		prefs = MonsterPreferences.getInstance(getApplicationContext());
		factory = MonsterPartsFactory.getInstance(getApplicationContext());
		
		for (int i = 0; i < cmdColors.length; i++) {
			Button cmdColor = cmdColors[i];
			cmdColor.setBackgroundColor(factory.colorForIndex(i));
//			cmdColor.setLeft((cmdColors.length / 2 - i) * 30

			cmdColor.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					int idx = Integer.parseInt((String)v.getTag());
					prefs.setColorIndex(idx);
					botLayerOneColor.setBackgroundColor(factory.colorForIndex(idx));
				}
			});
		}
		
		cmdUp.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				faceIndex--;
			    if (faceIndex == -1) {
			        faceIndex = factory.sizeOfFaceArray() - 1;
			    }

			    prefs.setFaceIndex(faceIndex);
			    botLayerThreeFace.setImageBitmap(factory.imageForFace(faceIndex));
			}
		});
		
		cmdDown.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				faceIndex++;
			    if (faceIndex == factory.sizeOfFaceArray()) {
			        faceIndex = 0;
			    }
			    
			    prefs.setFaceIndex(faceIndex);
			    botLayerThreeFace.setImageBitmap(factory.imageForFace(faceIndex));
			}
		});
		
		cmdLeft.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				bodyIndex--;
			    if (bodyIndex == -1) {
			    	bodyIndex = factory.sizeOfBodyArray() - 1;
			    }
			    
			    prefs.setBodyIndex(bodyIndex);
			    botLayerTwoBody.setImageBitmap(factory.imageForBody(bodyIndex));
			}
		});
		
		cmdRight.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				bodyIndex++;
			    if (bodyIndex == factory.sizeOfBodyArray()) {
			    	bodyIndex = 0;
			    }
			    
			    prefs.setBodyIndex(bodyIndex);
			    botLayerTwoBody.setImageBitmap(factory.imageForBody(bodyIndex));
			}
		});
		
		cmdDone.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editName.getText().toString().length() > 0) {
			        prefs.setMonsterName(editName.getText().toString());
			    } else {
			    	prefs.setMonsterName("Bingles Jingleheimer");
			    }
				
				Intent i = new Intent(getApplicationContext(), MonsterViewerActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		botLayerOneColor.setBackgroundColor(factory.colorForIndex(prefs.getColorIndex()));
		botLayerTwoBody.setImageBitmap(factory.imageForBody(prefs.getBodyIndex()));
		botLayerThreeFace.setImageBitmap(factory.imageForFace(prefs.getFaceIndex()));
		editName.setText(prefs.getMonsterName());
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
