package xyz.vfhhu.lib.android.utils;

public class CRC { 

    public CRC() {
    	/*
    	String str = "020020D100010000105D50EEDB5CF1C5FA66E9C84B5C35CBB60000000000C905";
    	byte[] bytes = hexToBytes(str);
    	int crc = modbus(bytes);
    	System.out.println(str);
    	System.out.println(byteToHexString(bytes));
    	System.out.println(crc);
    	*/
    }
    public static void modbus(byte[] buf)
    {
    	int len = buf.length -2;
    	int crc = 0xFFFF;

    	for (int pos = 0; pos < len; pos++) {
    		crc ^= (int)buf[pos] & 0xFF;    

    		for (int i = 8; i != 0; i--) {     
    			if ((crc & 0x0001) != 0) {       
    				crc >>= 1;                    
            		crc ^= 0xA001;
    			}
    			else                             
    				crc >>= 1;                    
    		}
    	}
    	buf[len+1]=(byte)(crc>>8 & 0xFF);
    	buf[len]=(byte)(crc & 0xFF);  
    }
    
    public  byte[] hexToBytes(String hexString) {
        hexString=hexString.trim().replace(" ","");
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] rawData = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127)
                value -= 256;
            rawData[i] = (byte) value;
        }
        return rawData;
    }
    
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String byteToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }    

}