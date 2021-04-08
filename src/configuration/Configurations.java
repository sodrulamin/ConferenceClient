package configuration;

import sample.PreferenceConstants;

public class Configurations {
    private String accountDisplayName;
    private IpPort signalingServerAddress;
    private IpPort mediaServerAddress;
    private String accountPassword;
    private String accountEmail;
    private String accountUid;
    private boolean javaRecording;
    private String participantID;



    public void loadFromPreference(){
        accountDisplayName = PreferenceManager.getInstance().get(PreferenceConstants.accountDisplayName, null);
        String ipPort = PreferenceManager.getInstance().get(PreferenceConstants.signalingAddress, null);
        if(ipPort != null && ipPort.length() > 0) {
            signalingServerAddress = new IpPort(ipPort);
        }
        ipPort = PreferenceManager.getInstance().get(PreferenceConstants.mediaAddress, null);
        if(ipPort != null && ipPort.length() > 0)
            mediaServerAddress = new IpPort(ipPort);
        accountPassword = PreferenceManager.getInstance().get(PreferenceConstants.accountPassword, null);
        accountEmail = PreferenceManager.getInstance().get(PreferenceConstants.accountEmail, null);
        accountUid = PreferenceManager.getInstance().get(PreferenceConstants.accountUid, null);
        javaRecording = PreferenceManager.getInstance().getBoolean(PreferenceConstants.javaRecording, true);
    }

    private Configurations(){
        loadFromPreference();
    }
    private static Configurations config = null;
    public static synchronized Configurations getInstance(){
        if(config == null)
            config = new Configurations();
        return config;
    }



    public String getAccountDisplayName() {
        return accountDisplayName;
    }

    public IpPort getSignalingServerAddress() {
        return signalingServerAddress;
    }

    public IpPort getMediaServerAddress() {
        return mediaServerAddress;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public boolean isJavaRecording() {
        return javaRecording;
    }


    public void setAccountUid(String accountUid) {
        this.accountUid = accountUid;
        PreferenceManager.getInstance().put(PreferenceConstants.accountUid, accountUid);
    }

    public void setAccountDisplayName(String accountDisplayName) {
        this.accountDisplayName = accountDisplayName;
        PreferenceManager.getInstance().put(PreferenceConstants.accountDisplayName, accountDisplayName);
    }

    public void setSignalingServerIp(String ip) {
        if(signalingServerAddress == null)
            signalingServerAddress = new IpPort();
        this.signalingServerAddress.ip = ip;
        PreferenceManager.getInstance().put(PreferenceConstants.signalingAddress, signalingServerAddress.toString());
    }
    public void setSignalingServerPort(int port) {
        if(signalingServerAddress == null)
            signalingServerAddress = new IpPort();
        this.signalingServerAddress.port = port;
        PreferenceManager.getInstance().put(PreferenceConstants.signalingAddress, signalingServerAddress.toString());
    }

    public void setMediaServerIp(String ip) {
        if(mediaServerAddress == null)
            mediaServerAddress = new IpPort();
        this.mediaServerAddress.ip = ip;
        PreferenceManager.getInstance().put(PreferenceConstants.mediaAddress, mediaServerAddress.toString());
    }
    public void setMediaServerPort(int port) {
        if(mediaServerAddress == null)
            mediaServerAddress = new IpPort();
        this.mediaServerAddress.port = port;
        PreferenceManager.getInstance().put(PreferenceConstants.mediaAddress, mediaServerAddress.toString());
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
        PreferenceManager.getInstance().put(PreferenceConstants.accountPassword, accountPassword);
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
        PreferenceManager.getInstance().put(PreferenceConstants.accountEmail, accountEmail);
    }
    public void setJavaRecording(boolean javaRecording) {
        this.javaRecording = javaRecording;
        PreferenceManager.getInstance().putBoolean(PreferenceConstants.javaRecording, javaRecording);
    }


    public void setParticipantID(String participantId) {
        this.participantID = participantId;
    }
    public String getParticipantID(){
        return participantID;
    }
}
