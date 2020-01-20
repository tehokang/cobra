package com.teho.cobra;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.teho.cobra.codec.protocol.response.CobraEvent;
import com.teho.cobra.constant.COBRA_N_ENUM;
import com.teho.cobra.interfaces.IEventThreadListener;

public class EventThread implements Runnable {

	public EventThread(IEventThreadListener listener) {
		m_listener = listener;
		m_queue = new ArrayBlockingQueue<CobraEvent>(COBRA_N_ENUM.EVENT_Q_SIZE.get());
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
					CobraEvent event = (CobraEvent)m_queue.take();
					m_listener.onRecvEventFromThread(event);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void putEvent(CobraEvent cmd) throws InterruptedException {
		m_queue.put(cmd);
	}

	public void stop() {
		this.m_is_run = false;
		m_thread.interrupt();
		m_thread = null;
		m_queue.clear();
		m_queue = null;
	}
	
	private BlockingQueue<CobraEvent> m_queue;
	private IEventThreadListener m_listener;
	private Thread m_thread;
	private boolean m_is_run = true;
}
