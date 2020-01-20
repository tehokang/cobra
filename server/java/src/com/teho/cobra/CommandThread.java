package com.teho.cobra;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.teho.cobra.codec.protocol.request.CobraCommand;
import com.teho.cobra.constant.COBRA_N_ENUM;
import com.teho.cobra.exceptions.CobraException;
import com.teho.cobra.interfaces.ICommandThreadListener;
import com.teho.util.CobraLogger;

public class CommandThread implements Runnable {
	
	public CommandThread(ICommandThreadListener listener) {
		m_listener = listener;
		m_queue = new ArrayBlockingQueue<CobraCommand>(COBRA_N_ENUM.COMMAND_Q_SIZE.get());
		m_thread = new Thread(this);
		m_thread.start();
	}

	@Override
	public void run() {
		while ( m_is_run ) {
			try {
				Thread.sleep(100);
				/* do work */
				if ( null!=m_queue ) {
					CobraCommand cmd = (CobraCommand)m_queue.take();
					m_listener.onRecvCommandFromThread(cmd);
				}
			} catch (InterruptedException e) {
			} catch (CobraException e) {
			}
		}
		CobraLogger.Debug("Stopped CommandThread ");
	}
	
	public void stop() {
		m_is_run = false;
		m_thread.interrupt();
		m_thread = null;
		m_queue.clear();
		m_queue = null;
	}
	
	public void putCommand(CobraCommand cmd) throws InterruptedException {
		m_queue.put(cmd);
	}
	
	private Thread m_thread;
	private BlockingQueue<CobraCommand> m_queue;
	private ICommandThreadListener m_listener;
	private boolean m_is_run = true;
}
