package swt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bsettle.encryption.util.DESUtil;
import com.bsettle.encryption.util.RSAUtil;

public class DecryptFile {

	protected Shell shell;
	private Button select_private_key_file_button;
	private Text private_key_file;
	private Text public_key;
	private Label private_key_file_title;
	private Label decrypt_file_title;
	private Text decrypt_file;
	private Button selec_dectypt_file_buttlo;
	private Label decrypt_result_title;
	private Table table;
	private List<TableColumn> tableColumns=new ArrayList<TableColumn>();
	private int length = 0;
	List<String> resultStrList=new ArrayList<String>();
	private Button export_button;
	private Text export_path;
	private Button select_export_feil_path;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			String str = "This is an test string";
//			String strKey = "This is an test key";
//			Map<String, Object> genKeyPair = RSAUtil.genKeyPair();
//			String privateKey = RSAUtil.getPrivateKey(genKeyPair);
//			String publicKey = RSAUtil.getPublicKey(genKeyPair);
//			byte[] encryptByPublicKey = RSAUtil.encryptByPrivateKey(str.getBytes(), privateKey);
//			byte[] decryptByPublicKey = RSAUtil.decryptByPublicKey(encryptByPublicKey, publicKey);
//			System.out.println(new String(decryptByPublicKey));
			DecryptFile window = new DecryptFile();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setModified(true);
		shell.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent arg0) {
			}
		});
		shell.setSize(1145, 502);
		shell.setText("对帐文件解密 SWT Application");

		Button button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String privateKeyFilePaht = private_key_file.getText();
				String decryptFilePath = decrypt_file.getText();
				String key = "";
				BufferedReader br = null;
				InputStream keyIn = null;
				InputStream in = null;
				InputStream decodeIn = null;
				InputStreamReader isr = null;
				BufferedReader decodeBr = null;
				try {
					keyIn = new FileInputStream(new File(privateKeyFilePaht));
					br = new BufferedReader(new InputStreamReader(keyIn));
					StringBuilder sb= new StringBuilder();
					String encodeKey=null;
					while (true) {
						encodeKey = br.readLine();
						if(null==encodeKey) break;
						sb.append(encodeKey);
					}
					String publicKeyStr = public_key.getText();
//					String publicKeyStr = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIvPTLV3YCocFcgU/cwBzeXUioiR+QM7v6Dc6t8RSnVDSSDFHMYPfjIa7wUg0Je6bPNDDsMr6Gs9LqgsZCBFRVTeKcMpAfS8+3KFXebT0nzfMveXxWGhgPjjxR3x6Hf/2YKeCGY4gyQ4ajFpdHeEs68DgLv5PUyfj3wSRczpH6vfAgMBAAECgYEAgdAqJnhABvlq1AuBl9G5DwRo2OPItVLd3Awj3JkebIDcPpwm2lP6VaWidz1/0NHdZYXHCDsVo7T8RFRrZ3dKJlunYvyRd/tzOje/MXrbU/zz2ef0jjcLo/Lzd5VQSLWl5QkDJUMd0RAvIncDsB4/TfHTkksDd727Ltk0ch6pVTECQQDmi0YrknW/bSO/FBDdd8Fq1cGfov5oqACoyKo+lks5R6B+Cf8IEOP/ITdhjoUOVh+VIjyN5vcLT4b8ytrk5K2ZAkEAmz9DraGOmDRImDdCzSS7XF45rSwPCq447WYhJcGPLGS7Hjry8/hqXG3zAj06qK7/n27kU2vlbIYChPkNjy1gNwJBAJ4sSAR1A9xGYRQkDD4mf8lwxttj1PCw82LZ6hPuwcWW4wIWkadTD1pny7tfMOLZK+oCB1HEeRKFibO+6dfJHWECQE4UXnR4OaBhQ1WTDXzhiX3mQfnPm+0SPL1hTTCRDfIU42tL2J9NhCjif4LTyqnGAfhsrFcsf+G58JByNhiZiLcCQQDKiIkaUgc5mo/WLpB4hbiZT5NjZrwYCKrW3ZBji7gDkztGI4cp34HmzA78M0vy8NwaRaDEYbzrBJ1AHrKhH9hD";
//					String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLz0y1d2AqHBXIFP3MAc3l1IqIkfkDO7+g3OrfEUp1Q0kgxRzGD34yGu8FINCXumzzQw7DK+hrPS6oLGQgRUVU3inDKQH0vPtyhV3m09J83zL3l8VhoYD448Ud8eh3/9mCnghmOIMkOGoxaXR3hLOvA4C7+T1Mn498EkXM6R+r3wIDAQAB";
					byte[] decryptByPublicKey = RSAUtil.decryptByPublicKey(sb.toString().getBytes(), publicKeyStr);
//					byte[] decryptByPublicKey = RSAUtil.decryptByPrivateKey(encodeKey.getBytes(), publicKeyStr);
					key = new String(decryptByPublicKey);
					br.close();
					keyIn.close();
					in = new FileInputStream(new File(decrypt_file.getText()));
					decodeIn = DESUtil.decryptFile(in, key);
					isr = new InputStreamReader(decodeIn, "UTF-8");
					decodeBr = new BufferedReader(isr);
					String tempString="";
//					List<String> resultStrList=new ArrayList<String>();
					resultStrList.clear();
					while (null!=(tempString = decodeBr.readLine())) {
						resultStrList.add(tempString);
					}
					for (String str : resultStrList) {
						System.out.println(str);
					}
					for (int i = 0; i < resultStrList.size(); i++) {
						String string = resultStrList.get(i);
						if(null!=string){
							String[] split = string.split("\\|\\+\\|");
							if(i<1){
								length=split.length;
								for (int j = 0; j < split.length; j++) {
									tableColumns.get(j+1).setText(split[j]);
									int length = split[j].length();
									tableColumns.get(j+1).setWidth(length*22);
								}
								tableColumns.get(0).setText("序号");
								tableColumns.get(0).setWidth(44);
							}else{
								if (split.length == length) {
									TableItem tableItem = new TableItem(table, SWT.NONE);
									tableItem.setText(0, String.valueOf(table.indexOf(tableItem)+1));
									for (int j = 0; j < split.length; j++) {
										tableItem.setText(j+1, split[j]);
									}
								}else{
									MessageBox msgBox = new MessageBox(shell);
									msgBox.setText("数据字段有误");
									msgBox.setMessage("数据字段个数与标题不匹配");
									msgBox.open();
								}
							}
							System.out.println();
						}
						
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
					MessageBox msgBox = new MessageBox(shell);
					msgBox.setText("解密失败");
					msgBox.setMessage(e1.getLocalizedMessage());
					msgBox.open();
				} catch (Exception e1) {
					e1.printStackTrace();
					MessageBox msgBox = new MessageBox(shell);
					msgBox.setText("解密失败");
					msgBox.setMessage(e1.getLocalizedMessage());
					msgBox.open();
				} finally{
					if(null!=decodeIn){
						try {
							decodeIn.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if(null!=br){
						try {
							br.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}


				
			}
		});
		button.setBounds(364, 8, 60, 27);
		button.setText("解密");

		public_key = new Text(shell, SWT.BORDER);
		public_key.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//		public_key.setText("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIvPTLV3YCocFcgU/cwBzeXUioiR+QM7v6Dc6t8RSnVDSSDFHMYPfjIa7wUg0Je6bPNDDsMr6Gs9LqgsZCBFRVTeKcMpAfS8+3KFXebT0nzfMveXxWGhgPjjxR3x6Hf/2YKeCGY4gyQ4ajFpdHeEs68DgLv5PUyfj3wSRczpH6vfAgMBAAECgYEAgdAqJnhABvlq1AuBl9G5DwRo2OPItVLd3Awj3JkebIDcPpwm2lP6VaWidz1/0NHdZYXHCDsVo7T8RFRrZ3dKJlunYvyRd/tzOje/MXrbU/zz2ef0jjcLo/Lzd5VQSLWl5QkDJUMd0RAvIncDsB4/TfHTkksDd727Ltk0ch6pVTECQQDmi0YrknW/bSO/FBDdd8Fq1cGfov5oqACoyKo+lks5R6B+Cf8IEOP/ITdhjoUOVh+VIjyN5vcLT4b8ytrk5K2ZAkEAmz9DraGOmDRImDdCzSS7XF45rSwPCq447WYhJcGPLGS7Hjry8/hqXG3zAj06qK7/n27kU2vlbIYChPkNjy1gNwJBAJ4sSAR1A9xGYRQkDD4mf8lwxttj1PCw82LZ6hPuwcWW4wIWkadTD1pny7tfMOLZK+oCB1HEeRKFibO+6dfJHWECQE4UXnR4OaBhQ1WTDXzhiX3mQfnPm+0SPL1hTTCRDfIU42tL2J9NhCjif4LTyqnGAfhsrFcsf+G58JByNhiZiLcCQQDKiIkaUgc5mo/WLpB4hbiZT5NjZrwYCKrW3ZBji7gDkztGI4cp34HmzA78M0vy8NwaRaDEYbzrBJ1AHrKhH9hD");
		public_key.setText("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLz0y1d2AqHBXIFP3MAc3l1IqIkfkDO7+g3OrfEUp1Q0kgxRzGD34yGu8FINCXumzzQw7DK+hrPS6oLGQgRUVU3inDKQH0vPtyhV3m09J83zL3l8VhoYD448Ud8eh3/9mCnghmOIMkOGoxaXR3hLOvA4C7+T1Mn498EkXM6R+r3wIDAQAB");
		public_key.setBounds(82, 8, 249, 23);

		private_key_file = new Text(shell, SWT.BORDER);
		private_key_file.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		private_key_file.setEditable(false);
		private_key_file.setText("请选解密私钥择文件");
		private_key_file.setBounds(82, 40, 249, 23);

		select_private_key_file_button = new Button(shell, SWT.NONE);
		select_private_key_file_button
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						FileDialog dialog = new FileDialog(shell, SWT.OPEN);
						dialog.setText("Source Folder Selection");
						dialog.setFilterExtensions(new String[] { "*.rsa",
								"*.*" });
						String filePath = dialog.open();
						if (null != dialog) {
							private_key_file.setText(filePath);
						}

					}
				});
		select_private_key_file_button.setText("浏览……");
		select_private_key_file_button.setBounds(364, 40, 60, 27);
		Label public_key_title = new Label(shell, SWT.NONE);
		public_key_title.setBounds(10, 47, 66, 17);
		public_key_title.setText("解密私钥：");

		private_key_file_title = new Label(shell, SWT.NONE);
		private_key_file_title.setText("解密公钥：");
		private_key_file_title.setBounds(10, 10, 66, 17);

		decrypt_file_title = new Label(shell, SWT.NONE);
		decrypt_file_title.setText("源文件：");
		decrypt_file_title.setBounds(10, 83, 53, 17);

		decrypt_file = new Text(shell, SWT.BORDER);
		decrypt_file.setText("请选择待解密文件");
		decrypt_file.setEditable(false);
		decrypt_file
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		decrypt_file.setBounds(82, 75, 249, 23);

		selec_dectypt_file_buttlo = new Button(shell, SWT.NONE);
		selec_dectypt_file_buttlo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setText("Source Folder Selection");
				dialog.setFilterExtensions(new String[] { "*.txt", "*.*" });
				String filePath = dialog.open();
				if (null != dialog) {
					decrypt_file.setText(filePath);
				}

			}
		});
		selec_dectypt_file_buttlo.setText("浏览……");
		selec_dectypt_file_buttlo.setBounds(364, 73, 60, 27);
		
		decrypt_result_title = new Label(shell, SWT.NONE);
		decrypt_result_title.setText("解密结果：");
		decrypt_result_title.setBounds(10, 118, 66, 17);
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(82, 118, 1028, 306);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		export_button = new Button(shell, SWT.NONE);
		export_button.setText("导出txt文件");
		export_button.setBounds(595, 78, 89, 27);
		export_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if("请选择导出目录...".equals(export_path.getText())){
					MessageBox msgBox = new MessageBox(shell);
					msgBox.setText("导出文件失败");
					msgBox.setMessage("请选择导出文件目录");
					msgBox.open();
					return;
				}
				if(resultStrList.isEmpty()){
					MessageBox msgBox = new MessageBox(shell);
					msgBox.setText("导出文件失败");
					msgBox.setMessage("请先解密后再导出");
					msgBox.open();
					return;
				}
				String textPath = export_path.getText();
				if(!(textPath.endsWith(".TXT")||textPath.endsWith(".txt"))) textPath=textPath+".txt";
				PrintWriter out = null;
				try {
					out = new PrintWriter(new FileOutputStream(textPath));
					for (String string : resultStrList) {
						out.print(string+"\n");
					}
					out.flush();
					MessageBox msgBox = new MessageBox(shell);
					msgBox.setText("导出文件成功");
					msgBox.setMessage("导出文件成功\n文件目录是："+textPath);
					msgBox.open();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					MessageBox msgBox = new MessageBox(shell);
					msgBox.setText("导出文件失败");
					msgBox.setMessage("导出文件失败");
					msgBox.open();
					return;
				}finally{
					if (null != out) {
						out.flush();
						out.close();
					}
				}
				
			}
		});
		
		export_path = new Text(shell, SWT.BORDER);
		export_path.setText("请选择导出目录...");
		export_path.setEditable(false);
		export_path.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		export_path.setBounds(595, 40, 249, 23);
		
		select_export_feil_path = new Button(shell, SWT.NONE);
		select_export_feil_path.setText("浏览……");
		select_export_feil_path.setBounds(866, 40, 60, 27);
		select_export_feil_path.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setText("导出文件目录");
				dialog.setFilterExtensions(new String[] { "*.txt", "*.*" });
				String filePath = dialog.open();
				if (null != dialog) {
					export_path.setText(filePath);
				}

			}
		});
		for (int i = 0; i < 13; i++) {
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			tableColumn.setWidth(44);
			tableColumn.setMoveable(true); 
			tableColumns.add(tableColumn);
		}
		
	}
}
