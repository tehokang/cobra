package com.teho.cobra.constant;

public enum COBRA_N_ENUM {
	COMMAND_Q_SIZE(100),
	EVENT_Q_SIZE(100),
	
	COBRA_CMD_INVOKE(200),
	COBRA_CMD_INVOKE_REG_CALLBACK(201),
	COBRA_CMD_INVOKE_UNREG_CALLBAC(202);
	
	COBRA_N_ENUM(int value) {
		this.m_value = value;
	}
	
	public int get() {
		return this.m_value;
	}
	
	private int m_value = 0;
}
