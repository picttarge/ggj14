package uk.co.vault101.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MultilineTextActor {

	private BitmapFont font;

	List<TextActor> textActors;
	StringBuilder currentTextForField;
	String[] textWords;
	
	//TODO: ADD TEXT ALIGN OPTION
	//TODO: ADD SUPPORT FOR NEW LINE
	
	public MultilineTextActor(final String text, float yPosition, final float screenWidth, final LabelStyle style) {
		
		this.textWords = text.split(" ");
		
		this.font = style.font;
			
		textActors = new ArrayList<>();
		currentTextForField = new StringBuilder();
		        
		boolean newline = false;
		for (int i=0; i<textWords.length; i++) {
        	
			if ("\n".equals(textWords[i])) {
				newline = true;
				continue;
			}
			
			String potentialNextString = currentTextForField.toString() + " " + textWords[i];
			
			if (newline) {
				
				TextActor newTextActor = new TextActor(currentTextForField.toString(), yPosition, screenWidth, style);
        		yPosition = newTextActor.getY();
        		currentTextForField.setLength(0);
        		currentTextForField.append(" " + textWords[i]); 
        		potentialNextString = " " + textWords[i];
        		
        		textActors.add(newTextActor);
				newline = false;
				
			} else if (font.getBounds(potentialNextString).width < screenWidth && !isLastItem(i, textWords.length)) {
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
