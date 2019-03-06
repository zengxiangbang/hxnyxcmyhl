package cn.richinfo.common.log;

import org.apache.log4j.Logger;

public class BehaviorLog {
	private static final Logger logger = Logger.getLogger("behaviorlog");
	public static final String SEPARATOR = "!~~!";
	/**
	 * 日志记录
	 * 
	 * @param project
	 *            项目编码唯一
	 * @param userId
	 *            用户id
	 * @param msg
	 *            日志信息
	 * @param behaviorid
	 *            (1-登录首页,)
	 * @param ip
	 *            用户IP地址
	 * @param url
	 *            访问的地址
	 */
	public static void WriteLog(String project, String userId, String msg, String behaviorid, String code, String ip,
			String url) {
		StringBuilder builder = new StringBuilder();
		if (project == null || project.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(project);
		}
		if (userId == null || userId.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(userId);
		}
		if (msg == null || msg.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(msg);
		}
		if (behaviorid == null || behaviorid.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(behaviorid);
		}
		if (code == null || code.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(code);
		}
		if (ip == null || ip.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(ip);
		}
		if (url == null || url.length() == 0) {
			builder.append(BehaviorLog.SEPARATOR);
		} else {
			builder.append(BehaviorLog.SEPARATOR);
			builder.append(url);
		}
		logger.info(builder.toString());
	}
}
