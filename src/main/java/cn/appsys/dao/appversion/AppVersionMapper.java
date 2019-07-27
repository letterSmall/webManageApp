package cn.appsys.dao.appversion;

import cn.appsys.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppVersionMapper {
	
	List<AppVersion> getAppVersionList(@Param("appId") Integer appId) ;

	int add(AppVersion appVersion);

	int getVersionCountByAppId(@Param("appId") Integer appId);

	int deleteVersionByAppId(@Param("appId") Integer appId);

	AppVersion getAppVersionById(@Param("id") Integer id);

	int modify(AppVersion appVersion);

	int deleteApkFile(@Param("id") Integer id);
}
