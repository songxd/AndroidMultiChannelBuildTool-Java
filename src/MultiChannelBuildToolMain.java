

import java.io.File;
import org.apache.commons.io.FileUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
/**
 * 
 * @author songxudong
 * 2016-01-27
 *  
 */

public class MultiChannelBuildToolMain {
	/**
	 * 渠道 定制时修改
	 */
	public static String [] channels = new String[]{
		"f1l_channelname"
	};
	/**
	 * 文件目录 定制时修改
	 */
	public static String sourceApkPath = "d:\\test";
	/**
	 * 文件名 定制时修改
	 */
	public static String sourceApkName = "f1l_v1.0.0_test.apk";
	public static String newApkPath = sourceApkPath + File.separator+"channel";
	/**
	 * 生成渠道包文件名使用 定制时修改
	 */
	public static String version = "1.2.1";
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		long pretime = time;
		try {
			String newApkName = "x";
			File channelFile = null;
			//获取apk文件
			ZipFile zipFile = new ZipFile(sourceApkPath+File.separator+sourceApkName);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); 
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setRootFolderInZip("META-INF/");
			//创建output目录 原目录下channel文件夹
			File output = new File(newApkPath);
			if (!output.exists()) {
				output.mkdirs();
			}
			
			for (int i=0 ;i<channels.length;i++) {
				 String[] split = channels[i].split("_");
			        if (split != null && split.length >= 3) {
			        	newApkName = "f1l_v"+version+"_"+split[1]+".apk";
			        }
			        System.out.println("newApkName " + newApkName);
			      //方法1. 可以通过为zip包加注释进行多渠道打包
					 //zipFile.setComment(newApkPath+File.separator+channels[i]); 
			      //方法2. 可以通过写空文件进行多渠道打包
				 channelFile = new File(newApkPath+File.separator+channels[i]);
				 if (!channelFile.exists()) {
					 channelFile.createNewFile();
				 }
				 
				 System.out.println("channelFile " + channelFile.getAbsolutePath());
				 zipFile.addFile(channelFile, parameters);
				 FileUtils.copyFile(zipFile.getFile(), new File(newApkPath+File.separator+newApkName));
				 zipFile.removeFile("META-INF/"+channelFile.getName());
				 System.out.println("第"+i+"个包时间"+(System.currentTimeMillis()-pretime)+"毫秒");
				 pretime = System.currentTimeMillis();
			}
		} catch (Exception e) {
			System.out.println("error " + e.toString());
		} 
		System.out.println((System.currentTimeMillis()-time)+"毫秒");
	}
	

}
