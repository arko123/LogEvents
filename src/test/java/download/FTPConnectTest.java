package download;

import org.junit.Test;
import org.apache.commons.net.ftp.FTPClient;

import java.util.Date;

import static org.junit.Assert.*;


public class FTPConnectTest {
    @Test
    public void createConnectionTest() throws Exception {

        FTPConnect ftpConnect = new FTPConnect("Anonymous", "testmil@test.com","ftp.swfwmd.state.fl.us","/","21", new Date());

        FTPClient ftpClient = ftpConnect.createConnection(ftpConnect.server,ftpConnect.user,ftpConnect.pass);

        if(ftpClient.isConnected()) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }


    }

}