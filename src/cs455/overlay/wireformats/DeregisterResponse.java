package cs455.overlay.wireformats;

import java.io.*;

/**
 * Created by MyGarden on 17/2/13.
 */
public class DeregisterResponse implements Event {

    private Type type;
    private boolean code;
    private String info;

    public DeregisterResponse(boolean code, String info){

        this.type = Type.DEREGISTER_RESPONSE;
        this.code = code;
        this.info = info;

    }



    public byte[] getBytes(){
        try {
            byte[] marshalledBytes = null;
            ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

            //write type
            dout.writeInt(type.getValue());

            //write code (boolean)
            dout.writeBoolean(code);

            //write info string
            byte[] infoBytes = info.getBytes();
            int infoLength = infoBytes.length;
            dout.writeInt(infoLength);
            dout.write(infoBytes);

            dout.flush();
            marshalledBytes = baOutputStream.toByteArray();

            baOutputStream.close();
            dout.close();
            return marshalledBytes;
        } catch (IOException ioe){
            System.out.println("Exception: deregisterResponse.getBytes");
            System.exit(-1);
        }
        return null;
    }

    public static DeregisterResponse decodeByte(byte[] marshalledBytes){
        try {
            ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
            DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

            //readin type, but actually already known
            din.readInt();

            //readin code(boolean)
            boolean code = din.readBoolean();

            //readin info string
            int infoLength = din.readInt();
            byte[] infoBytes = new byte[infoLength];
            din.readFully(infoBytes);
            String info = new String(infoBytes);


            baInputStream.close();
            din.close();

            return new DeregisterResponse(code, info);

        } catch (IOException ioe){
            System.out.println("Exception: deregisterResponse.getBytes");
            System.exit(-1);
        }
        return null;
    }
    public Type getType(){
        return this.type;
    }
    public boolean getCode(){
        return this.code;
    }
    public String getInfo() {
        return this.info;
    }
}
