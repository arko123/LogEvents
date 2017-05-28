package download;

import com.jcraft.jsch.ChannelSftp;


import main.DownloadConfiguration;
import main.LogEvents;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class SSHConnectTest {
    @Test
    public void createConnectionTest() throws Exception {

        DownloadConfiguration downloadConfiguration = new DownloadConfiguration("C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_ssh.properties");

        SSHConnect sshConnect = new SSHConnect(downloadConfiguration.user, downloadConfiguration.password,downloadConfiguration.host,downloadConfiguration.port,downloadConfiguration.getlogsFrom,downloadConfiguration.saveLogsIn,
                downloadConfiguration.fileNameRegex,new Date().getTime());

        ChannelSftp channelSftp = sshConnect.createConnection(sshConnect.user,sshConnect.host,sshConnect.port);

        if(channelSftp.isConnected()) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }

    }

}