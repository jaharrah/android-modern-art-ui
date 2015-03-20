package course.labs.modernartuilab;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class ModernArtActivity extends ActionBarActivity {

    static private final String URL = "http://www.moma.org";

    // String for LogCat documentation
    private final static String TAG = "ModernArtActivity";

    // int threshold for how dark "grey" should get
    private final int GREY_THRESHOLD = 100;

    // int of max color for RGB, which is 255
    private final int RGB_MAX_VALUE = 255;

    private ModernArtSquare[] modernArtSquares = new ModernArtSquare[5];
    private int mPercentage = Integer.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modern_art);

        SeekBar colorControl = (SeekBar) findViewById(R.id.seekBar);
        colorControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                Log.i(TAG, "progress value: " + progress);
                ModernArtActivity.this.updateSquareColors(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        TextView[] textViews = {(TextView) findViewById(R.id.textViewBottomLeft),
            (TextView) findViewById(R.id.textViewBottomRight),
            (TextView) findViewById(R.id.textViewTopLeft),
            (TextView) findViewById(R.id.textViewTopRight),
            (TextView) findViewById(R.id.textViewMediumRight)};

        loadModernArtSquares(textViews);

        this.updateSquareColors(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modern_art, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_moreinfo) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_inspiration)
                .setPositiveButton(R.string.button_visitMOMA, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                        ModernArtActivity.this.startActivity(baseIntent);
                    }
                })
                .setNegativeButton(R.string.button_notNow, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
            TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to generated ModertArtSquares with random start/end colors,
     * with one of them randomly being a white/grey color that won't change.
     * @param squareTextViews
     */
    private void loadModernArtSquares(final TextView[] squareTextViews) {
        int whiteGreyTextViewIdx = this.getRandomIntValue(squareTextViews.length);

        for (int i = 0; i < squareTextViews.length; i++) {
            if(whiteGreyTextViewIdx == i) {

                // create white/grey box for both start and end colors
                int greyColor = getRandomGreyValue();

                modernArtSquares[i] = new ModernArtSquare(squareTextViews[i], greyColor,
                        greyColor, greyColor, greyColor, greyColor, greyColor);

                // begin fun: add an onClick listener to change color, if mPercentage is 0 or 100.
                squareTextViews[i].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(ModernArtActivity.this.mPercentage == 0 || ModernArtActivity.this.mPercentage == 100) {
                            ModernArtSquare square = ModernArtActivity.this.findSquareById(v.getId());
                            int greyColor = ModernArtActivity.this.getRandomGreyValue();
                            square.setStartColor(greyColor,greyColor,greyColor);
                            square.setEndColor(greyColor,greyColor,greyColor);
                            ModernArtActivity.this.updateSquareColor(square);
                        }
                    }
                });
                // end fun
            } else {

                modernArtSquares[i] = new ModernArtSquare(squareTextViews[i],
                        getRandomIntValue(RGB_MAX_VALUE),getRandomIntValue(RGB_MAX_VALUE),
                        getRandomIntValue(RGB_MAX_VALUE),getRandomIntValue(RGB_MAX_VALUE),
                        getRandomIntValue(RGB_MAX_VALUE),getRandomIntValue(RGB_MAX_VALUE));

                // begin fun: add an onClick listener to change color, if mPercentage is 0 or 100.
                squareTextViews[i].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(ModernArtActivity.this.mPercentage == 0) {
                            ModernArtSquare square = ModernArtActivity.this.findSquareById(v.getId());
                            square.setStartColor(ModernArtActivity.this.getRandomIntValue(RGB_MAX_VALUE),
                                    ModernArtActivity.this.getRandomIntValue(RGB_MAX_VALUE),
                                    ModernArtActivity.this.getRandomIntValue(RGB_MAX_VALUE));
                            ModernArtActivity.this.updateSquareColor(square);
                        }
                        if(ModernArtActivity.this.mPercentage == 100) {
                            ModernArtSquare square = ModernArtActivity.this.findSquareById(v.getId());
                            square.setEndColor(ModernArtActivity.this.getRandomIntValue(RGB_MAX_VALUE),
                                    ModernArtActivity.this.getRandomIntValue(RGB_MAX_VALUE),
                                    ModernArtActivity.this.getRandomIntValue(RGB_MAX_VALUE));
                            ModernArtActivity.this.updateSquareColor(square);
                        }
                    }
                });
                // end fun
            }
        }
    }

    /**
     * This method will take the value of the scrollbar, calculate the proper color to update
     * the textView to, and update the textView.  The calculation is for each primary color (r,g,b),
     * take the difference between the start and end colors, multiply the difference by the %
     * passed in, and add it to the start color.
     *
     * Example, start color (10,50,0), end color (50,10,0).
     * zero % -> (10+((50-10)*0.0), 50+((10-50)*0.0), 0+((0-0)*0.0)) == (10,50,0) start color
     * 50 %   -> (10+((50-10)*0.5), 50+((10-50)*0.5), 0+((0-0)*0.5)) == (30,30,0)
     * 100 %  -> (10+((50-10)*1.0), 50+((10-50)*1.0), 0+((0-0)*1.0)) == (50,10,0) end color
     *
     * @param scrollBarValue The percentage value of the scrollbar, from 0 to 100
     */
    private void updateSquareColors(int scrollBarValue) {

        // this is just to add efficiency - only repaint if scrollBarValue different from last call.
        if(scrollBarValue == mPercentage) {
            return;
        } else {

            mPercentage = scrollBarValue;

            for (ModernArtSquare mas : modernArtSquares) {
                updateSquareColor(mas);
            }

        }
    }

    private void updateSquareColor(ModernArtSquare mas) {
        mas.getmTextView().setBackgroundColor(Color.rgb(
            getCalculatedColor(mas.getStartColorRed(), mas.getEndColorRed(), mPercentage),
            getCalculatedColor(mas.getStartColorGreen(), mas.getEndColorGreen(), mPercentage),
            getCalculatedColor(mas.getStartColorBlue(), mas.getEndColorBlue(), mPercentage)));
    }

    /**
     * This method finds the applicable ModernArtSquare, based on the id of the text view inside it.
     */
    private ModernArtSquare findSquareById(int textViewId) {
        for (ModernArtSquare square : this.modernArtSquares) {
            if(square.getmTextView().getId() == textViewId) {
                return square;
            }
        }

        // none found
        return null;
    }

    /**
     * This method retrieves a random int from zero to (exclusiveMax-1).
     * @param exclusiveMax
     * @return random int value
     */
    private int getRandomIntValue(int exclusiveMax) {
        return ((int)Math.floor(Math.random() * exclusiveMax));
    }

    /**
     * This method retrieves a RGB value that is greater than the grey threshold, so grey is
     * not black.
     * @return random int greater than GREY_THRESHOLD.
     */
    private int getRandomGreyValue() {
        int greyColor = getRandomIntValue(RGB_MAX_VALUE);

        // need to make sure greyColor is high enough to see the color, not black
        while (greyColor < GREY_THRESHOLD) {
            greyColor = getRandomIntValue(RGB_MAX_VALUE);
        }

        return greyColor;
    }

    /**
     * Calculate the color based on the formula:
     * startColor + Math.floor((endColor - startColor) * (percentage / 100))
     */
    private int getCalculatedColor(int startColor, int endColor, int percentage) {
        return startColor + (int)Math.floor((endColor - startColor) * ((double)percentage / 100));
    }
}
