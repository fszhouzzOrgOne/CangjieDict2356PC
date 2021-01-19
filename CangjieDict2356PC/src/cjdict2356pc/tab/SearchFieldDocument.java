package cjdict2356pc.tab;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class SearchFieldDocument extends PlainDocument {
    private int limit;

    public SearchFieldDocument(int l) {
        this.limit = l;
    }

    @Override
    public void insertString(int arg0, String arg1, AttributeSet arg2)
            throws BadLocationException {
        super.insertString(arg0, arg1, arg2);
    }
    
    
}
