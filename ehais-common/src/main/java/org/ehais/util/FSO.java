package org.ehais.util;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.io.SAXReader;

/**
 * @author 梁国俊
 * 
 */
public class FSO {

	/**
	 * 检验文件夹是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean FolderExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检验文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean TextFileExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static boolean CreateFolder(String path) {
		boolean f = false;
		File file = new File(path);
		f = file.mkdirs();
		return f;
	}

	/**
	 * 创建文件
	 * 
	 * @param files
	 * @return
	 */
	public static boolean CreateTextFile(String files) {
		boolean f = false;
		File file = new File(files);
		try {
			File filepath = file.getParentFile();// 获取文件夹路径
			// 判断文件夹是否存在
			if (!filepath.exists()) {
				f = file.getParentFile().mkdirs();
			}
			// 判断文件是否存在
			if (!file.exists()) {
				f = file.createNewFile();
			} else {
				f = false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static boolean DeleteFolder(String path) {
		boolean f = false;
		File file = new File(path);
		try {
			if (file.exists()) {
				f = file.delete();
			} else {
				f = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deletefile(String delpath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(delpath);
			if (!file.isDirectory()) {
				System.out.println("1");
				file.delete();
			} else if (file.isDirectory()) {
				System.out.println("2");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						System.out.println("path=" + delfile.getPath());
						System.out.println("absolutepath="
								+ delfile.getAbsolutePath());
						System.out.println("name=" + delfile.getName());
						delfile.delete();
						System.out.println("删除文件成功");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "\\" + filelist[i]);
					}
				}
				file.delete();

			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile()   Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		int k = 0;
		String name="";
		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
//				System.out.println("文件");
//				System.out.println("path=" + file.getPath());
//				System.out.println("absolutepath=" + file.getAbsolutePath());
				if(file.getName().indexOf("svn")<0)System.out.println(name+k+"=" + file.getName());
				k++;
			} else if (file.isDirectory()) {
//				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
//						System.out.println("path=" + readfile.getPath());
//						System.out.println("absolutepath="
//								+ readfile.getAbsolutePath());
						if(readfile.getName().indexOf("svn")<0)System.out.println(filepath+ "/" + readfile.getName());
						k++;
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}

	public static List<String> ReadfileList(List<String> list,String filepath)
			throws FileNotFoundException, IOException {
		
		int k = 0;

		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
				list.add(filepath);
				k++;
			} else if (file.isDirectory()) {
//				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath );
					if (!readfile.isDirectory()) {
						list.add(filepath);
						k++;
					} else if (readfile.isDirectory()) {
						ReadfileList(list,filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return list;
	}
	
	/**
	 * 获得控制台用户输入的信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getInputMessage() throws IOException {
		System.out.println("请输入您的命令∶");
		byte buffer[] = new byte[1024];
		int count = System.in.read(buffer);
		char[] ch = new char[count - 2];// 最后两位为结束符，删去不要
		for (int i = 0; i < count - 2; i++)
			ch[i] = (char) buffer[i];
		String str = new String(ch);
		return str;
	}

	/**
	 * 以文件流的方式复制文件
	 * 
	 * @param src
	 *            文件源目录
	 * @param dest
	 *            文件目的目录
	 * @throws IOException
	 */
	public static void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	/**
	 * 文件输出示例 利用PrintStream写文件
	 */
	public static void PrintStreamDemo() {
		try {
			FileOutputStream out = new FileOutputStream("D:/test.txt");
			PrintStream p = new PrintStream(out);
			for (int i = 0; i < 10; i++)
				p.println("This is " + i + " line");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用StringBuffer写文件
	 * 
	 * @throws IOException
	 */
	public static void StringBuffer() throws IOException {
		File file = new File("/root/sms.log");
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file, true);
		for (int i = 0; i < 10000; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("这是第" + i + "行:前面介绍的各种方法都不关用,为什么总是奇怪的问题 ");
			out.write(sb.toString().getBytes("utf-8"));
		}
		out.close();
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * 转移文件目录
	 * 
	 * @param filename
	 *            文件名
	 * @param oldpath
	 *            旧目录
	 * @param newpath
	 *            新目录
	 * @param cover
	 *            若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件，否则不操作
	 *            转移文件目录不等同于复制文件，复制文件是复制后两个目录都存在该文件，而转移文件目录则是转移后，只有新目录中存在该文件。
	 */
	public static void changeDirectory(String filename, String oldpath,
			String newpath, boolean cover) {
		if (!oldpath.equals(newpath)) {
			File oldfile = new File(oldpath + "/" + filename);
			File newfile = new File(newpath + "/" + filename);
			if (newfile.exists()) {// 若在待转移目录下，已经存在待转移文件
				if (cover)// 覆盖
					oldfile.renameTo(newfile);
				else
					System.out.println("在新目录下已经存在：" + filename);
			} else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * 从目录中读取xml文件
	 * 
	 * @param path
	 *            文件目录
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 *             1.利用FileInputStream读取文件
	 *             2.利用BufferedReader读取在IO操作，利用BufferedReader和BufferedWriter效率会更高一点
	 *             3.利用dom4j读取xml文件
	 */
//	public Document readXml(String path) throws DocumentException, IOException {
//		File file = new File(path);
//		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
//		SAXReader saxreader = new SAXReader();
//		Document document = (Document) saxreader.read(bufferedreader);
//		bufferedreader.close();
//		return document;
//	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            目录
	 */
	public static void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
	}

	/**
	 * 创建新文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 * @throws IOException
	 */
	public static void createFile(String path, String filename) throws IOException {
		File file = new File(path + "/" + filename);
		if (!file.exists())
			file.createNewFile();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 */
	public static void delFile(String path, String filename) {
		File file = new File(path + "/" + filename);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/**
	 * 递归删除文件夹
	 * 
	 * @param path
	 *            要利用File类的delete()方法删除目录时，必须保证该目录下没有文件或者子目录，否则删除失败，因此在实际应用中，我们要删除目录
	 *            ，必须利用递归删除该目录下的所有子目录和文件，然后再删除该目录
	 */
	public static void delDir(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					delDir(path + "/" + tmp[i].getName());
				} else {
					tmp[i].delete();
				}
			}
			dir.delete();
		}
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String BufferedReader(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
//		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8")); 
		
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
//			temp = common.toUTF8(temp);
//			temp = new String(temp.getBytes(), "UTF-8");
			
			sb.append(temp + "\r\n");
			temp = br.readLine();
		}
		return sb.toString();
	}
	
	public static String BufferedReader(File file) throws IOException {
		
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
//		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()),"UTF-8")); 
		
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
//			temp = common.toUTF8(temp);
//			temp = new String(temp.getBytes(), "UTF-8");
			
			sb.append(temp + "\r\n");
			temp = br.readLine();
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 读取文件解决中文乱码问题
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String BufferedReaderZhDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())throw new FileNotFoundException();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		
		String temp = null;
		StringBuffer sb = new StringBuffer();
		while ((temp = br.readLine()) != null) {
			sb.append(temp + "\r\n");
		}
		return sb.toString();
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String FileInputStreamDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		while ((fis.read(buf)) != -1) {
			sb.append(new String(buf));
			buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
		}
		return sb.toString();
	}

	public static boolean WriteTextFile(String path, String contents) {
		try {
			RandomAccessFile raf;

			raf = new RandomAccessFile(path, "rw");

			raf.seek(raf.length());

			raf.write(contents.getBytes("UTF-8"));
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void WriteTextFile2(String path, String contents) {
		FileWriter fw;
		try {
			fw = new FileWriter(path);
			fw.write(contents);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 写入文件，解决中文问题
	 * @param filePathAndName
	 * @param fileContent
	 */
	public static void WriteTextFileZh(String filePathAndName,String fileContent){
		try{
			File f = new File(filePathAndName);
			if(!f.exists())f.createNewFile();
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(fileContent);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	   /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                System.out.write(tempbyte);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[100];
            int byteread = 0;
            in = new FileInputStream(fileName);
            FSO.showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 随机读取文件内容
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 显示输入流中还剩的字节数
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String ReadFileName(String fileName){
    	StringBuffer sb = new StringBuffer();
    	
		try {
			InputStreamReader read = new InputStreamReader(  
			new FileInputStream(fileName), "utf-8"); 
			
			
			BufferedReader bufferedReader = new BufferedReader(read); 

			String lineTXT = null;   
			while ((lineTXT = bufferedReader.readLine()) != null) {   
				sb.append(lineTXT.trim());
			}
			
			read.close();   

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return sb.toString();
    }
    
    
    public static String ReadFileName(String fileName,boolean f){
    	StringBuffer sb = new StringBuffer();
    	
		try {
			InputStreamReader read = new InputStreamReader(  
			new FileInputStream(fileName), "utf-8"); 
			
			
			BufferedReader bufferedReader = new BufferedReader(read); 

			String lineTXT = null;   
			while ((lineTXT = bufferedReader.readLine()) != null) {   
				sb.append(lineTXT.trim());
			}
			
			read.close();   

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return sb.toString();
    }
    
	
	public static void main(String[] args) {
		FSO fso = new FSO();
//		fso.createDir("D:\\124\\567\\098");
//		String fileName = "/Applications/XAMPP/htdocs/taobaoApi/examples/xml/TRADE_PAID.xml";
//		String str = fso.ReadFileName(fileName);
//		System.out.println(str);
		try{
			String path = "D:/workspace_android/WXshare/libs";
			fso.readfile(path);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
}
