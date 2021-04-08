package configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpPort {
    public String ip;
    public int port;

    public IpPort()
    {
        clear();
    }
    public IpPort(String str)
    {
        update(str);
    }
    public IpPort(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }
    public IpPort(InetAddress ip, int port)
    {
        this.ip = ip.toString().substring(1);
        this.port = port;
    }
    public void update(String str)
    {
        String [] array = str.split(":");

        ip = array[0].trim();
        if(array.length > 1)
            port = Integer.parseInt(array[1].trim());
        else
            port = 0;
    }
    public InetAddress getIpAsInet() throws UnknownHostException {
        if(ip != null)
            return InetAddress.getByName(ip);
        return null;
    }
    public String toString()
    {
        if(ip == null)
            return null;
        return port > 0 ? ip + ":" + port : ip;
    }
    public void update(IpPort address) {
        ip = address.ip;
        port = address.port;
    }
    public boolean isValid(){
        if(ip == null || port == 0 || ip.length() == 0)
            return false;
        try{
            getIpAsInet();
        }catch(Exception e){
            return false;
        }
        return true;
    }
    public void clear() {
        ip = null;
        port = 0;
    }
}
