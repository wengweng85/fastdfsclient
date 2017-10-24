package com.insigma.fastdfsclient;

import java.io.FileInputStream;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * fastdfs�ļ��ϴ�����
 * @author wengsh
 *
 */
public class Fastdfs{
	
   private static  Fastdfs fastdfs;
   
   private static TrackerClient trackerClient = null;
   private static TrackerServer trackerServer = null;
   private static StorageClient storageClient= null;
   private static StorageServer storageServer= null;
	 
	private Fastdfs(){
		
	}
	
	/**
	 * ����ģʽ
	 * @return
	 */
	public static Fastdfs  getInstance (){
		if(fastdfs==null){
			fastdfs=new Fastdfs();
			try {
				//��ȡ�����ļ�
				//��ȡFastDFS�ͻ��˵������ļ��ͳ�ʼ������
	            String conf = Fastdfs.class.getClassLoader().getResource("fdfs_client.conf").getPath();
	            ClientGlobal.init(conf);
	            trackerClient = new TrackerClient();
                trackerServer = trackerClient.getConnection();
                storageClient = new StorageClient(trackerServer,   storageServer); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return fastdfs;
	}
	
	/**
	 * �ļ��ϴ�
	 * @param byteFile
	 * @param ext_file
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public String uploadFile(String filepath,String ext_file) throws MyException,IOException{
		FileInputStream input = new FileInputStream(filepath);
		byte[] byteFile = new byte[input.available()];  
		input.read(byteFile);
		//�����ֽ����ϴ��ļ�  
		StringBuffer sbPath=new StringBuffer();
        String[] strings = storageClient.upload_file(byteFile, ext_file, null);  
        for (String string : strings) {  
            sbPath.append("/"+string);  
        }
        input.close();
        // ȫ·��  
        System.out.println(sbPath);
        return sbPath.toString();
	}
	
	/**
	 * �ļ�����
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public byte[] downloadFile(String filepath) throws MyException,IOException{
		int index_first=filepath.indexOf("/");
		int index_second=filepath.indexOf("/", index_first+1);
		byte[] b = storageClient.download_file(filepath.substring(index_first+1, index_second), filepath.substring(index_second+1));               
        System.out.println(b); 
        return b;
	}
	
	/**
	 * �ļ�ɾ��
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public int deleteFile(String filepath) throws MyException,IOException{
		int index_first=filepath.indexOf("/");
		int index_second=filepath.indexOf("/", index_first+1);
		int i= storageClient.delete_file(filepath.substring(index_first+1, index_second), filepath.substring(index_second+1));      
        System.out.println( i==0 ? "ɾ���ɹ�" : "ɾ��ʧ��:"+i);
        return i;
	}
}
