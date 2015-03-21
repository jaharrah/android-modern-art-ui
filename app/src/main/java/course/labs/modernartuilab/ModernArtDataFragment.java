package course.labs.modernartuilab;

import android.app.Fragment;
import android.os.Bundle;

public class ModernArtDataFragment extends Fragment {

    private ModernArtSquare[] mModernArtSquares = new ModernArtSquare[5];
    private int whiteGreyTextViewIdx = Integer.MIN_VALUE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public ModernArtSquare[] getData() {
        return this.mModernArtSquares;
    }

    public void setData(ModernArtSquare[] squares) {
        this.mModernArtSquares = squares;
    }

    public int getWhiteGreyTextViewIdx() {
        return whiteGreyTextViewIdx;
    }

    public void setWhiteGreyTextViewIdx(int whiteGreyTextViewIdx) {
        this.whiteGreyTextViewIdx = whiteGreyTextViewIdx;
    }

}
