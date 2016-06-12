package co.uk.sbsystems.android.jobtrackermobile;

/**
 * Created by Sam on 10/03/2016.
 */
public enum CommandType {
    PERMISSION(1),
    IMAGEUPLOAD(2),
    JOBUPLOAD(3),
    UPFINISH(4),
    NEWIMAGE (5),
    NEWJOB (6),
    OTHERCOMMAND(7);

    private int val;

    private CommandType(int mval){
        val = mval;
    }

    public int getVal(){
      return val;
    }



}
