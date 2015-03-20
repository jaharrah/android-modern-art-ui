package course.labs.modernartuilab;

import android.widget.TextView;

public class ModernArtSquare {
    private TextView mTextView;

    private int startColorRed;
    private int startColorGreen;
    private int startColorBlue;
    private int endColorRed;
    private int endColorGreen;
    private int endColorBlue;

    public ModernArtSquare(TextView mTextView, int startColorRed, int startColorGreen,
                           int startColorBlue, int endColorRed, int endColorGreen,
                           int endColorBlue) {
        this.mTextView = mTextView;
        this.startColorRed = startColorRed;
        this.startColorGreen = startColorGreen;
        this.startColorBlue = startColorBlue;
        this.endColorRed = endColorRed;
        this.endColorGreen = endColorGreen;
        this.endColorBlue = endColorBlue;
    }

    public TextView getmTextView() {
        return mTextView;
    }

    public int getStartColorRed() {
        return startColorRed;
    }

    public int getStartColorGreen() {
        return startColorGreen;
    }

    public int getStartColorBlue() {
        return startColorBlue;
    }

    public int getEndColorRed() {
        return endColorRed;
    }

    public int getEndColorGreen() {
        return endColorGreen;
    }

    public int getEndColorBlue() {
        return endColorBlue;
    }

    public void setStartColor(int r, int g, int b) {
        this.startColorRed = r;
        this.startColorGreen = g;
        this.startColorBlue = b;
    }

    public void setEndColor(int r, int g, int b) {
        this.endColorRed = r;
        this.endColorGreen = g;
        this.endColorBlue = b;
    }
}
