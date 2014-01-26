package uk.co.vault101.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MultilineTextActor {

	private BitmapFont font;
	private LabelStyle style;
	private float screenWidth;
	private float yPosition;

	List<TextActor> textActors;
	StringBuilder currentTextForField;
	String[] textWords;
	
	//TODO: ADD TEXT ALIGN OPTION
	//TODO: ADD SUPPORT FOR NEW LINE
	
	public MultilineTextActor(String text, float yPosition, float screenWidth, LabelStyle style) {
		
		this.textWords = text.split(" ");
		
		this.font = style.font;
		this.style = style;
		this.screenWidth = screenWidth;
		this.yPosition = yPosition;
		
		textActors = new ArrayList<>();
		currentTextForField = new StringBuilder();
		
		build();
	}

	public void build() {
		        
		for (int i=0; i<textWords.length; i++) {
        	
			String potentialNextString = currentTextForField.toString() + " " + textWords[i];
			
			if (font.getBounds(potentialNextString).width < screenWidth && !isLastItem(i, textWords.length)) {
        		currentTextForField.append(" " + textWords[i]); 
        		
        	} else {
        		
        		boolean additionalLineRequired = false;
        		
        		if (isLastItem(i, textWords.length)) {
        			if (font.getBounds(potentialNextString).width < screenWidth) {
        				currentTextForField.append(" " + textWords[i]);
        			} else {
        				additionalLineRequired= true;
        			}
        		} 
        		
        		TextActor newTextActor = new TextActor(currentTextForField.toString(), yPosition, screenWidth, style);
        		yPosition = newTextActor.getY();
        		currentTextForField.setLength(0);
        		currentTextForField.append(" " + textWords[i]); 
        		potentialNextString = " " + textWords[i];
        		
        		textActors.add(newTextActor);
        		
        		if (additionalLineRequired) {
        			newTextActor = new TextActor(textWords[i], yPosition, screenWidth, style);
        			textActors.add(newTextActor);
        		}
        	}
        }
		
	}
	
	public List<TextActor> getTextActors() {
		return textActors;
	}
	
	private boolean isLastItem(int i, int countOfItems) {
		return i == (countOfItems-1);
	}
}
