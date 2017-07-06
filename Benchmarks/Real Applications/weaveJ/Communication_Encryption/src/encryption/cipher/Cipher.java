package encryption.cipher;

public   class Cipher {

	public static final  int KEY = 128;
    public static String Encrypt(String message)
    {
        String result = "";
        for (int i = 0; i < message.length(); i++)
        	result += (char)(message.charAt(i) + KEY);
        return result;
    }

    public static String Decrypt(String message)
    {
        String result = "";
        for (int i = 0; i < message.length(); i++)
        	result += (char)(message.charAt(i) - KEY);
        return result;
    }

    
}
