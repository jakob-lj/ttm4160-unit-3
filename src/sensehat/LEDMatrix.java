package sensehat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import runtime.Scheduler;

public class LEDMatrix {
	
	private static final String SENSE_HAT_FB_NAME = "RPi-Sense FB";
	private static File graphicsFolder = new File("/sys/class/graphics/");
	private static File FrameBufferFile = null;
	private static RandomAccessFile raf = null;
	private Scheduler scheduler;

	public LEDMatrix(Scheduler s) {
		scheduler = s;
		BufferedReader br = null;
		String currentLine;
		for (final File fbFolder : graphicsFolder.listFiles()) {
			if (fbFolder.getName().contains("fb")) {
				try {
					br = new BufferedReader(new FileReader(fbFolder + "/name"));
					currentLine = br.readLine();
					if (null != currentLine
							&& currentLine.equals(SENSE_HAT_FB_NAME)) {
						String eventFolderPath = fbFolder.getAbsolutePath();
						FrameBufferFile = new File("/dev/fb"
								+ eventFolderPath.substring(eventFolderPath
										.length() - 1));
						br.close();
						break;
					}
				} catch (IOException e) {
					System.err.println("LEDMatrix: Failure during the installation: " + e);
					scheduler.addToQueueLast("LEDMatrixError");
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							System.err.println("LEDMatrix: Failure during the installation: " + e);
							scheduler.addToQueueLast("LEDMatrixError");
						}
					}
				}
			}
		}
		try {
			raf = new RandomAccessFile(FrameBufferFile, "rw");
		} catch (FileNotFoundException e) {
			System.err.println("LEDMatrix: Failure during the installation: " + e);
			scheduler.addToQueueLast("LEDMatrixError");
		}
		scheduler.addToQueueLast("LEDMatrixReady");
	}
	
	public void writeMatrix(LEDPixelSet[] ps) {
		if (ps != null) {
			int x;
			int y;
			byte three1;
			byte three2;
			byte three3;
			byte two1;
			byte two2;
			for (int i = 0; i < ps.length; i++) {
				if (ps[i] != null) {
					x = ps[i].getX();
					y = ps[i].getY();
					if (x < 0 || x > 7 || y < 0 || y > 7) {
						System.err.println("LEDMatrixError: Failure during writing: Invalid matrix pixel position");
						scheduler.addToQueueLast("LEDMatrixError");
						return;
					}
					three1 = (byte) (ps[i].getBlue() >> 3 & 0x1F);
					three2 = (byte) (ps[i].getGreen() >> 2 & 0x3F);
					three3 = (byte) (ps[i].getRed() >> 3 & 0x1F);
					two1 = (byte) ((three2 << 5) + three1);
					two2 = (byte) ((three3 << 3) + (three2 >> 3));
					try {
						raf.seek(2 * (x * 8 + y));
						raf.write(two1);
						raf.write(two2);
					} catch (IOException e) {
						System.err.println("LEDMatrixError: Failure during writing: " + e);
						scheduler.addToQueueLast("LEDMatrixError");
					}
				}
			}
		}

	}

}
