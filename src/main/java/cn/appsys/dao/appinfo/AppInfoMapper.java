package cn.appsys.dao.appinfo;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInfoMapper {
	
	int add(AppInfo appInfo) ;
	
	int modify(AppInfo appInfo);
	
	int deleteAppInfoById(@Param(value = "id") Integer delId);

	List<AppInfo> getAppInfoList(@Param(value = "softwareName") String querySoftwareName,
                                        @Param(value = "status") Integer queryStatus,
                                        @Param(value = "categoryLevel1") Integer queryCategoryLevel1,
                                        @Param(value = "categoryLevel2") Integer queryCategoryLevel2,
                                        @Param(value = "categoryLevel3") Integer queryCategoryLevel3,
                                        @Param(value = "flatformId") Integer queryFlatformId,
                                        @Param(value = "devId") Integer devId,
                                        @Param(value = "from") Integer currentPageNo,
                                        @Param(value = "pageSize") Integer pageSize);

	int getAppInfoCount(@Param(value = "softwareName") String querySoftwareName,
                               @Param(value = "status") Integer queryStatus,
                               @Param(value = "categoryLevel1") Integer queryCategoryLevel1,
                               @Param(value = "categoryLevel2") Integer queryCategoryLevel2,
                               @Param(value = "categoryLevel3") Integer queryCategoryLevel3,
                               @Param(value = "flatformId") Integer queryFlatformId,
                               @Param(value = "devId") Integer devId);

	AppInfo getAppInfo(@Param(value = "id") Integer id, @Param(value = "APKName") String APKName);

	int deleteAppLogo(@Param(value = "id") Integer id);

	/**
	 * 根据appId，更新最新versionId
	 * @param versionId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	int updateVersionId(@Param(value = "versionId") Integer versionId, @Param(value = "id") Integer appId);

	/**
	 * updateSaleStatusByAppId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	int updateSaleStatusByAppId(@Param(value = "id") Integer appId);

	/*
	 * 更新app状态
	 * @param status
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int updateSatus(@Param(value = "status") Integer status, @Param(value = "id") Integer id);

}
