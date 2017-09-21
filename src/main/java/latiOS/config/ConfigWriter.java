package latiOS.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigWriter {
	
	private FileOutputStream config;
	
	public ConfigWriter(FileOutputStream fop) {
		config = fop;
	}
	/**
	 * Used to write to the config file. Automatically adds a newline.
	 * 
	 * @param w - the string to write
	 * @param isComment - is it a comment
	 * @throws IOException
	 */
	private void write(String w, boolean isComment) throws IOException {
		if (isComment) {
			w = w.replace("\n", "\n#");
			w="#"+w;
		}
		w=w+"\n";
		config.write(w.getBytes());
	}
	
	/**
	 * Used to write to the config file
	 * 
	 * @param w - the string to write
	 * @param isComment - is it a comment
	 * @param lines - number of line to add after
	 * @throws IOException
	 */
	private void write(String w,boolean isComment, int lines) throws IOException {
		if (isComment) {
			w = w.replace("\n", "\n#");
			w="#"+w;
		}
		for (int i=0;i<lines;i++) {
			w=w+"\n";
		}
		config.write(w.getBytes());
	}
	
	public void makeHeader(String msg) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		write(dateFormat.format(date), true);
		write(msg, true, 2);
	}
	@Deprecated
	public void addBoolean(ConfigValues name, String description, boolean defaltValue) throws IOException {
		write(description, true);
		write("B:"+name.getName()+"=", false, 0);
		write(Boolean.toString(defaltValue), false, 2);
	}
	@Deprecated
	public void addInt(ConfigValues name, String description, int defaultValue) throws IOException {
		write(description, true);
		write("I:"+name.getName()+"=", false, 0);
		write(Integer.toString(defaultValue), false, 2);
	}
	@Deprecated
	public void addDouble(ConfigValues name, String description, double defaultValue) throws IOException {
		write(description, true);
		write("D:"+name.getName()+"=", false, 0);
		write(Double.toString(defaultValue), false, 2);
	}
	@Deprecated
	public void addString(ConfigValues name, String description, String defaultValue) throws IOException {
		write(description, true);
		write("S:"+name.getName()+"=", false, 0);
		write(defaultValue, false, 2);
	}
	
	public void addValue(ConfigValues value) throws IOException {
		write(value.getDescription(),true);
		write(value.getType().getPrefix()+value.getName()+"=",false,0);
		
	}
	
	public void addArray(ConfigValues name, String description, ConfigDataTypes type, Object[] defaultValues) throws IOException {
		write(description, true);
		String t = "";
		switch (type) {
			case STRING_ARRAY:
				t = "A[S]:";
				break;
			case INT_ARRAY:
				t = "A[I]:";
				break;
			case DOUBLE_ARRAY:
				t = "A[D]:";
				break;
			case BOOLEAN_ARRAY:
				t = "A[B]:";
				break;
			default:
				type=ConfigDataTypes.STRING_ARRAY;
				t = "A[S]:";
				break;
		}
		write(t+name.getName()+"=<", false, 0);
		for (int i=0;i<defaultValues.length;i++) {
			write(defaultValues[i].toString(), false, 0);
			if (i != defaultValues.length-1) {
				write(",", false, 0);
			}
		}
		write(">",false,2);
	}
}
