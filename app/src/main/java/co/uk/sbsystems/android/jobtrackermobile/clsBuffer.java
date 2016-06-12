package co.uk.sbsystems.android.jobtrackermobile;

import android.util.Log;

/**
 * Created by Sam on 11/03/2016.
 */
public class clsBuffer {
    String data;

    public void addString(String dataIn){
        data+= dataIn;

    }

    public String geStringtData(){
        return data;
    }
    public void clear(){
        data="";
    }

    public double getSize(){

        double datasz = -1.0;
        if (data.contains("<?xml"))
        {
            // The command is always sent at the start
            // = JTApplication.getInstance().GetParser().ExtractTagValue(DataIn ,"CMD");
            // Get the data size
            datasz = GetDataSize(data);


        }

        return datasz;

    }

    public Double GetDataSize(String sDataIn) {
        Double size = -1.0;			// -1 = Unknown size
        String sSize="";
        Integer startPos;
        Integer endPos;

        try {
            if (sDataIn.contains("<SIZE>")) {
                startPos = sDataIn.indexOf("<SIZE>");
                endPos = sDataIn.indexOf("</SIZE>");
                sSize = sDataIn.substring(startPos + 6, endPos);
                if (sSize.equals("XXX")) {
                    size  = -1.0;
                } else {
                    size = Double.parseDouble(sSize);
                }
            }
        } catch (Exception ex) {
            Log.i("Buffer GedtDataSize::", ex.getMessage());
        }

        return size;
    }


    public int getBufferSize(){
        return data.length();
    }

    public String GetCommand(){
        String sCmd = "";
        String sDataIn = data;

        try
        {
            if (sDataIn.contains("</data>") || (sDataIn.contains("</ERROR>")) || sDataIn.contains("<NAK"))
            {
                if (sDataIn.contains("</ERROR>"))
                {
                    sCmd = "ERR";
                    return (sCmd);
                }
                else
                {
                    // Set cursor to normal.
                  //  _StillReceiving = false;
                   // _DataIn += sDataIn;

                    try
                    {
                        if (sDataIn.contains("NAK")) {
                            JTApplication.getInstance().GetParser().ResetDocLoaded();
                            sCmd = "NAK";
                        } else {
                            JTApplication.getInstance().GetParser().ResetDocLoaded();
                            sCmd = JTApplication.getInstance().GetParser().ExtractTagValue(data ,"CMD");
                        }
                    }
                    catch (Exception e)
                    {
                        Log.i("GetCommand", "Exception: " + e.getMessage());
                        sCmd = "";
                    }

                }
            }
            else
            {
              //  _StillReceiving = true;
               // _DataIn += sDataIn;
                sCmd = "";
            }
        }
        catch (Exception e)
        {
            Log.i("GetCommand", "Exception: " + e.getMessage());
            sCmd = "";
        }


        return (sCmd);

    }

}
