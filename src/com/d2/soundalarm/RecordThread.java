package com.d2.soundalarm;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Bundle;
import android.os.Message;

import com.d2.soundalarm.MorningShoutActivity.MyHandler;

public class RecordThread extends Thread {
	private AudioRecord ar;
	private int bs = 100;
	private static int SAMPLE_RATE_IN_HZ = 8000;
	private Message msg;
	private int number = 1;
	private int tal = 1;
	private MyHandler handler;
	private long currenttime;
	private long endtime;
	private long time = 1;

	// 到达该值之后 触发事件
	private static int BLOW_ACTIVI = 2800;

	public RecordThread(MyHandler myHandler) {
		super();
		bs = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
				AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		System.out.println("!!!!"+ bs);
		if (bs != AudioRecord.ERROR_BAD_VALUE) {
			ar = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
					AudioFormat.CHANNEL_IN_MONO,
					AudioFormat.ENCODING_PCM_16BIT, bs);
			System.out.println("!!!!");
			System.out.println("!!!!"+ bs);
			
		}
		handler = myHandler;
	}

	@Override
	public void run() {
		try {
			ar.startRecording();
			System.out.println("!!!!"+"startrecord");
			Parameter.isblow = true;
			// 用于读取的 buffer
			byte[] buffer = new byte[bs];
			System.out.println("!!!!"+"startbuffer");
			while (Parameter.isblow) {
				number++;
				System.out.println("------->"+number);
				sleep(50);
				currenttime = System.currentTimeMillis();
				int r = ar.read(buffer, 0, bs) + 1;
				int v = 0;
				for (int i = 0; i < buffer.length; i++) {
					v += (buffer[i] * buffer[i]);
				}
				int value = Integer.valueOf(v / (int) r);
				
				tal = tal + value;
				endtime = System.currentTimeMillis();
				time = time + (endtime - currenttime);

				if (time >= 500 || number > 5) {

					int total = tal / number;
					System.out.println(total+"----and---"+time);
					if (total > BLOW_ACTIVI) {
						// 发送消息通知到界面 触发动画

						// 利用传入的handler 给界面发送通知
						ar.stop();
						ar.release();
						MorningShoutActivity.i += 1;
						handler.sendEmptyMessage(0); // 改变i的值后，发送一个空message到主线程

						//
						number = 1;
						tal = 1;
						time = 1;
					}
				}

			}
			ar.stop();
			ar.release();
			bs = 100;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
