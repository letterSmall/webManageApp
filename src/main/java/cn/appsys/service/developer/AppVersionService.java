package cn.appsys.service.developer;

import cn.appsys.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {
	/**
	 * 根据appId查询相应的app版本列表
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	List<AppVersion> getAppVersionList(Integer appId);
	/**
	 * 新增app版本信息，并更新app_info表的versionId字段
	 * @param appVersion
	 * @return
	 * @throws Exception
	 */
	boolean appsysadd(AppVersion appVersion);
	/**
	 * 根据id获取AppVersion
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AppVersion getAppVersionById(Integer id);
	
	/**
	 * 修改app版本信息
	 * @param appVersion
	 * @return
	 * @throws Exception
	 */
	boolean modify(AppVersion appVersion);
	
	/**
	 * 删除apk文件
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteApkFile(Integer id);
}
