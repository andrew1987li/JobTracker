package co.uk.sbsystems.android.jobtrackermobile;

/**
 * Created by Sam on 10/03/2016.
 */
public class mUtils {
    static public byte[] MakePacket(byte[]data,byte cmd_type, int len){

        int data_size = len + 5;
        byte[] sendData = new byte[data_size];

        byte[] sizearr = intToByteArray(data_size);

        System.arraycopy(sizearr, 0, sendData, 0, 4);


        sendData[4] = cmd_type;
        System.arraycopy(data,0, sendData, 5, len);

        return  sendData;
    }

    public static byte[] intToByteArray(int a)
    {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    public static int byteArrayToInt(byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }



}
