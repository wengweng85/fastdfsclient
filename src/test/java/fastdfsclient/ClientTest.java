package fastdfsclient;

import org.junit.Test;

import com.insigma.fastdfsclient.Fastdfs;


public class ClientTest {

	
	
	@Test
	public void test(){
		try{
			Fastdfs fastdfs=Fastdfs.getInstance();
			String filepath="d:\\2.jpg";
			fastdfs.uploadFile(filepath, "jpg");
		/*	String downloadfilepath="/group1/M00/00/00/Cr6OBFnvGQ-AWaOmAAhyawpaICA411.jpg";
			fastdfs.downloadFile(downloadfilepath);*/
			//fastdfs.deleteFile(downloadfilepath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
